package egovframework.coing.cmm.service;

import egovframework.coing.cmm.service.impl.UserMapper;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.cmm.vo.UserLoginLogVO;

import java.util.List;
import java.util.Map;

public interface UserService {
    
    UserMapper getMapper();
    
    LoginVO actionLogin(LoginVO vo) throws Exception;
    
    LoginVO selectUserMaster(LoginVO vo) throws Exception;
    
    Map<String, Object> selectLoginLogList(UserLoginLogVO vo) throws Exception;
    
    void updatePassword(LoginVO vo) throws Exception;
    
    List<UserLoginLogVO> selectLoginLogListAll(UserLoginLogVO vo) throws Exception;
    
}