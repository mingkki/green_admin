package egovframework.coing.site.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class SiteCounterMenuVO extends SiteCounterMenu {
    
    private String searchStartDt;
    private String searchEndDt;
    private String menuPath;
    
}