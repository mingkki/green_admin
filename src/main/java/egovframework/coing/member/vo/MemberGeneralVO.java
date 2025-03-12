package egovframework.coing.member.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class MemberGeneralVO extends MemberGeneral {
    
    private String[] memIdArr;
    
    private String[] memGroupArr;
    
    private String memTel1;
    private String memTel2;
    private String memTel3;
    
    private String memHp1;
    private String memHp2;
    private String memHp3;
    
    private String memEmail1;
    private String memEmail2;
    private String memEmail3;
    
    private String memFax1;
    private String memFax2;
    private String memFax3;
    
    private String memLevelNm;
    private String memGroupsNm;
    
    private String memComName;
    
    private String memName;
    
    private String memHp;
    
    private String memEmail;
    
    private String searchCondition = "";
    
    private String searchKeyword = "";
    
    private boolean paging = false;
    
    private String orderBy = "";
    
    private int pageIndex = 1;
    
    private int pageUnit = 10;
    
    private int pageSize = 10;
    
    private int firstIndex = 1;
    
    private int lastIndex = 1;
    
    private int recordCountPerPage = 10;
    
    private String queryString = "";
    
}