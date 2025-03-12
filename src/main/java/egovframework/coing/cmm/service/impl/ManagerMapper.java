package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.vo.ManagerVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("managerMapper")
public interface ManagerMapper {
    
    Integer selectManagerListCnt(ManagerVO vo) throws Exception;
    
    List<ManagerVO> selectManagerList(ManagerVO vo) throws Exception;
    
    ManagerVO selectManager(ManagerVO vo) throws Exception;
    
    void insertManager(ManagerVO vo) throws Exception;
    
    void updateManager(ManagerVO vo) throws Exception;
    
    void updateManagerMyinfo(ManagerVO vo) throws Exception;
    
    void deleteManager(ManagerVO vo) throws Exception;
    
}