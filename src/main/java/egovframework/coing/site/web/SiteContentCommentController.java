package egovframework.coing.site.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.site.service.SiteContentService;
import egovframework.coing.site.service.SiteInfoService;
import egovframework.coing.site.service.SiteMenuService;
import egovframework.coing.site.vo.SiteContentCommentVO;
import egovframework.coing.site.vo.SiteInfoVO;
import egovframework.coing.site.vo.SiteMenuVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/site/content/comment.do")
public class SiteContentCommentController {
    
    private final EgovPropertyService propertiesService;
    private final SiteMenuService siteMenuService;
    private final SiteContentService siteContentService;
    private final SiteInfoService siteInfoService;
    
    private String CONTENT_PATH = String.format("%s/egovframework/coing/site/", CmsManager.getModulePath());

    /*private void checkAdminSinId(HttpServletRequest request, SiteMenuVO searchSiteMenuVO) throws Exception {
    	
    	ManagerVO ManagerVO = (ManagerVO)request.getAttribute("MANAGER_VO");
    	String[] adminSinIdsArr = ManagerVO.getAdminSiteIdArr();
		if (!EgovUserDetailsHelper.isMasterAdmin()) {
			if (adminSinIdsArr != null && adminSinIdsArr.length > 0) {
				if(!Arrays.asList(adminSinIdsArr).contains(searchSiteMenuVO.getSinId())) {
					ModelAndView mv = new ModelAndView("egovframework/coing/access_denied");
					throw new ModelAndViewDefiningException(mv);
				}
			}
		}
    }*/
    
    @RequestMapping()
    public String write(@ModelAttribute("searchSiteContentCommentVO") SiteContentCommentVO searchSiteContentCommentVO,
                        @ModelAttribute("searchSiteMenuVO") SiteMenuVO searchSiteMenuVO,
                        @RequestParam(value = "sinId", required = false, defaultValue = "") String sinId,
                        @RequestParam(value = "smeId", required = false, defaultValue = "0") int smeId,
                        @RequestParam Map<String, Object> paramMap,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        ModelMap model) throws Exception {
        
        /*ManagerVO managerVO = (ManagerVO)request.getAttribute("MANAGER_VO");//EMPLOYER_INFO DB에 없음
         */    	/*
    	if( sinId.equals("") && !siteId.equals("") )
    	{
    		sinId = siteId;
    	}
    	searchSiteContentCommentVO.setSinId( sinId );
    	System.out.println("sinId===================" + sinId );
    	*/
        
        SiteInfoVO searchSiteInfoVO = new SiteInfoVO();
    	/*if (!EgovUserDetailsHelper.isMasterAdmin()) {
    		searchSiteInfoVO.setAdminSiteIdArr(managerVO.getAdminSiteIdArr());
    	}*/
        List<SiteInfoVO> siteList = siteInfoService.selectSiteInfoListAll(searchSiteInfoVO);
        
        System.out.println("siteList===================" + siteList.size());
        
        if(siteList == null || (siteList != null && siteList.size() < 1)) {
            model.addAttribute("message", "site.info.nosite");
            return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
        } else if("".equals(sinId) && siteList != null) {
            System.out.println("3===================");
            return "redirect:/site/content/comment.do?sinId=" + siteList.get(0).getSinId();
        }
        
        /*checkAdminSinId(request, searchSiteMenuVO);*/
        
        SiteInfoVO siteInfoVO = getSiteInfo(request, sinId);
        if(siteInfoVO == null) {
            model.addAttribute("message", "site.info.nodata");
            return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
        }
        
        List<SiteMenuVO> allSiteMenuList = siteMenuService.selectSiteMenuList(searchSiteMenuVO);
        
        SiteMenuVO siteMenuVO = new SiteMenuVO();
        
        if(smeId > 0) {
            siteMenuVO.setSinId(sinId);
            siteMenuVO.setSmeId(smeId);
            siteMenuVO = siteMenuService.selectSiteMenu(siteMenuVO);
            if(siteMenuVO != null) {
                
                searchSiteContentCommentVO.setSearchMenuLft(siteMenuVO.getSmeLft());
                searchSiteContentCommentVO.setSearchMenuRgt(siteMenuVO.getSmeRgt());
            }
        }
        
        searchSiteContentCommentVO.setPageUnit(propertiesService.getInt("pageUnit"));
        searchSiteContentCommentVO.setPageSize(propertiesService.getInt("pageSize"));
        
        PaginationInfo paginationInfo = new PaginationInfo();
        
        paginationInfo.setCurrentPageNo(searchSiteContentCommentVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(searchSiteContentCommentVO.getPageUnit());
        paginationInfo.setPageSize(searchSiteContentCommentVO.getPageSize());
        
        searchSiteContentCommentVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        searchSiteContentCommentVO.setLastIndex(paginationInfo.getLastRecordIndex());
        searchSiteContentCommentVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(paramMap);
        param.put("pageIndex", null);
        String paginationQueryString = MapQuery.urlEncodeUTF8(param);
        
        Map<String, Object> map = siteContentService.selectSiteContentCommentList(searchSiteContentCommentVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));
        
        paginationInfo.setTotalRecordCount(totCnt);
        
        model.addAttribute("siteMenuVO", siteMenuVO);
        model.addAttribute("allSiteMenuList", allSiteMenuList);
        model.addAttribute("siteList", siteList);
        
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("paginationQueryString", paginationQueryString);
        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "content_comment_list.jsp");
        
        return "egovframework/coing/common/admin_view";
    }
    
    private SiteInfoVO getSiteInfo(HttpServletRequest request, String sinId) throws Exception {
        
        SiteInfoVO vo = null;
        
        if(!"".equals(sinId)) {
            vo = new SiteInfoVO();
            vo.setSinId(sinId);
            vo = siteInfoService.selectSiteInfo(vo);
        }
        
        return vo;
    }
    
}