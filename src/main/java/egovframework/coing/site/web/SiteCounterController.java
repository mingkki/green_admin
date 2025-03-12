package egovframework.coing.site.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.util.EgovDateUtil;
import egovframework.coing.site.service.SiteCounterService;
import egovframework.coing.site.service.SiteInfoService;
import egovframework.coing.site.vo.SiteCounterDayVO;
import egovframework.coing.site.vo.SiteCounterMenuVO;
import egovframework.coing.site.vo.SiteInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/site/counter.do")
public class SiteCounterController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/site/", CmsManager.getModulePath());
    private final SiteCounterService siteCounterService;
    private final SiteInfoService siteInfoService;
    
    @RequestMapping()
    public String index(@RequestParam(value = "sinId", required = false, defaultValue = "") String sinId,
                        HttpServletRequest request,
                        ModelMap model) throws Exception {
        
        SiteInfoVO searchSiteInfoVO = new SiteInfoVO();
        List<SiteInfoVO> siteList = siteInfoService.selectSiteInfoListAll(searchSiteInfoVO);
        if(siteList == null || (siteList != null && siteList.size() < 1)) {
            model.addAttribute("message", "site.info.nosite");
            return CmsManager.alert(CONTENT_PATH + "counter_result.jsp", model);
        } else if("".equals(sinId) && siteList != null) {
            return "redirect:/site/counter.do?sinId=" + siteList.get(0).getSinId();
        }
        
        String today = EgovDateUtil.formatDate(EgovDateUtil.getToday(), "-");
        String yesterday = EgovDateUtil.formatDate(EgovDateUtil.addDay(today, -1), "-");
        String weekAgo = EgovDateUtil.formatDate(EgovDateUtil.addDay(yesterday, -6), "-");
        String monthAgo = EgovDateUtil.formatDate(EgovDateUtil.addDay(EgovDateUtil.addMonth(yesterday, -1), 1), "-");
        String yearAgo = EgovDateUtil.formatDate(EgovDateUtil.addDay(EgovDateUtil.addYear(yesterday, -1), 1), "-");
        
        SiteCounterDayVO searchVO = new SiteCounterDayVO();
        searchVO.setSinId(sinId);
        
        // 전체
        SiteCounterDayVO totalVO = siteCounterService.selectCounterDayTotal(searchVO);
        
        // 어제
        searchVO.setSearchStartDt(yesterday);
        searchVO.setSearchEndDt(yesterday);
        SiteCounterDayVO yesterdayVO = siteCounterService.selectCounterDayTotal(searchVO);
        
        // 오늘
        searchVO.setSearchStartDt(today);
        searchVO.setSearchEndDt(today);
        SiteCounterDayVO todayVO = siteCounterService.selectCounterDayTotal(searchVO);
        
        // 일주일간
        searchVO.setSearchStartDt(weekAgo);
        searchVO.setSearchEndDt(yesterday);
        SiteCounterDayVO weekVO = siteCounterService.selectCounterDayTotal(searchVO);
        
        // 한달간
        searchVO.setSearchStartDt(monthAgo);
        searchVO.setSearchEndDt(yesterday);
        SiteCounterDayVO monthVO = siteCounterService.selectCounterDayTotal(searchVO);
        
        // 1년간
        searchVO.setSearchStartDt(yearAgo);
        searchVO.setSearchEndDt(yesterday);
        SiteCounterDayVO yearVO = siteCounterService.selectCounterDayTotal(searchVO);
        
        model.addAttribute("today", today);
        model.addAttribute("yesterday", yesterday);
        model.addAttribute("weekAgo", weekAgo);
        model.addAttribute("monthAgo", monthAgo);
        model.addAttribute("yearAgo", yearAgo);
        
        model.addAttribute("totalVO", totalVO);
        model.addAttribute("yesterdayVO", yesterdayVO);
        model.addAttribute("todayVO", todayVO);
        model.addAttribute("weekVO", weekVO);
        model.addAttribute("monthVO", monthVO);
        model.addAttribute("yearVO", yearVO);
        model.addAttribute("siteList", siteList);
        model.addAttribute("sinId", sinId);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "counter_index.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=hour")
    public String hour(@ModelAttribute("searchSiteCounterDayVO") SiteCounterDayVO searchVO,
                       @RequestParam(value = "sinId", required = false, defaultValue = "") String sinId,
                       @RequestParam(value = "searchStartDt", required = false, defaultValue = "") String searchStartDt,
                       @RequestParam(value = "searchEndDt", required = false, defaultValue = "") String searchEndDt,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        SiteInfoVO searchSiteInfoVO = new SiteInfoVO();
        List<SiteInfoVO> siteList = siteInfoService.selectSiteInfoListAll(searchSiteInfoVO);
        if(siteList == null || (siteList != null && siteList.size() < 1)) {
            model.addAttribute("message", "site.info.nosite");
            return CmsManager.alert(CONTENT_PATH + "counter_result.jsp", model);
        } else if("".equals(sinId) && siteList != null) {
            return "redirect:/site/counter.do?act=hour&sinId=" + siteList.get(0).getSinId();
        }
        
        String today = EgovDateUtil.formatDate(EgovDateUtil.getToday(), "-");
        
        if("".equals(searchStartDt)) {
            searchVO.setSearchStartDt(today);
        }
        if("".equals(searchEndDt)) {
            searchVO.setSearchEndDt(today);
        }
        
        SiteCounterDayVO result = new SiteCounterDayVO();
        
        List<SiteCounterDayVO> resultList = siteCounterService.selectCounterDayListHour(searchVO);
        if(resultList != null && resultList.size() > 0) {
            for(int i = 0; i < resultList.size(); i++) {
                result.setScdHh00(result.getScdHh00() + resultList.get(i).getScdHh00());
                result.setScdHh01(result.getScdHh01() + resultList.get(i).getScdHh01());
                result.setScdHh02(result.getScdHh02() + resultList.get(i).getScdHh02());
                result.setScdHh03(result.getScdHh03() + resultList.get(i).getScdHh03());
                result.setScdHh04(result.getScdHh04() + resultList.get(i).getScdHh04());
                result.setScdHh05(result.getScdHh05() + resultList.get(i).getScdHh05());
                result.setScdHh06(result.getScdHh06() + resultList.get(i).getScdHh06());
                result.setScdHh07(result.getScdHh07() + resultList.get(i).getScdHh07());
                result.setScdHh08(result.getScdHh08() + resultList.get(i).getScdHh08());
                result.setScdHh09(result.getScdHh09() + resultList.get(i).getScdHh09());
                result.setScdHh10(result.getScdHh10() + resultList.get(i).getScdHh10());
                result.setScdHh11(result.getScdHh11() + resultList.get(i).getScdHh11());
                result.setScdHh12(result.getScdHh12() + resultList.get(i).getScdHh12());
                result.setScdHh13(result.getScdHh13() + resultList.get(i).getScdHh13());
                result.setScdHh14(result.getScdHh14() + resultList.get(i).getScdHh14());
                result.setScdHh15(result.getScdHh15() + resultList.get(i).getScdHh15());
                result.setScdHh16(result.getScdHh16() + resultList.get(i).getScdHh16());
                result.setScdHh17(result.getScdHh17() + resultList.get(i).getScdHh17());
                result.setScdHh18(result.getScdHh18() + resultList.get(i).getScdHh18());
                result.setScdHh19(result.getScdHh19() + resultList.get(i).getScdHh19());
                result.setScdHh20(result.getScdHh20() + resultList.get(i).getScdHh20());
                result.setScdHh21(result.getScdHh21() + resultList.get(i).getScdHh21());
                result.setScdHh22(result.getScdHh22() + resultList.get(i).getScdHh22());
                result.setScdHh23(result.getScdHh23() + resultList.get(i).getScdHh23());
                result.setScdUhh00(result.getScdUhh00() + resultList.get(i).getScdUhh00());
                result.setScdUhh01(result.getScdUhh01() + resultList.get(i).getScdUhh01());
                result.setScdUhh02(result.getScdUhh02() + resultList.get(i).getScdUhh02());
                result.setScdUhh03(result.getScdUhh03() + resultList.get(i).getScdUhh03());
                result.setScdUhh04(result.getScdUhh04() + resultList.get(i).getScdUhh04());
                result.setScdUhh05(result.getScdUhh05() + resultList.get(i).getScdUhh05());
                result.setScdUhh06(result.getScdUhh06() + resultList.get(i).getScdUhh06());
                result.setScdUhh07(result.getScdUhh07() + resultList.get(i).getScdUhh07());
                result.setScdUhh08(result.getScdUhh08() + resultList.get(i).getScdUhh08());
                result.setScdUhh09(result.getScdUhh09() + resultList.get(i).getScdUhh09());
                result.setScdUhh10(result.getScdUhh10() + resultList.get(i).getScdUhh10());
                result.setScdUhh11(result.getScdUhh11() + resultList.get(i).getScdUhh11());
                result.setScdUhh12(result.getScdUhh12() + resultList.get(i).getScdUhh12());
                result.setScdUhh13(result.getScdUhh13() + resultList.get(i).getScdUhh13());
                result.setScdUhh14(result.getScdUhh14() + resultList.get(i).getScdUhh14());
                result.setScdUhh15(result.getScdUhh15() + resultList.get(i).getScdUhh15());
                result.setScdUhh16(result.getScdUhh16() + resultList.get(i).getScdUhh16());
                result.setScdUhh17(result.getScdUhh17() + resultList.get(i).getScdUhh17());
                result.setScdUhh18(result.getScdUhh18() + resultList.get(i).getScdUhh18());
                result.setScdUhh19(result.getScdUhh19() + resultList.get(i).getScdUhh19());
                result.setScdUhh20(result.getScdUhh20() + resultList.get(i).getScdUhh20());
                result.setScdUhh21(result.getScdUhh21() + resultList.get(i).getScdUhh21());
                result.setScdUhh22(result.getScdUhh22() + resultList.get(i).getScdUhh22());
                result.setScdUhh23(result.getScdUhh23() + resultList.get(i).getScdUhh23());
                result.setScdHits(result.getScdHits() + resultList.get(i).getScdHits());
                result.setScdUniqHits(result.getScdUniqHits() + resultList.get(i).getScdUniqHits());
            }
        } else {
            result.setScdHh00(0);
            result.setScdHh01(0);
            result.setScdHh02(0);
            result.setScdHh03(0);
            result.setScdHh04(0);
            result.setScdHh05(0);
            result.setScdHh06(0);
            result.setScdHh07(0);
            result.setScdHh08(0);
            result.setScdHh09(0);
            result.setScdHh10(0);
            result.setScdHh11(0);
            result.setScdHh12(0);
            result.setScdHh13(0);
            result.setScdHh14(0);
            result.setScdHh15(0);
            result.setScdHh16(0);
            result.setScdHh17(0);
            result.setScdHh18(0);
            result.setScdHh19(0);
            result.setScdHh20(0);
            result.setScdHh21(0);
            result.setScdHh22(0);
            result.setScdHh23(0);
            result.setScdUhh00(0);
            result.setScdUhh01(0);
            result.setScdUhh02(0);
            result.setScdUhh03(0);
            result.setScdUhh04(0);
            result.setScdUhh05(0);
            result.setScdUhh06(0);
            result.setScdUhh07(0);
            result.setScdUhh08(0);
            result.setScdUhh09(0);
            result.setScdUhh10(0);
            result.setScdUhh11(0);
            result.setScdUhh12(0);
            result.setScdUhh13(0);
            result.setScdUhh14(0);
            result.setScdUhh15(0);
            result.setScdUhh16(0);
            result.setScdUhh17(0);
            result.setScdUhh18(0);
            result.setScdUhh19(0);
            result.setScdUhh20(0);
            result.setScdUhh21(0);
            result.setScdUhh22(0);
            result.setScdUhh23(0);
            result.setScdHits(0);
            result.setScdUniqHits(0);
        }
        
        model.addAttribute("result", result);
        model.addAttribute("searchSiteCounterDayVO", searchVO);
        model.addAttribute("siteList", siteList);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "counter_hour.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=day")
    public String day(@ModelAttribute("searchSiteCounterDayVO") SiteCounterDayVO searchVO,
                      @RequestParam(value = "sinId", required = false, defaultValue = "") String sinId,
                      @RequestParam(value = "searchStartDt", required = false, defaultValue = "") String searchStartDt,
                      @RequestParam(value = "searchEndDt", required = false, defaultValue = "") String searchEndDt,
                      HttpServletRequest request,
                      ModelMap model) throws Exception {
        
        SiteInfoVO searchSiteInfoVO = new SiteInfoVO();
        List<SiteInfoVO> siteList = siteInfoService.selectSiteInfoListAll(searchSiteInfoVO);
        if(siteList == null || (siteList != null && siteList.size() < 1)) {
            model.addAttribute("message", "site.info.nosite");
            return CmsManager.alert(CONTENT_PATH + "counter_result.jsp", model);
        } else if("".equals(sinId) && siteList != null) {
            return "redirect:/site/counter.do?act=day&sinId=" + siteList.get(0).getSinId();
        }
        
        String today = EgovDateUtil.formatDate(EgovDateUtil.getToday(), "-");
        
        if("".equals(searchStartDt)) {
            searchVO.setSearchStartDt(today.substring(0, 8).concat("01"));
        }
        if("".equals(searchEndDt)) {
            searchVO.setSearchEndDt(today);
        }
        
        int sumHits = 0;
        int sumUniqHits = 0;
        
        List<SiteCounterDayVO> resultList = siteCounterService.selectCounterDayListDay(searchVO);
        if(resultList != null) {
            for(int i = 0; i < resultList.size(); i++) {
                sumHits += resultList.get(i).getScdHits();
                sumUniqHits += resultList.get(i).getScdUniqHits();
            }
        }
        
        model.addAttribute("resultList", resultList);
        model.addAttribute("sumHits", sumHits);
        model.addAttribute("sumUniqHits", sumUniqHits);
        model.addAttribute("searchSiteCounterDayVO", searchVO);
        model.addAttribute("siteList", siteList);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "counter_day.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=week")
    public String week(@ModelAttribute("searchSiteCounterDayVO") SiteCounterDayVO searchVO,
                       @RequestParam(value = "sinId", required = false, defaultValue = "") String sinId,
                       @RequestParam(value = "searchStartDt", required = false, defaultValue = "") String searchStartDt,
                       @RequestParam(value = "searchEndDt", required = false, defaultValue = "") String searchEndDt,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        SiteInfoVO searchSiteInfoVO = new SiteInfoVO();
        List<SiteInfoVO> siteList = siteInfoService.selectSiteInfoListAll(searchSiteInfoVO);
        if(siteList == null || (siteList != null && siteList.size() < 1)) {
            model.addAttribute("message", "site.info.nosite");
            return CmsManager.alert(CONTENT_PATH + "counter_result.jsp", model);
        } else if("".equals(sinId) && siteList != null) {
            return "redirect:/site/counter.do?act=week&sinId=" + siteList.get(0).getSinId();
        }
        
        String today = EgovDateUtil.formatDate(EgovDateUtil.getToday(), "-");
        
        if("".equals(searchStartDt)) {
            searchVO.setSearchStartDt(today.substring(0, 8).concat("01"));
        }
        if("".equals(searchEndDt)) {
            searchVO.setSearchEndDt(today);
        }
        
        int sumHits = 0;
        int sumUniqHits = 0;
        
        List<SiteCounterDayVO> resultList = siteCounterService.selectCounterDayListWeek(searchVO);
        if(resultList != null) {
            for(int i = 0; i < resultList.size(); i++) {
                sumHits += resultList.get(i).getScdHits();
                sumUniqHits += resultList.get(i).getScdUniqHits();
            }
        }
        
        model.addAttribute("resultList", resultList);
        model.addAttribute("sumHits", sumHits);
        model.addAttribute("sumUniqHits", sumUniqHits);
        model.addAttribute("searchSiteCounterDayVO", searchVO);
        model.addAttribute("siteList", siteList);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "counter_week.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=month")
    public String month(@ModelAttribute("searchSiteCounterDayVO") SiteCounterDayVO searchVO,
                        @RequestParam(value = "sinId", required = false, defaultValue = "") String sinId,
                        @RequestParam(value = "searchStartDt", required = false, defaultValue = "") String searchStartDt,
                        @RequestParam(value = "searchEndDt", required = false, defaultValue = "") String searchEndDt,
                        HttpServletRequest request,
                        ModelMap model) throws Exception {
        
        SiteInfoVO searchSiteInfoVO = new SiteInfoVO();
        List<SiteInfoVO> siteList = siteInfoService.selectSiteInfoListAll(searchSiteInfoVO);
        if(siteList == null || (siteList != null && siteList.size() < 1)) {
            model.addAttribute("message", "site.info.nosite");
            return CmsManager.alert(CONTENT_PATH + "counter_result.jsp", model);
        } else if("".equals(sinId) && siteList != null) {
            return "redirect:/site/counter.do?act=month&sinId=" + siteList.get(0).getSinId();
        }
        
        String today = EgovDateUtil.formatDate(EgovDateUtil.getToday(), "-");
        
        if("".equals(searchStartDt)) {
            searchVO.setSearchStartDt(today.substring(0, 5).concat("01"));
        }
        if("".equals(searchEndDt)) {
            searchVO.setSearchEndDt(today.substring(0, 7));
        }
        
        int sumHits = 0;
        int sumUniqHits = 0;
        
        List<SiteCounterDayVO> resultList = siteCounterService.selectCounterDayListMonth(searchVO);
        if(resultList != null) {
            for(int i = 0; i < resultList.size(); i++) {
                sumHits += resultList.get(i).getScdHits();
                sumUniqHits += resultList.get(i).getScdUniqHits();
            }
        }
        
        model.addAttribute("resultList", resultList);
        model.addAttribute("sumHits", sumHits);
        model.addAttribute("sumUniqHits", sumUniqHits);
        model.addAttribute("searchSiteCounterDayVO", searchVO);
        model.addAttribute("siteList", siteList);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "counter_month.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=year")
    public String year(@ModelAttribute("searchSiteCounterDayVO") SiteCounterDayVO searchVO,
                       @RequestParam(value = "sinId", required = false, defaultValue = "") String sinId,
                       @RequestParam(value = "searchStartDt", required = false, defaultValue = "") String searchStartDt,
                       @RequestParam(value = "searchEndDt", required = false, defaultValue = "") String searchEndDt,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        SiteInfoVO searchSiteInfoVO = new SiteInfoVO();
        List<SiteInfoVO> siteList = siteInfoService.selectSiteInfoListAll(searchSiteInfoVO);
        if(siteList == null || (siteList != null && siteList.size() < 1)) {
            model.addAttribute("message", "site.info.nosite");
            return CmsManager.alert(CONTENT_PATH + "counter_result.jsp", model);
        } else if("".equals(sinId) && siteList != null) {
            return "redirect:/site/counter.do?act=year&sinId=" + siteList.get(0).getSinId();
        }
        
        String today = EgovDateUtil.formatDate(EgovDateUtil.getToday(), "-");
        
        if("".equals(searchStartDt)) {
            searchVO.setSearchStartDt(today.substring(0, 4));
        }
        if("".equals(searchEndDt)) {
            searchVO.setSearchEndDt(today.substring(0, 4));
        }
        
        int sumHits = 0;
        int sumUniqHits = 0;
        
        List<SiteCounterDayVO> resultList = siteCounterService.selectCounterDayListYear(searchVO);
        if(resultList != null) {
            for(int i = 0; i < resultList.size(); i++) {
                sumHits += resultList.get(i).getScdHits();
                sumUniqHits += resultList.get(i).getScdUniqHits();
            }
        }
        
        model.addAttribute("resultList", resultList);
        model.addAttribute("sumHits", sumHits);
        model.addAttribute("sumUniqHits", sumUniqHits);
        model.addAttribute("searchSiteCounterDayVO", searchVO);
        model.addAttribute("siteList", siteList);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "counter_year.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    /*페이지별 통계 추가건_곽민성*/
    @RequestMapping(params = "act=page")
    public String page(@ModelAttribute("searchSiteCounterMenuVO") SiteCounterMenuVO searchVO,
                       @RequestParam(value = "sinId", required = false, defaultValue = "") String sinId,
                       @RequestParam(value = "searchStartDt", required = false, defaultValue = "") String searchStartDt,
                       @RequestParam(value = "searchEndDt", required = false, defaultValue = "") String searchEndDt,
                       @RequestParam(value = "mode", required = false, defaultValue = "") String mode,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        SiteInfoVO searchSiteInfoVO = new SiteInfoVO();
        List<SiteInfoVO> siteList = siteInfoService.selectSiteInfoListAll(searchSiteInfoVO);
        
        if(siteList == null || (siteList != null && siteList.size() < 1)) {
            model.addAttribute("message", "site.info.nosite");
            return CmsManager.alert(CONTENT_PATH + "counter_result.jsp", model);
        } else if("".equals(sinId) && siteList != null) {
            return "redirect:/site/counter.do?act=page&sinId=" + siteList.get(0).getSinId();
        }
        
        String yesterday = EgovDateUtil.formatDate(EgovDateUtil.addDay(EgovDateUtil.getToday(), -1), "-");
        String startMonth = EgovDateUtil.formatDate(EgovDateUtil.addMonth(yesterday, -1), "-");
        
        if("".equals(searchStartDt)) {
            searchVO.setSearchStartDt(startMonth);
        }
        if("".equals(searchEndDt)) {
            searchVO.setSearchEndDt(yesterday);
        }
        
        searchVO.setSearchStartDt(searchVO.getSearchStartDt().replace("-", ""));
        searchVO.setSearchEndDt(searchVO.getSearchEndDt().replace("-", ""));
        
        int totalHits = 0;
        List<SiteCounterMenuVO> resultList = siteCounterService.selectCounterMenuList(searchVO);
        if(resultList != null && resultList.size() > 0) {
            for(int i = 0; i < resultList.size(); i++) {
                totalHits += resultList.get(i).getHits();
            }
        }
        model.addAttribute("searchSiteCounterMenuVO", searchVO);
        model.addAttribute("resultList", resultList);
        model.addAttribute("siteList", siteList);
        model.addAttribute("totalHits", totalHits);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "counter_page.jsp");
        
        if("excel".equals(mode)) {
            return "egovframework/coing/counter/counter_page_excel";
        }
        
        return "egovframework/coing/common/admin_view";
    }
    
}