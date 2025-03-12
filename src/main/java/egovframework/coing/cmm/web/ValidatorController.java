package egovframework.coing.cmm.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class ValidatorController {
    
    @RequestMapping("/validator.do")
    public String validate(HttpServletRequest request, ModelMap model) {
        
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
        
        String lang = "";
        try {
            lang = locale.getLanguage();
        } catch(Exception e) {
            lang = "KO";
        }
        
        model.addAttribute("lang", lang);
        return "/egovframework/coing/common/validator";
    }
    
}