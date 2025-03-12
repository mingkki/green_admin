package egovframework.coing.site.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SiteMenu {
    
    private String sinId;
    
    private int smeId;
    
    private int smeParntsId;
    
    private String smeName;
    
    private String smeContent;
    
    private String smeDescription;
    
    private String smeKeyword;
    
    private String smeTheme;
    
    private String smeLayout;
    
    /** 메뉴 구분 */
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
    
    private String smeMainmenuYn = "Y";
    
    private String smeShowYn = "N";
    
    private String smeUseYn = "Y";
    
    private String smeSitemapYn = "Y";
    
    private String smeLoginViewYn = "N";
    
    private int smeKoglType;
    
    private int smeLft;
    
    private int smeRgt;
    
    private int smeLvl;
    
    private String smeRegDttm;
    
    private String smeRegId;
    
    private String smeRegIp;
    
    private String smeUpdtDttm;
    
    private String smeUpdtId;
    
    private String smeUpdtIp;
    
    /** 콘텐츠담당자 지정여부 */
    private String appointStaffAt;
    
    /** 만족도조사 출력여부 */
    private String showSatisAt;
    
    /** 콘텐츠담당부서ID */
    private String ordId;
    
    /** 콘텐츠담당부서명 */
    private String ordName;
    
    /** 콘텐츠담당자ID */
    private String orsId;
    
    /** 콘텐츠담당부서명 */
    private String orsName;
    
}