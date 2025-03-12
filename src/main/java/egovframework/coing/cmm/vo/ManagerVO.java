package egovframework.coing.cmm.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ManagerVO extends Manager {
    
    private String[] groupIdArr;
    
    private String[] adminSiteIdArr;
    private String[] adminMenuIdArr;
    
    private String mngTel1;
    private String mngTel2;
    private String mngTel3;
    
    private String mngHp1;
    private String mngHp2;
    private String mngHp3;
    
    private String mngEmail1;
    private String mngEmail2;
    private String mngEmail3;
    
    private String searchCondition = "";
    
    private String searchKeyword = "";
    
    private int pageIndex = 1;
    
    private int pageUnit = 10;
    
    private int pageSize = 10;
    
    private int firstIndex = 1;
    
    private int lastIndex = 1;
    
    private int recordCountPerPage = 10;
    
    private String queryString = "";
    
}