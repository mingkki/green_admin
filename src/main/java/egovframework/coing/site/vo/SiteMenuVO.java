package egovframework.coing.site.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class SiteMenuVO extends SiteMenu {
    
    private String updateChildrenDesign = "N";
    
    private String[] smeLimitViewArr;
    
    private String menuPath;
    
    private String[] limitGroupIdArr;
    
    private String userLinkUrl;
    
    private String updateChldrnsLayout;
    
    private String updateChldrnsMngStaff;
    
    private String updateChldrnsCpyrgtAt;
    
    private String updateChldrnsSnsAt;
    
    private String updateChldrnsMngUserIds;
    
    private String updateChldrnsSatis;
    
}