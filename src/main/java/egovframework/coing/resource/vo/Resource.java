package egovframework.coing.resource.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Resource {
    
    private Long id;
    private String name;
    private Integer point;
    private Character useYn;
    private Character delYn;
    private Date createDttm;
    private Date updateDttm;
    
}