package egovframework.coing.site.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.Globals;
import egovframework.coing.cmm.util.EgovFileTool;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.site.service.SiteContentService;
import egovframework.coing.site.service.SiteInfoService;
import egovframework.coing.site.service.SiteMenuService;
import egovframework.coing.site.vo.SiteContentVO;
import egovframework.coing.site.vo.SiteInfoVO;
import egovframework.coing.site.vo.SiteMenuVO;
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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/site/content.do")
public class SiteContentController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/site/", CmsManager.getModulePath());
    private final EgovPropertyService propertiesService;
    private final SiteContentService siteContentService;
    private final SiteMenuService siteMenuService;
    private final SiteInfoService siteInfoService;

    /**
     * 프로그램내에서 사용할 파라미터를 초기화한다.
     */
    private void initParam(String popType) {
        
        Map<String, Object> param = new HashMap<String, Object>();
    }
    
    /**
     * 콘텐츠 관리
     *
     * --프로세스--
     * 1. 메뉴 네비게이터를 들고 온다.
     * 2. 클릭시 해당 메뉴가 콘텐츠 인지 아닌지 여부를 판단한다.
     * 3. 콘텐츠 일경우 콘텐츠 내용을 가져오고 아닐경우 콘텐츠가 아니라고 알려준다.
     * 4. 해당 콘텐츠를 수정하고 저장하면 데이터 베이스에 저장
     * 5. 출판하면 소스 히스토리에 저장
     * 6. 복원 누르면 해당 데이터 불러옴
     * 7. 저장하면 콘텐츠 데이터 업데이트
     * 8. 복원된 날짜 아이디 기록
     *
     * --기능--
     * 1. 사이트 리스트 들고오기
     * 2. 메뉴 리스트 가져오기
     * 3. 메뉴 정보 가져오기
     * 4. 콘텐츠 내용 가져오기
     * 5. 콘텐츠 히스토리 리스트 가져오기
     * 6. 히스토리 내용 가져오기
     * 7. 히스토리를 콘텐츠로 복원하기
     */
    @RequestMapping()
    public String form(@ModelAttribute("searchSiteMenuVO") SiteMenuVO searchMenuVO,
                       @RequestParam(value = "sinId", required = false, defaultValue = "") String sinId,
                       @RequestParam(value = "smeId", required = false, defaultValue = "0") int smeId,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       ModelMap model) throws Exception {
        
        //사이트 리스트 들고오기
        SiteInfoVO siteVO = new SiteInfoVO();
        List<SiteInfoVO> siteList = siteInfoService.selectSiteInfoListAll(siteVO);
        
        //사이트 정보 유효성 체크
        if(siteList == null || (siteList != null && siteList.size() < 1)) {
            model.addAttribute("message", "site.info.nosite");
            return CmsManager.alert(CONTENT_PATH + "content_result.jsp", model);
        } else if("".equals(sinId) && siteList != null) {
            return "redirect:/site/content.do?sinId=" + siteList.get(0).getSinId();
        }
        
        //사이트 메뉴 리스트 가져오기
        SiteMenuVO menuVO = new SiteMenuVO();
        menuVO.setSinId(sinId);
        List<SiteMenuVO> allSiteMenuList = siteMenuService.selectSiteMenuList(menuVO);
        List<SiteMenuVO> parentSiteMenuList = null;
        
        //메뉴 정보 가져오기
        SiteMenuVO siteMenuVO = new SiteMenuVO();
        if(smeId > 0) {
            siteMenuVO = siteMenuService.selectSiteMenu(searchMenuVO);
            if(siteMenuVO == null) {
                model.addAttribute("message", "site.menu.nodata");
                return CmsManager.alert(CONTENT_PATH + "content_result.jsp", model);
            }
            parentSiteMenuList = siteMenuService.selectParentSiteMenuList(siteMenuVO);
            if(parentSiteMenuList != null && parentSiteMenuList.size() > 0) {
                parentSiteMenuList.remove(0);
            }
        }
        
        SiteContentVO contentVO = null;
        PaginationInfo paginationInfo = new PaginationInfo();
        
        if("HTML".equals(siteMenuVO.getSmeType())) {
            //콘텐츠 내용 가져오기
            contentVO = new SiteContentVO();
            contentVO.setSinId(sinId);
            contentVO.setSmeId(smeId);
            contentVO = siteContentService.selectSiteContentByMenu(contentVO);
            
            String command = (contentVO == null)
                    ? "insert"
                    : "update";
            

            int totCnt = 0;
            if(contentVO != null) {

            } else {
                contentVO = new SiteContentVO();
            }
            
            paginationInfo.setTotalRecordCount(totCnt);
            System.out.println("TotalRecordCount = " + totCnt);
            
            String fileNm = smeId > 0
                    ? smeId + ".jsp"
                    : "main.jsp";
            String filePath = Globals.DISTRIBUTE_PATH + "/" + sinId + "/content/" + fileNm;
            filePath = filePath.replace('\\', File.separatorChar).replace('/', File.separatorChar);
            
            model.addAttribute("command", command);
            model.addAttribute("writeSiteContent", contentVO);
            model.addAttribute("lastUpdtDate", EgovFileTool.getUpdtDate(filePath, "yyyy-MM-dd HH:mm:ss"));
        }
        
        paramMap.put("pageIndex", null);
        
        model.addAttribute("parentSiteMenuList", parentSiteMenuList);
        model.addAttribute("siteList", siteList);
        model.addAttribute("siteMenuVO", siteMenuVO);
        model.addAttribute("rootMenuId", siteMenuService.selectSiteMenuRootMenuId(menuVO));
        model.addAttribute("allSiteMenuList", allSiteMenuList);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("paginationQueryString", MapQuery.urlEncodeUTF8(paramMap));
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "content_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.POST)
    public String write(@ModelAttribute("searchSiteMenuVO") SiteMenuVO searchVO,
                        @ModelAttribute("writeSiteContent") SiteContentVO writeSiteContentVO,
                        @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                        HttpServletRequest reuqest,
                        ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        String message = "";
        
        if("insert".equals(command)) {
            
            message = "common.success.insert";
            writeSiteContentVO.setScoRegId(loginVO.getId());
            writeSiteContentVO.setScoRegIp(reuqest.getRemoteAddr());
            siteContentService.insertSiteContent(writeSiteContentVO);
            
        } else {
    		
    		/*SiteContentVO origVO = siteContentService.selectSiteContent(writeSiteContentVO);
    		if (origVO == null) {
    			model.addAttribute("message", message);
    			return CmsManager.alert(CONTENT_PATH + "content_result.jsp", model);
    		} else {*/
            
            SiteContentVO vo = new SiteContentVO();
            vo.setSinId(writeSiteContentVO.getSinId());
            vo.setSmeId(writeSiteContentVO.getSmeId());
            vo = siteContentService.selectSiteContentByMenu(writeSiteContentVO);
            
            message = "common.success.update";
            writeSiteContentVO.setScoUpdtId(loginVO.getId());
            writeSiteContentVO.setScoUpdtIp(reuqest.getRemoteAddr());
            siteContentService.updateSiteContent(writeSiteContentVO);
            
            if(vo != null) {
                try {
                    String orignlSourceCn = EgovStringUtil.nullConvert(vo.getContent());
                    if(!orignlSourceCn.equals(writeSiteContentVO.getContent())) {

                    }
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        
        model.addAttribute("message", message);
        
        return CmsManager.alert(CONTENT_PATH + "content_result.jsp", model);
    }
    
    @RequestMapping(params = "act=publish", method = RequestMethod.POST)
    public String publish(@ModelAttribute("searchSiteMenuVO") SiteMenuVO searchMenuVO,
                          @ModelAttribute("searchSiteContentVO") SiteContentVO searchContentVO,
                          @RequestParam(value = "sinId", required = true) String sinId,
                          @RequestParam(value = "smeId", required = true) int smeId,
                          @RequestParam(value = "contentId", required = true) int contentId,
                          HttpServletRequest request,
                          ModelMap model) throws Exception {
        
        System.out.println("searchSiteContentVO.getContent() = " + searchContentVO.getContent());
        System.out.println("searchSiteContentVO.getSinId() = " + searchContentVO.getSinId());
        System.out.println("searchSiteContentVO.getSmeId() = " + searchContentVO.getSmeId());
        System.out.println("searchSiteContentVO.getContentId() = " + searchContentVO.getContentId());
        
        SiteContentVO vo = siteContentService.selectSiteContent(searchContentVO);
        if(vo == null) {
            model.addAttribute("message", "site.content.nodata");
            return CmsManager.alert(CONTENT_PATH + "content_result.jsp", model);
        }
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        vo.setScoPubId(loginVO.getId());
        vo.setScoPubIp(request.getRemoteAddr());
        siteContentService.updateSiteContentPublish(vo);
        
        model.addAttribute("message", "common.success.process");
        return CmsManager.alert(CONTENT_PATH + "content_result.jsp", model);
    }    
    /*
    private SiteInfoVO getSiteInfo(HttpServletRequest request, String sinId) throws Exception {

    	SiteInfoVO vo = null;
    	if (!"".equals(sinId)) {
    		vo = new SiteInfoVO();
    		vo.setSinId(sinId);
    		vo = siteInfoService.selectSiteInfo(vo);
    	}

    	return vo;
    }  */
    
    @RequestMapping(params = "act=restore", method = RequestMethod.POST)
    public String restore(@ModelAttribute("writeSiteContent") SiteContentVO writeSiteContentVO,
                          @RequestParam(value = "historyId", required = true) int historyId,
                          HttpServletRequest request,
                          ModelMap model) throws Exception {
        
        String message = "common.success.restore";
        siteContentService.restoreSiteContent(writeSiteContentVO, historyId);
        
        model.addAttribute("message", message);
        
        return CmsManager.alert(CONTENT_PATH + "content_result.jsp", model);
    }
    
}