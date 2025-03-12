package egovframework.coing.cmm.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskingUtil {
    
    public static String phoneNum(String str) {
        String replaceString = str;
        
        Matcher matcher = Pattern.compile("^(\\d{3})-?(\\d{3,4})-?(\\d{4})$").matcher(str);
        
        if(matcher.matches()) {
            replaceString = "";
            
            boolean isHyphen = false;
            if(str.indexOf("-") > -1) {
                isHyphen = true;
            }
            
            for(int i = 1; i <= matcher.groupCount(); i++) {
                String replaceTarget = matcher.group(i);
                if(i == 2) {
                    char[] c = new char[replaceTarget.length()];
                    Arrays.fill(c, '*');
                    
                    replaceString = replaceString + String.valueOf(c);
                } else {
                    replaceString = replaceString + replaceTarget;
                }
                
                if(isHyphen && i < matcher.groupCount()) {
                    replaceString = replaceString + "-";
                }
            }
        }
        
        return replaceString;
    }
    
    public static String name(String str) {
        str = str.replace(" ", "");
        String replaceString = str;
        
        String pattern = "";
        
        if(Pattern.compile("^[a-zA-Z]*$").matcher(str).matches()) {
            if(str.length() <= 6) {
                pattern = "^(.)(.)(.+)$";
            } else {
                pattern = "^(\\w{3})(.+)(\\w{3})$";
            }
        } else {
            if(str.length() == 2) {
                pattern = "^(.)(.+)$";
            } else {
                pattern = "^(.)(.)(.+)$";
            }
        }
        
        Matcher matcher = Pattern.compile(pattern).matcher(str);
        
        if(matcher.matches()) {
            replaceString = "";
            
            for(int i = 1; i <= matcher.groupCount(); i++) {
                String replaceTarget = matcher.group(i);
                if(i == 2) {
                    char[] c = new char[replaceTarget.length()];
                    Arrays.fill(c, '*');
                    
                    replaceString = replaceString + String.valueOf(c);
                } else {
                    replaceString = replaceString + replaceTarget;
                }
                
            }
        }
        
        return replaceString;
    }
    
    public static String id(String str) {
        
        int length = str.length();
        if(length < 5) {
            return str;
        }
        
        length = length - 3;
        
        char[] c = new char[3];
        Arrays.fill(c, '*');
        
        String maskId = str.substring(0, length);
        maskId = maskId + String.valueOf(c);
        
        return maskId;
    }
    
    
    public static String email(String str) {
        String replaceString = str;
        
        Matcher matcher = Pattern.compile("^(.*)([@]{1})(.*)$").matcher(str);
        
        if(matcher.matches()) {
            replaceString = "";
            
            for(int i = 1; i <= matcher.groupCount(); i++) {
                String replaceTarget = matcher.group(i);
                if(i == 1) {
                    if(replaceTarget.length() < 4) {
                        char[] c = new char[replaceTarget.length()];
                        Arrays.fill(c, '*');
                        replaceString = replaceString + String.valueOf(c);
                    } else {
                        char[] c = new char[3];
                        Arrays.fill(c, '*');
                        replaceString = replaceTarget.substring(0, replaceTarget.length() - 3) + String.valueOf(c);
                    }
                } else {
                    replaceString = replaceString + replaceTarget;
                }
            }
            
        }
        
        return replaceString;
    }
    
    public static String ip(String str) {
        String replaceString = str;
        
        Matcher matcher = Pattern.compile("^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$").matcher(str);
        
        if(matcher.matches()) {
            replaceString = "";
            
            for(int i = 1; i <= matcher.groupCount(); i++) {
                String replaceTarget = matcher.group(i);
                if(i == 3) {
                    char[] c = new char[replaceTarget.length()];
                    Arrays.fill(c, '*');
                    
                    replaceString = replaceString + String.valueOf(c);
                } else {
                    replaceString = replaceString + replaceTarget;
                }
                if(i < matcher.groupCount()) {
                    replaceString = replaceString + ".";
                }
            }
        }
        
        return replaceString;
    }
    
}