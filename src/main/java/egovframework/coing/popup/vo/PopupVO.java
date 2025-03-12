package egovframework.coing.popup.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class PopupVO extends Popup {
    
    private String progress;
    
    private String popCategoryNm;
    
    private String popSharesNm;
    private String[] popShareArr;
    
    private String popStartHH;
    private String popStartMM;
    private String popEndHH;
    private String popEndMM;
    
    /*
     * 팝업파일 삭제 체크
     */
    private String deleteFile;
    
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