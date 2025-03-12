package egovframework.coing.cmm.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminMenu {
    
    /*
     * 메뉴 ID
     */
    private int ameId = 0;
    
    /*
     * 부모메뉴 ID
     */
    private int ameParntsId = 0;
    
    /*
     * 메뉴명
     */
    private String ameName = "";
    
    /*
     * 프로그램명
     */
    private String amePname = "";
    
    /*
     * 메뉴 타입(LINK_IN:내부링크, LINK_OUT:외부링크)
     */
    private String ameType = "LINK_IN";
    
    /*
     * 링크 타겟(_SELF:현재창, _BALNK:새창, _POPUP:팝업창)
     */
    private String ameLinkTarget = "_SELF";
    
    /*
     * 링크 URL
     */
    private String ameLinkUrl = "";
    
    /*
     * 링크 파라미터
     */
    private String ameLinkParam = "";
    
    /*
     * 팝업 파라미터
     */
    private String amePopupParam = "";
    
    /*
     * 출력 여부(Y:출력, N:출력안함)
     */
    private String ameShowYn = "Y";
    
    /*
     * 사용 여부(Y:사용, N:사용안함)
     */
    private String ameUseYn = "Y";
    
    /*
     * 트리 정렬을 위한 LEFT
     */
    private int ameLft = 0;
    
    /*
     * 트리 정렬을 위한 RIGHT
     */
    private int ameRgt = 0;
    
    /*
     * 메뉴 레벨
     */
    private int ameLvl = 0;
    
    /*
     * 등록 일시
     */
    private String ameRegDttm = "";
    
    /*
     * 등록자 ID
     */
    private String ameRegId = "";
    
    /*
     * 등록자 IP
     */
    private String ameRegIp = "";
    
    /*
     * 수정 일시
     */
    private String ameUpdtDttm = "";
    
    /*
     * 수정자 ID
     */
    private String ameUpdtId = "";
    
    /*
     * 수정자 IP
     */
    private String ameUpdtIp = "";
    
}