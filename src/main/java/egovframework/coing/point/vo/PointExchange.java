package egovframework.coing.point.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class PointExchange {
    
    private Long id;
    private String memId;
    private Integer exchangePoint;
    private String bank;
    private String bankAccountNumber;
    private String hp;
    private String status;
    private Date confirmDttm;
    private Date createDttm;
    private Date updateDttm;
    
}