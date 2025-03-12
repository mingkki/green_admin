package egovframework.coing.cmm;

import egovframework.coing.cmm.util.EgovStringUtil;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

public class CmsManager {
    
    public static String getModulePath() {
        return "/WEB-INF/jsp";
    }
    
    
    public static String alert(String contentFile, ModelMap model) {
        
        model.addAttribute("CONTENT_FILE", contentFile);
        
        return "egovframework/coing/common/alert_view";
    }
    
    public static String getFormError(Errors errors, String url, ModelMap modelMap) {
        String msg = "입력 양식을 확인해 주세요.\\n";
        for(FieldError error : errors.getFieldErrors()) {
            msg += "\\n" + error.getCode();
        }
        return alert(msg, url, modelMap);
    }
    
    public static String alert(String msg, String url, ModelMap modelMap) {
        modelMap.addAttribute("msg", msg);
        modelMap.addAttribute("url", url);
        return "egovframework/coing/common/alert2_view";
    }
    
    public static String alert(String msg, String url, ModelMap modelMap, String windowYn) {
        modelMap.addAttribute("msg", msg);
        modelMap.addAttribute("url", url);
        modelMap.addAttribute("windowYn", windowYn);
        return "egovframework/coing/common/alert2_view";
    }
    
    public static ModelAndView alertMV(String contentFile, ModelMap model) {
        
        ModelAndView mv = new ModelAndView("egovframework/coing/common/alert_view");
        mv.addObject("CONTENT_FILE", contentFile);
        mv.addAllObjects(model);
        
        return mv;
    }
    
    public static boolean ipCheck(String remoteAddr, String ips) {
        
        if("".equals(EgovStringUtil.nullConvert(ips))) {
            return true;
        }
        
        boolean isAccessIp = false;
        
        String[] ipsArr = ips.split("\\s*\\r?\\n\\s*");
        if(ipsArr != null && ipsArr.length > 0) {
            for(int i = 0; i < ipsArr.length; i++) {
                if(remoteAddr.equals(ipsArr[i])) {
                    isAccessIp = true;
                    break;
                }
            }
        }
        
        return isAccessIp;
    }
    
    // 이하 원추가
    public static void showLogin(String viewName, String message) throws Exception {
        ModelAndView mv = new ModelAndView(viewName);
        mv.addObject("message", message);
        mv.addObject("CONTENT_FILE", getModulePath() + "/egovframework/coing/core/view/login.jsp");
        throw new ModelAndViewDefiningException(mv);
    }
    
    public static void showMessage(String viewName, String message) throws Exception {
        ModelAndView mv = new ModelAndView(viewName);
        mv.addObject("message", message);
        mv.addObject("CONTENT_FILE", getModulePath() + "/egovframework/coing/core/view/message.jsp");
        throw new ModelAndViewDefiningException(mv);
    }
}