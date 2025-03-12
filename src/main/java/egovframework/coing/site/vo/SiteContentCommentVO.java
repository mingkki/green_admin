package egovframework.coing.site.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class SiteContentCommentVO extends SiteContentComment {
    
    private String smeName;
    
    private String menuPath;
    
    private String scoRegDttm;
    
    private String scoRegIp;
    
    private int searchMenuLft;
    
    private int searchMenuRgt;
    
    /** 검색조건 */
    private String searchCondition = "";
    
    /** 검색Keyword */
    private String searchKeyword = "";
    
    /** 현재페이지 */
    private int pageIndex = 1;
    
    /** 페이지갯수 */
    private int pageUnit = 10;
    
    /** 페이지사이즈 */
    private int pageSize = 10;
    
    /** firstIndex */
    private int firstIndex = 1;
    
    /** lastIndex */
    private int lastIndex = 1;
    
    /** recordCountPerPage */
    private int recordCountPerPage = 10;
    
    private String queryString = "";
    private String searchStartDt;
    private String searchEndDt;
    
}