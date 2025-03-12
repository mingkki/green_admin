package egovframework.coing.point.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class PointLog {
    
    private Long id;
    private String memId;
    private Long exchangeId;
    private Integer point;
    private Date dttm;
    private Integer nowPoint;
    
}