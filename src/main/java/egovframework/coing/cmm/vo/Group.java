package egovframework.coing.cmm.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Group {
    
    /*
     * 그룹 ID
     */
    private String grpId;
    
    /*
     * 그룹명
     */
    private String grpName;
    
    /*
     * 설명
     */
    private String grpContent;
    
    /*
     * 순서
     */
    private int grpOrderNo;
    
    /*
     * 등록 일시
     */
    private String grpRegDttm;
    
    /*
     * 등록자 ID
     */
    private String grpRegId;
    
    /*
     * 등록자 IP
     */
    private String grpRegIp;
    
    /*
     * 수정 일시
     */
    private String grpUpdtDttm;
    
    /*
     * 수정자 ID
     */
    private String grpUpdtId;
    
    /*
     * 수정자 IP
     */
    private String grpUpdtIp;
    
}