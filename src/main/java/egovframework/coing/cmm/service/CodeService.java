package egovframework.coing.cmm.service;

import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.cmm.vo.CodeVO;

import java.util.List;
import java.util.Map;

public interface CodeService {
    
    Map<String, Object> selectCodeList(CodeVO vo) throws Exception;
    
    List<CodeVO> selectCodeListAll(CodeVO vo) throws Exception;
    
    CodeVO selectCode(CodeVO vo) throws Exception;
    
    int checkCodeId(CodeVO vo) throws Exception;
    
    void insertCode(CodeVO vo) throws Exception;
    
    void updateCode(CodeVO vo) throws Exception;
    
    void deleteCode(CodeVO vo) throws Exception;
    
    Map<String, Object> selectCodeDetailList(CodeDetailVO vo) throws Exception;
    
    List<CodeDetailVO> selectCodeDetailListAll(CodeDetailVO vo) throws Exception;
    
    CodeDetailVO selectCodeDetail(CodeDetailVO vo) throws Exception;
    
    int selectCodeDetailMaxOrderNo(CodeDetailVO vo) throws Exception;
    
    int checkCodeDetailId(CodeDetailVO vo) throws Exception;
    
    void insertCodeDetail(CodeDetailVO vo) throws Exception;
    
    void updateCodeDetail(CodeDetailVO vo) throws Exception;
    
    void deleteCodeDetail(CodeDetailVO vo) throws Exception;
    
}