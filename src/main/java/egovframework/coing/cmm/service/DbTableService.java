package egovframework.coing.cmm.service;

import egovframework.coing.cmm.vo.DbTableColumnVO;
import egovframework.coing.cmm.vo.DbTableInfoVO;

import java.util.List;
import java.util.Map;

public interface DbTableService {
    
    Map<String, Object> selectDbTableInfoList(DbTableInfoVO vo) throws Exception;
    
    DbTableInfoVO selectDbTableInfo(DbTableInfoVO vo) throws Exception;
    
    List<DbTableColumnVO> selectDbTableColumnList(DbTableColumnVO vo) throws Exception;
    
    List<DbTableInfoVO> selectDbTableInfoListAll(DbTableInfoVO vo) throws Exception;
    
}