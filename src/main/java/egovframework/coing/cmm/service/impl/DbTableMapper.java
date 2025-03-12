package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.vo.DbTableColumnVO;
import egovframework.coing.cmm.vo.DbTableInfoVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("dbTableMapper")
public interface DbTableMapper {
    
    Integer selectDbTableInfoListCnt(DbTableInfoVO vo) throws Exception;
    
    List<DbTableInfoVO> selectDbTableInfoList(DbTableInfoVO vo) throws Exception;
    
    DbTableInfoVO selectDbTableInfo(DbTableInfoVO vo) throws Exception;
    
    List<DbTableColumnVO> selectDbTableColumnList(DbTableColumnVO vo) throws Exception;
    
    List<DbTableInfoVO> selectDbTableInfoListAll(DbTableInfoVO vo) throws Exception;
    
}