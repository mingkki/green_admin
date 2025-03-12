package egovframework.coing.point.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class PointLogVO extends PointLog {
    
    private String searchCondition = "";
    private String searchKeyword = "";
    private int pageIndex = 1;
    private int pageUnit = 10;
    private int pageSize = 10;
    private int firstIndex = 1;
    private int lastIndex = 1;
    private int recordCountPerPage = 10;
    private String queryString = "";
    
    private String memName;
    private String memEmail;
    private String emd;
    private String emdNm;
    
}