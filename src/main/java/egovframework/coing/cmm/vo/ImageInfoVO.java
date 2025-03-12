package egovframework.coing.cmm.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ImageInfoVO extends ImageInfo {
    
    private String[] uploadFileMemo;
    private String[] deleteUploadFile;
    
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