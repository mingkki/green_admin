package egovframework.coing.cmm.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.Globals;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.service.UserService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/auth/password/period.do")
public class AuthPasswordPeriodController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/", CmsManager.getModulePath());
    private final CommonService commonService;
    private final UserService userService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String form(ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        accessCheck(loginVO, model);
        
        return "egovframework/coing/password_period";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@RequestParam(value = "password", required = true) String password,
                         @RequestParam(value = "newPassword", required = true) String newPassword,
                         HttpServletRequest request,
                         ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        accessCheck(loginVO, model);
        
        String enpassword = commonService.encryptPassword(password, loginVO.getId());
        if(!enpassword.equals(loginVO.getPassword())) {
            model.addAttribute("message", "auth.password.wrong");
            return CmsManager.alert(CONTENT_PATH + "password_period_result.jsp", model);
        }
        
        LoginVO updateVO = new LoginVO();
        updateVO.setUserSe(loginVO.getUserSe());
        updateVO.setId(loginVO.getId());
        updateVO.setPassword(newPassword);
        updateVO.setIp(request.getRemoteAddr());
        userService.updatePassword(updateVO);
        
        // 비밀번호를 변경 후 수정된 정보로 로그인 세션을 변경한다.
        LoginVO updateLoginVO = new LoginVO();
        updateLoginVO.setId(loginVO.getId());
        updateLoginVO = userService.selectUserMaster(updateLoginVO);
        request.getSession().setAttribute(Globals.LOGIN_SESSION_NM, updateLoginVO);
        
        model.addAttribute("message", "common.success.update");
        return CmsManager.alert(CONTENT_PATH + "password_period_result.jsp", model);
    }
    
    /**
     * 접근 권한을 체크한다.
     * 로그인을 안했거나, 관리자인 경우 메세지를 출력한다.
     */
    private void accessCheck(LoginVO loginVO, ModelMap model) throws Exception {
        
        if(!EgovUserDetailsHelper.isAuthenticated() || loginVO == null) {
            model.addAttribute("message", "common.access.denied");
            throw new ModelAndViewDefiningException(CmsManager.alertMV(CONTENT_PATH + "/password_period_result.jsp", model));
        }
        
        String userSe = EgovStringUtil.nullConvert(loginVO.getUserSe());
        if(!"MNG".equals(userSe)) {
            model.addAttribute("message", "common.access.denied");
            throw new ModelAndViewDefiningException(CmsManager.alertMV(CONTENT_PATH + "/password_period_result.jsp", model));
        }
    }
    
}