package egovframework.coing.cmm.service;

import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.cmm.vo.GroupVO;
import egovframework.coing.cmm.vo.LevelVO;
import egovframework.coing.cmm.vo.SendSmsVO;

import java.util.List;

public interface CommonService {
    
    List<LevelVO> selectLevelList() throws Exception;
    
    List<GroupVO> selectGroupList() throws Exception;
    
    List<GroupVO> selectGroupList(String systemYn) throws Exception;
    
    List<CodeDetailVO> selectCodeDetailList(CodeDetailVO vo) throws Exception;
    
    String encryptPassword(String password) throws Exception;
    
    String encryptPassword(String password, String id) throws Exception;
    
    void sendSms(SendSmsVO vo) throws Exception;
    
    CodeDetailVO selectCodeDetailSmsInfo(String string) throws Exception;
    
    CodeDetailVO selectCodeDetail(CodeDetailVO vo) throws Exception;
}