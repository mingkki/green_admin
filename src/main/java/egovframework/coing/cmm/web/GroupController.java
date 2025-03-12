package egovframework.coing.cmm.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.CodeService;
import egovframework.coing.cmm.service.GroupService;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.vo.GroupVO;
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
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/group.do")
public class GroupController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/", CmsManager.getModulePath());
    private final GroupService groupService;
    private final CodeService codeService;
    
    private void initParam(GroupVO searchVO) {
    }
    
    @RequestMapping()
    public String list(@ModelAttribute("searchGroupVO") GroupVO searchVO,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        List<GroupVO> resultList = groupService.selectGroupList(searchVO);
        
        model.addAttribute("resultList", resultList);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "group_list.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.GET)
    public String form(@ModelAttribute("searchGroupVO") GroupVO searchVO,
                       @ModelAttribute("writeGroup") GroupVO writeGroupVO,
                       @RequestParam(value = "grpId", required = false, defaultValue = "") String grpId,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        String command = ("".equals(grpId))
                ? "insert"
                : "update";
        
        if("update".equals(command)) {
            writeGroupVO = groupService.selectGroup(searchVO);
            if(writeGroupVO == null) {
                model.addAttribute("message", "group.nodata");
                return CmsManager.alert(CONTENT_PATH + "group_result.jsp", model);
            }
        } else {
        }
        
        model.addAttribute("command", command);
        model.addAttribute("writeGroup", writeGroupVO);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "group_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.POST)
    public String write(@ModelAttribute("searchGroupVO") GroupVO searchVO,
                        @ModelAttribute("writeGroup") GroupVO writeGroupVO,
                        @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                        @RequestParam Map<String, Object> paramMap,
                        HttpServletRequest request,
                        ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        String message = "";
        
        if("insert".equals(command)) {
            GroupVO vo = new GroupVO();
            vo.setGrpId(writeGroupVO.getGrpId());
            vo = groupService.selectGroup(vo);
            if(vo != null) {
                model.addAttribute("message", "group.useId");
                return CmsManager.alert(CONTENT_PATH + "group_result.jsp", model);
            }
            
            message = "common.success.insert";
            writeGroupVO.setGrpRegId(loginVO.getId());
            writeGroupVO.setGrpRegIp(request.getRemoteAddr());
            groupService.insertGroup(writeGroupVO);
        } else {
            GroupVO vo = groupService.selectGroup(searchVO);
            if(vo == null) {
                model.addAttribute("message", "group.nodata");
                return CmsManager.alert(CONTENT_PATH + "group_result.jsp", model);
            }
            message = "common.success.update";
            writeGroupVO.setGrpUpdtId(loginVO.getId());
            writeGroupVO.setGrpUpdtIp(request.getRemoteAddr());
            groupService.updateGroup(writeGroupVO);
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "group_result.jsp", model);
    }
    
    @RequestMapping(params = "act=delete", method = RequestMethod.POST)
    public String delete(@ModelAttribute("searchGroupVO") GroupVO searchVO,
                         @RequestParam(value = "grpId", required = true) String grpId,
                         HttpServletRequest request,
                         ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        GroupVO vo = groupService.selectGroup(searchVO);
        if(vo == null) {
            model.addAttribute("message", "group.nodata");
            return CmsManager.alert(CONTENT_PATH + "group_result.jsp", model);
        }
        
        groupService.deleteGroup(vo);
        
        model.addAttribute("message", "common.success.delete");
        return CmsManager.alert(CONTENT_PATH + "group_result.jsp", model);
    }
    
}