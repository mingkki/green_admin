package egovframework.coing.cmm.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLoginLog {
    
    private int ullId;
    
    private String sinId;
    
    private String ullSuccessYn;
    
    private String ullUserId;
    
    private String ullDttm;
    
    private String ullIp;
    
    private String ullMessage;
    
    private String ullUseragent;
    
    private String ullBrowser;
    
    private String ullBrowserType;
    
    private String ullOs;
    
    private String ullDeviceType;
    
    private String ullUrl;
    
    private String ullReferer;
    
    private String ullSessionId;
    
    private String ullCmsYn;
    
}