package egovframework.coing.site.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.cmm.vo.GroupVO;
import egovframework.coing.cmm.vo.LevelVO;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.site.service.SiteInfoService;
import egovframework.coing.site.vo.SiteInfoVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/site/info.do")
public class SiteInfoController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/site/", CmsManager.getModulePath());
    private final EgovPropertyService propertiesService;
    private final SiteInfoService siteInfoService;
    private final CommonService commonService;
    
    /**
     * 프로그램내에서 사용할 파라미터를 초기화한다.
     */
    private void initParam(SiteInfoVO searchVO) {
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sinUseYn", searchVO.getSinUseYn());
        param.put("searchCondition", searchVO.getSearchCondition());
        param.put("searchKeyword", searchVO.getSearchKeyword());
        param.put("pageIndex", searchVO.getPageIndex());
        
        searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
    }
    
    /**
     * 사이트 정보 목록을 조회한다.
     */
    @RequestMapping()
    public String list(@ModelAttribute("searchSiteInfoVO") SiteInfoVO searchVO,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
        searchVO.setPageSize(propertiesService.getInt("pageSize"));
        
        PaginationInfo paginationInfo = new PaginationInfo();
        
        paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
        paginationInfo.setPageSize(searchVO.getPageSize());
        
        searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
        searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(paramMap);
        param.put("pageIndex", null);
        String paginationQueryString = MapQuery.urlEncodeUTF8(param);
        
        Map<String, Object> map = siteInfoService.selectSiteInfoList(searchVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));
        
        paginationInfo.setTotalRecordCount(totCnt);
        
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("paginationQueryString", paginationQueryString);
        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "info_list.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    /**
     * 사이트 정보 등록/수정 폼을 출력한다.
     */
    @RequestMapping(params = "act=write", method = RequestMethod.GET)
    public String form(@ModelAttribute("searchSiteInfoVO") SiteInfoVO searchVO,
                       @ModelAttribute("writeSiteInfo") SiteInfoVO writeSiteInfoVO,
                       @RequestParam(value = "sinId", required = false, defaultValue = "") String sinId,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        String command = ("".equals(sinId))
                ? "insert"
                : "update";
        
        if("update".equals(command)) {
            writeSiteInfoVO = siteInfoService.selectSiteInfo(searchVO);
            if(writeSiteInfoVO == null) {
                model.addAttribute("message", "site.info.nodata");
                return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
            }
            
            String sinLimitView = EgovStringUtil.nullConvert(writeSiteInfoVO.getSinLimitView());
            writeSiteInfoVO.setSinLimitViewArr(sinLimitView.split(","));
        } else {
            writeSiteInfoVO.setSinPermitViewGubun(1);
        }
        
        // 등급 및 그룹 리스트
        List<LevelVO> levelList = commonService.selectLevelList();
        List<GroupVO> groupList = commonService.selectGroupList();
        
        // 사이트 언어 리스트
        CodeDetailVO cddVO = new CodeDetailVO();
        cddVO.setSearchCodId("LANG");
        List<CodeDetailVO> langList = commonService.selectCodeDetailList(cddVO);
        
        model.addAttribute("command", command);
        model.addAttribute("levelList", levelList);
        model.addAttribute("groupList", groupList);
        model.addAttribute("langList", langList);
        model.addAttribute("writeSiteInfo", writeSiteInfoVO);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "info_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    /**
     * 사이트 정보를 등록/수정한다.
     */
    @RequestMapping(params = "act=write", method = RequestMethod.POST)
    public String write(@ModelAttribute("searchSiteInfoVO") SiteInfoVO searchVO,
                        @ModelAttribute("writeSiteInfo") SiteInfoVO writeSiteInfoVO,
                        @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                        @RequestParam Map<String, Object> paramMap,
                        HttpServletRequest request,
                        ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        writeSiteInfoVO.setSinLimitView(EgovStringUtil.join(writeSiteInfoVO.getSinLimitViewArr(), ","));
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        String message = "";
        
        if("insert".equals(command)) {
            // 사이트 ID 중복체크
            int cnt = siteInfoService.checkSiteId(writeSiteInfoVO);
            if(cnt > 0) {
                model.addAttribute("message", "site.info.usedata");
                return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
            }
            // 사이트 도메인 중복체크
            String writeSinDomain = EgovStringUtil.nullConvert(writeSiteInfoVO.getSinDomain());
            if(!"".equals(writeSinDomain)) {
                int cnt2 = siteInfoService.checkSiteDomain(writeSiteInfoVO);
                if(cnt2 > 0) {
                    model.addAttribute("message", "site.info.useDomain");
                    return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
                }
            }
            
            message = "common.success.insert";
            writeSiteInfoVO.setSinRegId(loginVO.getId());
            writeSiteInfoVO.setSinRegIp(request.getRemoteAddr());
            siteInfoService.insertSiteInfo(writeSiteInfoVO);
        } else {
            // 사이트 정보 원글 정보
            SiteInfoVO origVO = siteInfoService.selectSiteInfo(searchVO);
            if(origVO == null) {
                model.addAttribute("message", "site.info.nodata");
                return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
            }
            // 사이트 도메인을 수정한 경우 중복체크
            String origSinDomain = EgovStringUtil.nullConvert(origVO.getSinDomain());
            String writeSinDomain = EgovStringUtil.nullConvert(writeSiteInfoVO.getSinDomain());
            if(!"".equals(writeSinDomain) && !origSinDomain.equals(writeSinDomain)) {
                int cnt = siteInfoService.checkSiteDomain(writeSiteInfoVO);
                if(cnt > 0) {
                    model.addAttribute("message", "site.info.useDomain");
                    return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
                }
            }
            message = "common.success.update";
            writeSiteInfoVO.setSinUpdtId(loginVO.getId());
            writeSiteInfoVO.setSinUpdtIp(request.getRemoteAddr());
            siteInfoService.updateSiteInfo(writeSiteInfoVO);
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
    }
    
    /**
     * 사이트 정보를 삭제한다.
     */
    @RequestMapping(params = "act=delete", method = RequestMethod.POST)
    public String delete(@ModelAttribute("searchSiteInfoVO") SiteInfoVO searchVO,
                         @RequestParam(value = "sinId", required = true) String sinId,
                         HttpServletRequest request,
                         ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        SiteInfoVO vo = siteInfoService.selectSiteInfo(searchVO);
        if(vo == null) {
            model.addAttribute("message", "site.info.nodata");
            return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
        }
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        vo.setSinDelId(loginVO.getId());
        vo.setSinDelIp(request.getRemoteAddr());
        siteInfoService.deleteSiteInfo(vo);
        
        model.addAttribute("message", "common.success.delete");
        return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
    }
    
}