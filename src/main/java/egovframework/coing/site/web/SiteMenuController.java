package egovframework.coing.site.web;

import egovframework.coing.board.service.BoardInfoService;
import egovframework.coing.board.vo.BoardInfoVO;
import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.service.ProgramService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.vo.GroupVO;
import egovframework.coing.cmm.vo.LevelVO;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.cmm.vo.ProgramVO;
import egovframework.coing.site.service.SiteInfoService;
import egovframework.coing.site.service.SiteMenuService;
import egovframework.coing.site.vo.SiteInfoVO;
import egovframework.coing.site.vo.SiteMenuVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/site/menu.do")
public class SiteMenuController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/site/", CmsManager.getModulePath());
    private final SiteMenuService siteMenuService;
    private final SiteInfoService siteInfoService;
    private final BoardInfoService boardInfoService;
    private final ProgramService programService;
    private final CommonService commonService;

    @RequestMapping()
    public String form(@ModelAttribute("searchSiteMenuVO") SiteMenuVO searchVO,
                       @ModelAttribute("writeSiteMenu") SiteMenuVO writeSiteMenuVO,
                       @RequestParam(value = "sinId", required = false, defaultValue = "") String sinId,
                       @RequestParam(value = "smeId", required = false, defaultValue = "0") int smeId,
                       @RequestParam(value = "smeParntsId", required = false, defaultValue = "0") int smeParntsId,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        SiteInfoVO searchSiteInfoVO = new SiteInfoVO();
        List<SiteInfoVO> siteList = siteInfoService.selectSiteInfoListAll(searchSiteInfoVO);
        if(siteList == null || (siteList != null && siteList.size() < 1)) {
            model.addAttribute("message", "site.info.nosite");
            return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
        } else if("".equals(sinId) && siteList != null) {
            System.out.println(sinId);
            return "redirect:/site/menu.do?sinId=" + siteList.get(0).getSinId();
        }
        
        SiteInfoVO siteInfoVO = getSiteInfo(request, sinId);
        if(siteInfoVO == null) {
            model.addAttribute("message", "site.info.nodata");
            return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
        }
        
        List<SiteMenuVO> allSiteMenuList = siteMenuService.selectSiteMenuList(searchVO);
        if(allSiteMenuList == null || allSiteMenuList.size() < 1) {
            writeSiteMenuVO.setSinId(sinId);
            writeSiteMenuVO.setSmeName("홈");
            writeSiteMenuVO.setSmeShowYn("Y");
            writeSiteMenuVO.setSmeUseYn("Y");
            siteMenuService.insertSiteMenu(writeSiteMenuVO);
            return "redirect:/site/menu.do?sinId=" + sinId;
        }
        
        List<SiteMenuVO> parentSiteMenuList = null;
        
        String command = (smeId < 1)
                ? "insert"
                : "update";
        
        if("update".equals(command)) {
            writeSiteMenuVO = siteMenuService.selectSiteMenu(searchVO);
            if(writeSiteMenuVO == null) {
                model.addAttribute("message", "site.menu.nodata");
                return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
            }
            String smeLimitView = EgovStringUtil.nullConvert(writeSiteMenuVO.getSmeLimitView());
            writeSiteMenuVO.setSmeLimitViewArr(smeLimitView.split(","));
            
            parentSiteMenuList = siteMenuService.selectParentSiteMenuList(searchVO);
            if(parentSiteMenuList != null && parentSiteMenuList.size() > 0) {
                parentSiteMenuList.remove(0);
            }
        } else {
            if(smeParntsId > 0) {
                SiteMenuVO parentSiteMenuVO = new SiteMenuVO();
                parentSiteMenuVO.setSinId(sinId);
                parentSiteMenuVO.setSmeId(smeParntsId);
                parentSiteMenuVO = siteMenuService.selectSiteMenu(parentSiteMenuVO);
                if(parentSiteMenuVO == null) {
                    model.addAttribute("message", "site.menu.noParntsData");
                    return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
                }
                
                writeSiteMenuVO.setSmeTheme(EgovStringUtil.nullConvert(parentSiteMenuVO.getSmeTheme()));
                writeSiteMenuVO.setSmeLayout(EgovStringUtil.nullConvert(parentSiteMenuVO.getSmeLayout()));
                writeSiteMenuVO.setSmePermitViewGubun(parentSiteMenuVO.getSmePermitViewGubun());
                
                SiteMenuVO vo = new SiteMenuVO();
                vo.setSinId(sinId);
                vo.setSmeId(smeParntsId);
                parentSiteMenuList = siteMenuService.selectParentSiteMenuList(vo);
                if(parentSiteMenuList != null && parentSiteMenuList.size() > 0) {
                    parentSiteMenuList.remove(0);
                }
                parentSiteMenuList.add(parentSiteMenuVO);
            } else {
                writeSiteMenuVO.setSmeMainmenuYn("Y");
                writeSiteMenuVO.setSmePermitViewGubun(1);
            }
        }
        
        List<LevelVO> levelList = commonService.selectLevelList();
        List<GroupVO> groupList = commonService.selectGroupList();
        List<SiteMenuVO> treeSiteMenuList = getTreeSiteMenuList(allSiteMenuList);
        List<BoardInfoVO> boardList = getBoardList();
        List<ProgramVO> programList = getProgramList();
        
        if(!"".equals(EgovStringUtil.nullConvert(writeSiteMenuVO.getOrdId()))) {

        }
        
        int rootMenuId = siteMenuService.selectSiteMenuRootMenuId(searchVO);
        
        model.addAttribute("command", command);
        model.addAttribute("siteInfoVO", siteInfoVO);
        model.addAttribute("siteList", siteList);
        model.addAttribute("allSiteMenuList", allSiteMenuList);
        model.addAttribute("parentSiteMenuList", parentSiteMenuList);
        model.addAttribute("treeSiteMenuList", treeSiteMenuList);
        model.addAttribute("rootMenuId", rootMenuId);
        model.addAttribute("writeSiteMenu", writeSiteMenuVO);
        model.addAttribute("groupList", groupList);
        model.addAttribute("levelList", levelList);
        model.addAttribute("boardList", boardList);
        model.addAttribute("programList", programList);

        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "menu_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.POST)
    public String write(@ModelAttribute("searchSiteMenuVO") SiteMenuVO searchVO,
                        @ModelAttribute("writeSiteMenu") SiteMenuVO writeSiteMenuVO,
                        @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                        HttpServletRequest reuqest,
                        ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        writeSiteMenuVO.setSmeMainmenuYn(EgovStringUtil.nullConvert(writeSiteMenuVO.getSmeMainmenuYn(), "N"));
        writeSiteMenuVO.setSmeLoginViewYn(EgovStringUtil.nullConvert(writeSiteMenuVO.getSmeLoginViewYn(), "N"));
        writeSiteMenuVO.setSmeLimitView(EgovStringUtil.join(writeSiteMenuVO.getSmeLimitViewArr(), ","));
        
        // 메뉴 종류가 게시판인 경우
        String smeType = EgovStringUtil.nullConvert(writeSiteMenuVO.getSmeType());
        if("BOARD".equals(smeType)) {
            String smeLinkBrdId = EgovStringUtil.nullConvert(writeSiteMenuVO.getSmeLinkBrdId());
            if(!"".equals(smeLinkBrdId)) {
                writeSiteMenuVO.setSmeLinkUrl("/board/" + smeLinkBrdId + ".do");
            }
        } else if("PROGRAM".equals(smeType)) {
            int smeLinkPrgId = writeSiteMenuVO.getSmeLinkPrgId();
            ProgramVO programVO = new ProgramVO();
            programVO.setPrgId(smeLinkPrgId);
            programVO.setPrgUseYn("Y");
            programVO = programService.selectProgram(programVO);
            if(programVO == null) {
                model.addAttribute("message", "program.nodata");
                return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
            }
            writeSiteMenuVO.setSmeLinkUrl(programVO.getPrgUrl());
        }
        
        String message = "";
        
        if("insert".equals(command)) {
            message = "common.success.insert";
            writeSiteMenuVO.setSmeRegId(loginVO.getId());
            writeSiteMenuVO.setSmeRegIp(reuqest.getRemoteAddr());
            siteMenuService.insertSiteMenu(writeSiteMenuVO);
        } else {
            SiteMenuVO vo = siteMenuService.selectSiteMenu(searchVO);
            if(vo == null) {
                model.addAttribute("message", "site.menu.nodata");
                return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
            }
            
            message = "common.success.update";
            writeSiteMenuVO.setSmeLft(vo.getSmeLft());
            writeSiteMenuVO.setSmeRgt(vo.getSmeRgt());
            writeSiteMenuVO.setSmeUpdtId(loginVO.getId());
            writeSiteMenuVO.setSmeUpdtIp(reuqest.getRemoteAddr());
            siteMenuService.updateSiteMenu(writeSiteMenuVO);
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
    }
    
    @RequestMapping(params = "act=delete", method = RequestMethod.POST)
    public String delete(@ModelAttribute("searchSiteMenuVO") SiteMenuVO searchVO,
                         @RequestParam(value = "sinId", required = true) String sinId,
                         @RequestParam(value = "smeId", required = true) int smeId,
                         ModelMap model) throws Exception {
        
        SiteMenuVO vo = siteMenuService.selectSiteMenu(searchVO);
        if(vo == null) {
            model.addAttribute("message", "site.menu.nodata");
            return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
        }
        
        siteMenuService.deleteSiteMenu(vo);
        
        model.addAttribute("message", "common.success.delete");
        return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
    }
    
    @RequestMapping(params = "act=move", method = RequestMethod.GET)
    public String moveForm(@ModelAttribute("searchSiteMenuVO") SiteMenuVO searchVO,
                           @RequestParam(value = "sinId", required = true) String sinId,
                           ModelMap model) throws Exception {
        
        int rootMenuId = siteMenuService.selectSiteMenuRootMenuId(searchVO);
        
        List<SiteMenuVO> list = siteMenuService.selectSiteMenuList(searchVO);
        
        model.addAttribute("rootMenuId", rootMenuId);
        model.addAttribute("siteMenuList", list);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "menu_move.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=move", method = RequestMethod.POST)
    public String moveWrite(@ModelAttribute("searchSiteMenuVO") SiteMenuVO searchVO,
                            @RequestParam(value = "sinId", required = true) String sinId,
                            @RequestParam(value = "jsonData", required = true) String jsonData,
                            ModelMap model) throws Exception {
        
        siteMenuService.updateSiteMenuMove(sinId, jsonData);
        
        model.addAttribute("message", "common.success.move");
        return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
    }
    
    @RequestMapping(params = "act=distribute", method = RequestMethod.POST)
    public String delete(@ModelAttribute("searchSiteMenuVO") SiteMenuVO searchVO,
                         @RequestParam(value = "sinId", required = true) String sinId,
                         HttpServletRequest request,
                         ModelMap model) throws Exception {
        
        siteMenuService.siteMenuDistribute(sinId);
        
        model.addAttribute("message", "site.menu.distribute");
        return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
    }
    
    @RequestMapping(params = {"act=search", "type=board"})
    public String searchBoard(@RequestParam(value = "sinId", required = true) String sinId, ModelMap model) throws Exception {
        
        List<BoardInfoVO> resultList = getBoardList();
        
        model.addAttribute("resultList", resultList);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "search_board.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = {"act=search", "type=program"})
    public String searchProgram(@RequestParam(value = "sinId", required = true) String sinId, ModelMap model) throws Exception {
        
        List<ProgramVO> resultList = getProgramList();
        
        model.addAttribute("resultList", resultList);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "search_program.jsp");
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
    
    private List<BoardInfoVO> getBoardList() throws Exception {
        
        BoardInfoVO searchVO = new BoardInfoVO();
        searchVO.setBrdUseYn("Y");
        return boardInfoService.selectBoardInfoListAll(searchVO);
    }
    
    private List<ProgramVO> getProgramList() throws Exception {
        
        ProgramVO searchVO = new ProgramVO();
        searchVO.setPrgUseYn("Y");
        return programService.selectProgramListAll(searchVO);
    }
    
    private List<SiteMenuVO> getTreeSiteMenuList(List<SiteMenuVO> list) throws Exception {
        
        List<SiteMenuVO> rlist = new ArrayList<SiteMenuVO>();
        
        if(list != null && list.size() > 0) {
            for(int i = 0; i < list.size(); i++) {
                if(list.get(i).getSmeParntsId() < 1) {
                    continue;
                }
                
                String menuNm = list.get(i).getSmeName();
                
                if(list.get(i).getSmeLvl() > 2) {
                    String lpad = StringUtils.leftPad("", list.get(i).getSmeLvl() - 2, '*').replace("*", "　");
                    menuNm = lpad + " └ " + list.get(i).getSmeName();
                }
                
                SiteMenuVO vo = new SiteMenuVO();
                vo.setSmeId(list.get(i).getSmeId());
                vo.setSmeName(menuNm);
                
                rlist.add(vo);
            }
        }
        
        return rlist;
    }
}