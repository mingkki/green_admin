package egovframework.coing.board.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class BoardWriteVO extends BoardWrite {
    
    private String brdName;
    
    private String bwrDeptNm;
    private String[] bwrDeptIdArr;
    
    private String[] uploadFileMemo;
    private String[] deleteUploadFile;
    
    private String bwrThumbFile;
    
    private int bwrRegDttmDiff;
    
    private String bwrCategoryNm;
    
    private String bwrProgressNm;
    
    private String searchPeriodYn = "N";
    
    private String searchMode = "";
    
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