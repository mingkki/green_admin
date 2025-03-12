package egovframework.coing.cmm.util;

import egovframework.coing.cmm.Globals;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.rte.fdl.string.EgovObjectUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


public class EgovUserDetailsHelper {
    
    /**
     * 인증된 사용자객체를 VO형식으로 가져온다.
     * @return Object - 사용자 ValueObject
     */
    public static Object getAuthenticatedUser() {
        return (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute(Globals.LOGIN_SESSION_NM, RequestAttributes.SCOPE_SESSION) == null
                ? new LoginVO()
                : (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute(Globals.LOGIN_SESSION_NM, RequestAttributes.SCOPE_SESSION);
        
    }
    
    /**
     * 인증된 사용자 여부를 체크한다.
     * @return Boolean - 인증된 사용자 여부(TRUE / FALSE)
     */
    public static Boolean isAuthenticated() {
        
        if(EgovObjectUtil.isNull((LoginVO) RequestContextHolder.getRequestAttributes()
                .getAttribute(Globals.LOGIN_SESSION_NM, RequestAttributes.SCOPE_SESSION))) {
            // log.debug("## authentication object is null!!");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    
    public static Boolean isMasterAdmin() {
        
        if(!isAuthenticated()) {
            return Boolean.FALSE;
        }
        
        LoginVO loginVO = (LoginVO) getAuthenticatedUser();
        if(loginVO.getUserLevel() == 99) {
            return Boolean.TRUE;
        }
        
        return Boolean.FALSE;
    }
}