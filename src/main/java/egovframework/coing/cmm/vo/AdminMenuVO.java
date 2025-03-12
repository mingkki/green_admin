package egovframework.coing.cmm.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString(callSuper = true)
@Alias("adminMenuVO")
public class AdminMenuVO extends AdminMenu {
    
    private String accessUserId;
    
    private String accessGroupId;
    private String[] accessGroupIdArr;
    
    private String accessDeptId;
    
}