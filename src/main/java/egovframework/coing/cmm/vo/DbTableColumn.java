package egovframework.coing.cmm.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class DbTableColumn {
    
    private String field;
    
    private String type;
    
    private String collation;
    
    private String nullYn;
    
    private String key;
    
    private String defaultValue;
    
    private String extra;
    
    private String privileges;
    
    private String comment;
    
}