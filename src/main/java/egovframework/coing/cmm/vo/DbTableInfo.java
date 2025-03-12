package egovframework.coing.cmm.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@Setter
@ToString
public class DbTableInfo {
    
    private String tableCatalog;
    
    private String tableSchema;
    
    private String tableName;
    
    private String tableType;
    
    private String engine;
    
    private String version;
    
    private String rowFormat;
    
    private String tableRows;
    
    private String avgRowLength;
    
    private String dataLength;
    
    private BigInteger maxDataLength;
    
    private String indexLength;
    
    private String dataFree;
    
    private String autoIncrement;
    
    private String createTime;
    
    private String updateTime;
    
    private String checkTime;
    
    private String tableCollation;
    
    private String checksum;
    
    private String createOptions;
    
    private String tableComment;
    
}