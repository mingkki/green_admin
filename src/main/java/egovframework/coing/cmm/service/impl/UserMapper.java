package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.cmm.vo.UserLoginLogVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("userMapper")
public interface UserMapper {
    
    LoginVO actionLogin(LoginVO vo) throws Exception;
    
    LoginVO selectUserMaster(LoginVO vo) throws Exception;
    
    void updateLoginInfo(LoginVO vo) throws Exception;
    
    void updateLoginFail(LoginVO vo) throws Exception;
    
    void updateLoginFailReset(LoginVO vo) throws Exception;
    
    Integer selectLoginLogListCnt(UserLoginLogVO vo) throws Exception;
    
    List<UserLoginLogVO> selectLoginLogList(UserLoginLogVO vo) throws Exception;
    
    void insertLoginLog(UserLoginLogVO vo) throws Exception;
    
    void updatePassword(LoginVO vo) throws Exception;
    
    List<LoginVO> selectStfMng() throws Exception;
    
    List<UserLoginLogVO> selectLoginLogListAll(UserLoginLogVO vo) throws Exception;
    
}