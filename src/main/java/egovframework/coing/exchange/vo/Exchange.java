package egovframework.coing.exchange.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Exchange {
    
    private Long id;
    private String emd;
    private String place;
    private String address;
    private Double lat;
    private Double lng;
    private String open;
    private String close;
    private Character delYn;
    private Date createDttm;
    private Date updateDttm;
    
}