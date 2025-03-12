package egovframework.coing.point.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class PointExchangeVO extends PointExchange {
    
    private String searchCondition = "";
    private String searchKeyword = "";
    private int pageIndex = 1;
    private int pageUnit = 10;
    private int pageSize = 10;
    private int firstIndex = 1;
    private int lastIndex = 1;
    private int recordCountPerPage = 10;
    private String queryString = "";
    
    private String statusNm;
    private String memName;
    
    public String getStatusNm() {
        if(getStatus() == null) {
            return "";
        }
        
        if("WAIT".equals(getStatus())) {
            statusNm = "대기중";
        } else if("CONFIRM".equals(getStatus())) {
            statusNm = "환전 완료";
        }
        
        return statusNm;
    }
    
}