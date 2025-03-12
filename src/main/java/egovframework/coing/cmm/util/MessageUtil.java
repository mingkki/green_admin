package egovframework.coing.cmm.util;

import org.springframework.context.support.MessageSourceAccessor;

import java.util.Locale;

public class MessageUtil {
    
    private static MessageSourceAccessor msAcc = null;
    
    public static String getMessage(String code) {
        return msAcc.getMessage(code, Locale.getDefault());
    }
    
    public static String getMessage(String code, Object[] objs) {
        return msAcc.getMessage(code, objs, Locale.getDefault());
    }
    
    public void setMessageSourceAccessor(MessageSourceAccessor msAcc) {
        MessageUtil.msAcc = msAcc;
    }
    
}