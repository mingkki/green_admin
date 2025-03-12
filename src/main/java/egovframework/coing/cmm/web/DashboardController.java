package egovframework.coing.cmm.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.util.EgovDateUtil;
import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.exchange.service.ExchangeService;
import egovframework.coing.exchange.vo.ExchangeVO;
import egovframework.coing.point.service.PointLogService;
import egovframework.coing.site.service.SiteCounterService;
import egovframework.coing.site.service.SiteInfoService;
import egovframework.coing.site.vo.SiteCounterDayVO;
import egovframework.coing.site.vo.SiteInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/dashboard.do")
public class DashboardController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/", CmsManager.getModulePath());
    private final SiteInfoService siteInfoService;
    private final SiteCounterService siteCounterService;
    private final PointLogService pointLogService;
    private final ExchangeService exchangeService;
    private final CommonService commonService;
    
    @RequestMapping()
    public String index(@RequestParam(value = "sinId", required = false, defaultValue = "") String sinId, ModelMap model) throws Exception {
        
        SiteInfoVO searchSiteInfoVO = new SiteInfoVO();
        List<SiteInfoVO> siteList = siteInfoService.selectSiteInfoListAll(searchSiteInfoVO);
        if(siteList == null || (siteList != null && siteList.size() < 1)) {
            model.addAttribute("CONTENT_FILE", CONTENT_PATH + "dashboard.jsp");
            return "egovframework/coing/common/admin_view";
        }
        
        if("".equals(sinId)) {
            sinId = siteList.get(0).getSinId();
        }
        
        String today = EgovDateUtil.formatDate(EgovDateUtil.getToday(), "-");
        String yesterday = EgovDateUtil.formatDate(EgovDateUtil.addDay(today, -1), "-");
        String weekAgo = EgovDateUtil.formatDate(EgovDateUtil.addDay(today, -6), "-");
        
        SiteCounterDayVO searchVO = new SiteCounterDayVO();
        searchVO.setSinId(sinId);
        
        model.addAttribute("todayPoint", pointLogService.todayPoint());
        model.addAttribute("totalPoint", pointLogService.totalPoint());
        model.addAttribute("totalExchangePoint", pointLogService.totalExchangePoint());
        
        // 전체
        SiteCounterDayVO totalCounterVO = siteCounterService.selectCounterDayTotal(searchVO);
        
        // 어제
        searchVO.setSearchStartDt(yesterday);
        searchVO.setSearchEndDt(yesterday);
        SiteCounterDayVO yesterdayCounterVO = siteCounterService.selectCounterDayTotal(searchVO);
        
        // 오늘
        searchVO.setSearchStartDt(today);
        searchVO.setSearchEndDt(today);
        SiteCounterDayVO todayCounterVO = siteCounterService.selectCounterDayTotal(searchVO);
        
        // 이번주 일별
        int weekSumHits = 0;
        int weekSumUniqHits = 0;
        
        searchVO.setSearchStartDt(weekAgo);
        searchVO.setSearchEndDt(today);
        List<SiteCounterDayVO> weekCounterList = siteCounterService.selectCounterDayListDay(searchVO);
        if(weekCounterList != null) {
            for(int i = 0; i < weekCounterList.size(); i++) {
                weekSumHits += weekCounterList.get(i).getScdHits();
                weekSumUniqHits += weekCounterList.get(i).getScdUniqHits();
            }
        }
        
        List<ExchangeVO> exchangeList = exchangeService.select(new ExchangeVO());
        
        CodeDetailVO codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("EMD");
        List<CodeDetailVO> emdList = commonService.selectCodeDetailList(codeDetailVO);
        
        
        model.addAttribute("emdList", emdList);
        model.addAttribute("exchangeList", exchangeList);
        model.addAttribute("today", today);
        model.addAttribute("yesterday", yesterday);
        model.addAttribute("weekAgo", weekAgo);
        model.addAttribute("totalCounterVO", totalCounterVO);
        model.addAttribute("yesterdayCounterVO", yesterdayCounterVO);
        model.addAttribute("todayCounterVO", todayCounterVO);
        model.addAttribute("weekCounterList", weekCounterList);
        model.addAttribute("weekSumHits", weekSumHits);
        model.addAttribute("weekSumUniqHits", weekSumUniqHits);
        model.addAttribute("siteList", siteList);
        model.addAttribute("sinId", sinId);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "dashboard.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
}