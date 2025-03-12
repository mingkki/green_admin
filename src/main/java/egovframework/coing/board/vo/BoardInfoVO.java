package egovframework.coing.board.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class BoardInfoVO extends BoardInfo {
    
    private String[] brdLimitReadArr;
    private String[] brdLimitWriteArr;
    private String[] brdLimitReplyArr;
    private String[] brdLimitCommentArr;
    private String[] brdLimitUploadArr;
    private String[] brdLimitDownloadArr;
    private String[] brdLimitNoticeArr;
    private String[] brdLimitEditorArr;
    
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