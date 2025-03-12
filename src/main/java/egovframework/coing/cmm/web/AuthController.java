package egovframework.coing.cmm.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.Globals;
import egovframework.coing.cmm.service.UserService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class AuthController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/", CmsManager.getModulePath());
    private final UserService userService;
    
    @RequestMapping(value = "/auth/login.do", method = RequestMethod.GET)
    public String login(@ModelAttribute("loginVO") LoginVO loginVO, ModelMap model) {
        if(EgovUserDetailsHelper.isAuthenticated()) {
            return "redirect:/dashboard.do";
        }
        return "egovframework/coing/user/login";
    }
    
    @RequestMapping(value = "/auth/login.do", method = RequestMethod.POST)
    public String doLogin(@ModelAttribute("loginVO") LoginVO loginVO,
                          HttpSession session,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          ModelMap model) throws Exception {
        
        loginVO.setIp(request.getRemoteAddr());
        loginVO.setReferrer(request.getHeader("referer"));
        loginVO.setUseragent(request.getHeader("User-Agent"));
        
        LoginVO resultVO = userService.actionLogin(loginVO);
        if(resultVO != null && !"".equals(EgovStringUtil.nullConvert(resultVO.getId()))) {
            if("A".equals(resultVO.getStatus())) {
                model.addAttribute("message", "login.status.request");
                return CmsManager.alert(CONTENT_PATH + "login_result.jsp", model);
            } else if("H".equals(resultVO.getStatus())) {
                model.addAttribute("message", "login.status.dormant");
                return CmsManager.alert(CONTENT_PATH + "login_result.jsp", model);
            } else if("L".equals(resultVO.getStatus())) {
                model.addAttribute("message", "login.status.lock");
                return CmsManager.alert(CONTENT_PATH + "login_result.jsp", model);
            } else if("X2".equals(resultVO.getStatus())) {
                model.addAttribute("resultVO", resultVO);
                return "egovframework/coing/login_temp_lock";
            } else if("X1".equals(resultVO.getStatus())) {
                model.addAttribute("resultVO", resultVO);
                model.addAttribute("message", "login.nodata");
                return CmsManager.alert(CONTENT_PATH + "login_result.jsp", model);
            } else {
                // 이미 로그인 되어 있다면, 기존 로그인 세션을 종료
    			/*
	    		LoginStore loginStore = LoginStore.getInstance();
	            if( loginStore.get(resultVO.getId()) != null ) {
	                loginStore.logout(resultVO.getId());
	            }

	            // 새로운 로그인 세션 입력
	            loginStore.add(resultVO.getId(), session);
	            */
                
                session.setAttribute(Globals.LOGIN_SESSION_NM, resultVO);
                
                model.addAttribute("result", "login.ok");
                return CmsManager.alert(CONTENT_PATH + "login_result.jsp", model);
            }
        }
        
        model.addAttribute("resultVO", resultVO);
        model.addAttribute("result", "login.fail");
        model.addAttribute("message", "login.nodata");
        
        return CmsManager.alert(CONTENT_PATH + "login_result.jsp", model);
    }
    
    @RequestMapping(value = "/auth/logout.do")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        
        //LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
        
        session.setAttribute(Globals.LOGIN_SESSION_NM, null);
        
        //LoginStore loginStore = LoginStore.getInstance();
        //loginStore.logout(loginVO.getId());
        
        try {
            RequestContextHolder.getRequestAttributes().removeAttribute(Globals.LOGIN_SESSION_NM, RequestAttributes.SCOPE_SESSION);
        } catch(Exception e) {
            LOGGER.debug("Exception", e);
        }
        
        return "redirect:/auth/login.do";
    }
    
}