package egovframework.coing.site.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SiteContentComment {
    
    private int commentId;
    
    private String sinId;
    
    private int smeId;
    
    private String writerId;
    
    private String writerNm;
    
    private int grade;
    
    private String content;
    
    private String scoRegDttm;
    
    private String scoRegIp;
    
}