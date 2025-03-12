package egovframework.coing.cmm.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Level {
    
    /*
     * 등급 ID
     */
    private int lvlId;
    
    /*
     * 등급명
     */
    private String lvlName;
    
    /*
     * 설명
     */
    private String lvlContent;
    
    /*
     * 등록 일시
     */
    private String lvlRegDttm;
    
    /*
     * 등록자 ID
     */
    private String lvlRegId;
    
    /*
     * 등록자 IP
     */
    private String lvlRegIp;
    
    /*
     * 수정 일시
     */
    private String lvlUpdtDttm;
    
    /*
     * 수정자 ID
     */
    private String lvlUpdtId;
    
    /*
     * 수정자 IP
     */
    private String lvlUpdtIp;
    
}