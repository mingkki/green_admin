package egovframework.coing.cmm.service;

import egovframework.coing.cmm.vo.ManagerVO;

import java.util.Map;

public interface ManagerService {
    
    Map<String, Object> selectManagerList(ManagerVO vo) throws Exception;
    
    ManagerVO selectManager(ManagerVO vo) throws Exception;
    
    void insertManager(ManagerVO vo) throws Exception;
    
    void updateManager(ManagerVO vo) throws Exception;
    
    void updateManagerMyinfo(ManagerVO vo) throws Exception;
    
    void deleteManager(ManagerVO vo) throws Exception;
    
}