package egovframework.coing.site.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class SiteMenuJsonVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String sinId;
    
    private int smeId;
    
    private int smeParntsId;
    
    private String smeName;
    
    private String smeContent;
    
    private String smeDescription;
    
    private String smeKeyword;
    
    private String smeTheme;
    
    private String smeLayout;
    
    private String smeType = "HTML";
    
    private String smeLinkTarget = "_SELF";
    
    private String smeLinkUrl;
    
    private String smeLinkParam;
    
    private int smeLinkSmeId;
    
    private String smeLinkBrdId;
    
    private int smeLinkPrgId;
    
    private String smePopupLeft;
    
    private String smePopupTop;
    
    private String smePopupWidth;
    
    private String smePopupHeight;
    
    private String smePopupScrollAt = "no";
    
    private int smePermitView;
    
    private int smePermitViewGubun;
    
    private String smeLimitView;
    
    private String smeMainmenuYn = "N";
    
    private String smeShowYn = "Y";
    
    private String smeUseYn = "Y";
    
    private String smeSitemapYn = "Y";
    
    private String smeLoginViewYn = "N";
    
    private int smeKoglType;
    
    private int smeLvl;
    
}