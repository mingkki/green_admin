package egovframework.coing.cmm.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.AdminMenuService;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.vo.AdminMenuVO;
import egovframework.coing.cmm.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admenu.do")
public class AdminMenuController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/", CmsManager.getModulePath());
    private final AdminMenuService adminMenuService;
    
    @RequestMapping()
    public String form(@ModelAttribute("searchAdminMenuVO") AdminMenuVO searchVO,
                       @ModelAttribute("writeAdminMenu") AdminMenuVO writeAdminMenuVO,
                       @RequestParam(value = "ameId", required = false, defaultValue = "0") int ameId,
                       @RequestParam(value = "ameParntsId", required = false, defaultValue = "0") int ameParntsId,
                       ModelMap model) throws Exception {
        
        List<AdminMenuVO> allAdminMenuList = adminMenuService.selectAdminMenuList(searchVO);
        if(allAdminMenuList.size() < 1) {
            writeAdminMenuVO.setAmeName("홈");
            writeAdminMenuVO.setAmeShowYn("Y");
            writeAdminMenuVO.setAmeUseYn("Y");
            adminMenuService.insertAdminMenu(writeAdminMenuVO);
            return "redirect:/admenu.do";
        }
        
        List<AdminMenuVO> parentAdminMenuList = null;
        
        String command = (ameId < 1)
                ? "insert"
                : "update";
        
        if("update".equals(command)) {
            writeAdminMenuVO = adminMenuService.selectAdminMenu(searchVO);
            if(writeAdminMenuVO == null) {
                model.addAttribute("message", "admin.menu.nodata");
                return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
            }
            
            parentAdminMenuList = adminMenuService.selectParentAdminMenuList(searchVO);
            parentAdminMenuList.remove(0);
        } else {
            if(ameParntsId > 0) {
                AdminMenuVO parentAdminMenuVO = new AdminMenuVO();
                parentAdminMenuVO.setAmeId(ameParntsId);
                parentAdminMenuVO = adminMenuService.selectAdminMenu(parentAdminMenuVO);
                if(parentAdminMenuVO == null) {
                    model.addAttribute("message", "admin.menu.noParntsData");
                    return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
                }
                
                AdminMenuVO vo = new AdminMenuVO();
                vo.setAmeId(ameParntsId);
                parentAdminMenuList = adminMenuService.selectParentAdminMenuList(vo);
                parentAdminMenuList.remove(0);
                parentAdminMenuList.add(parentAdminMenuVO);
            }
        }
        
        int rootMenuId = adminMenuService.selectAdminMenuRootMenuId(null);
        
        model.addAttribute("command", command);
        model.addAttribute("allAdminMenuList", allAdminMenuList);
        model.addAttribute("parentAdminMenuList", parentAdminMenuList);
        model.addAttribute("rootMenuId", rootMenuId);
        model.addAttribute("writeAdminMenu", writeAdminMenuVO);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "menu_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.POST)
    public String write(@ModelAttribute("searchAdminMenuVO") AdminMenuVO searchVO,
                        @ModelAttribute("writeAdminMenu") AdminMenuVO writeAdminMenuVO,
                        @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                        HttpServletRequest reuqest,
                        ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        String message = "";
        
        if("insert".equals(command)) {
            message = "common.success.insert";
            writeAdminMenuVO.setAmeRegId(loginVO.getId());
            writeAdminMenuVO.setAmeRegIp(reuqest.getRemoteAddr());
            adminMenuService.insertAdminMenu(writeAdminMenuVO);
        } else {
            AdminMenuVO vo = adminMenuService.selectAdminMenu(searchVO);
            if(vo == null) {
                model.addAttribute("message", "admin.menu.nodata");
                return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
            }
            
            message = "common.success.update";
            writeAdminMenuVO.setAmeUpdtId(loginVO.getId());
            writeAdminMenuVO.setAmeUpdtIp(reuqest.getRemoteAddr());
            adminMenuService.updateAdminMenu(writeAdminMenuVO);
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
    }
    
    @RequestMapping(params = "act=delete", method = RequestMethod.POST)
    public String delete(@ModelAttribute("searchAdminMenuVO") AdminMenuVO searchVO,
                         @RequestParam(value = "ameId", required = true) int ameId,
                         ModelMap model) throws Exception {
        
        AdminMenuVO vo = adminMenuService.selectAdminMenu(searchVO);
        if(vo == null) {
            model.addAttribute("message", "admin.menu.nodata");
            return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
        }
        
        adminMenuService.deleteAdminMenu(vo);
        
        model.addAttribute("message", "common.success.delete");
        return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
    }
    
    @RequestMapping(params = "act=move", method = RequestMethod.GET)
    public String moveForm(@ModelAttribute("searchAdminMenuVO") AdminMenuVO searchVO, ModelMap model) throws Exception {
        
        int rootMenuId = adminMenuService.selectAdminMenuRootMenuId(searchVO);
        
        List<AdminMenuVO> list = adminMenuService.selectAdminMenuList(searchVO);
        
        model.addAttribute("rootMenuId", rootMenuId);
        model.addAttribute("adminMenuList", list);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "menu_move.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=move", method = RequestMethod.POST)
    public String moveWrite(@ModelAttribute("searchAdminMenuVO") AdminMenuVO searchVO,
                            @RequestParam(value = "jsonData", required = true) String jsonData,
                            ModelMap model) throws Exception {
        
        adminMenuService.updateAdminMenuMove(jsonData);
        
        model.addAttribute("message", "common.success.move");
        return CmsManager.alert(CONTENT_PATH + "menu_result.jsp", model);
    }
    
}