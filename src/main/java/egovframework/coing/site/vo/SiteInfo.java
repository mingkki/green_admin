package egovframework.coing.site.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SiteInfo {
    
    private String sinId;
    
    private String sinTitle;
    
    private String sinName;
    
    private String sinDomain;
    
    private String sinLang;
    
    private String sinTheme;
    
    private String sinLayout;
    
    private String sinDescription;
    
    private String sinKeyword;
    
    private int sinPermitView;
    
    private int sinPermitViewGubun;
    
    private String sinLimitView;
    
    private String sinChargeNm;
    
    private String sinChargeTel;
    
    private String sinChargeEmail;
    
    private String sinTel;
    
    private String sinFax;
    
    private String sinZipcd;
    
    private String sinAddress1;
    
    private String sinAddress2;
    
    private String sinMapLat;
    
    private String sinMapLng;
    
    private String sinCopyright;
    
    private String sinHeadHtml;
    
    private String sinTailHtml;
    
    private int sinOrderNo;
    
    private String sinIpGubun = "N";
    
    private String sinCheckIp;
    
    private String sinUseYn = "Y";
    
    private String sinRegDttm;
    
    private String sinRegId;
    
    private String sinRegIp;
    
    private String sinUpdtDttm;
    
    private String sinUpdtId;
    
    private String sinUpdtIp;
    
    private String sinDelId;
    
    private String sinDelIp;
    
}