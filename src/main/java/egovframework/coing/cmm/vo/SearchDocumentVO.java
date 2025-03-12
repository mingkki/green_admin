package egovframework.coing.cmm.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDocumentVO {
    
    public String indexKey;
    
    public String siteId;
    
    public String sinId;
    
    public String code;
    
    private String title;
    
    private String content;
    
    private String regDate;
    
    private String updtDate;
    
    private String linkUrl;
    
    private String navi;
    
    private String type;
    
    private String boardId;
    
    private String boardWriteId;
    
}