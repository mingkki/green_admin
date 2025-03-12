package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.vo.UserConfigVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("userConfigMapper")
public interface UserConfigMapper {
    
    UserConfigVO selectUserConfig(UserConfigVO vo) throws Exception;
    
    void insertUserConfig(UserConfigVO vo) throws Exception;
    
    void updateUserConfig(UserConfigVO vo) throws Exception;
    
}