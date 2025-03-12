package egovframework.coing.cmm.service;

import egovframework.coing.cmm.vo.UserConfigVO;

public interface UserConfigService {
    
    UserConfigVO selectUserConfig(UserConfigVO vo) throws Exception;
    
    void insertUserConfig(UserConfigVO vo) throws Exception;
    
    void updateUserConfig(UserConfigVO vo) throws Exception;
    
}