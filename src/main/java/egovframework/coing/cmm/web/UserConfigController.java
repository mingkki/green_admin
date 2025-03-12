package egovframework.coing.cmm.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.service.UserConfigService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.vo.LevelVO;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.cmm.vo.UserConfigVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user/config/{ucfUserSe}.do")
public class UserConfigController {
    
    private final UserConfigService userConfigService;
    private final CommonService commonService;
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/user/", CmsManager.getModulePath());
    
    private void initParam(UserConfigVO searchVO, String ucfUserSe) {
        
        searchVO.setUcfUserSe(ucfUserSe.toUpperCase());
    }
    
    @RequestMapping()
    public String form(@PathVariable String ucfUserSe,
                       @ModelAttribute("searchUserConfigVO") UserConfigVO searchVO,
                       @ModelAttribute("writeUserConfig") UserConfigVO writeUserConfigVO,
                       ModelMap model) throws Exception {
        
        initParam(searchVO, ucfUserSe);
        
        String command = "";
        writeUserConfigVO = userConfigService.selectUserConfig(searchVO);
        if(writeUserConfigVO != null) {
            command = "update";
        } else {
            command = "insert";
        }
        
        List<LevelVO> levelList = commonService.selectLevelList();
        
        model.addAttribute("command", command);
        model.addAttribute("writeUserConfig", writeUserConfigVO);
        model.addAttribute("levelList", levelList);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "config_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String write(@PathVariable String ucfUserSe,
                        @ModelAttribute("searchUserConfigVO") UserConfigVO searchVO,
                        @ModelAttribute("writeUserConfig") UserConfigVO writeUserConfigVO,
                        @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                        HttpServletRequest request,
                        ModelMap model) throws Exception {
        
        initParam(searchVO, ucfUserSe);
        
        writeUserConfigVO.setUcfJoinYn(EgovStringUtil.nullConvert(writeUserConfigVO.getUcfJoinYn(), "Y"));
        writeUserConfigVO.setUcfLeaveConfirmYn(EgovStringUtil.nullConvert(writeUserConfigVO.getUcfLeaveConfirmYn(), "N"));
        writeUserConfigVO.setUcfJoinConfirmYn(EgovStringUtil.nullConvert(writeUserConfigVO.getUcfJoinConfirmYn(), "N"));
        writeUserConfigVO.setUcfJoinGubunYn(EgovStringUtil.nullConvert(writeUserConfigVO.getUcfJoinGubunYn(), "N"));
        writeUserConfigVO.setUcfJoinAgreeYn(EgovStringUtil.nullConvert(writeUserConfigVO.getUcfJoinAgreeYn(), "Y"));
        writeUserConfigVO.setUcfJoinRealnameYn(EgovStringUtil.nullConvert(writeUserConfigVO.getUcfJoinRealnameYn(), "Y"));
        writeUserConfigVO.setUcfFindRealnameYn(EgovStringUtil.nullConvert(writeUserConfigVO.getUcfFindRealnameYn(), "Y"));
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        String message = "";
        
        if("insert".equals(command)) {
            message = "common.success.insert";
            writeUserConfigVO.setUcfRegId(loginVO.getId());
            writeUserConfigVO.setUcfRegIp(request.getRemoteAddr());
            userConfigService.insertUserConfig(writeUserConfigVO);
        } else {
            UserConfigVO vo = userConfigService.selectUserConfig(searchVO);
            if(vo == null) {
                model.addAttribute("message", "user.config.nodata");
                return CmsManager.alert(CONTENT_PATH + "config_result.jsp", model);
            }
            message = "common.success.update";
            writeUserConfigVO.setUcfUpdtId(loginVO.getId());
            writeUserConfigVO.setUcfUpdtIp(request.getRemoteAddr());
            userConfigService.updateUserConfig(writeUserConfigVO);
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "config_result.jsp", model);
    }
}