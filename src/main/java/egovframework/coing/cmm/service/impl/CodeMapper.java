package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.cmm.vo.CodeVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("codeMapper")
public interface CodeMapper {
    
    Integer selectCodeListCnt(CodeVO vo) throws Exception;
    
    List<CodeVO> selectCodeList(CodeVO vo) throws Exception;
    
    List<CodeVO> selectCodeListAll(CodeVO vo) throws Exception;
    
    CodeVO selectCode(CodeVO vo) throws Exception;
    
    Integer checkCodeId(CodeVO vo) throws Exception;
    
    void insertCode(CodeVO vo) throws Exception;
    
    void updateCode(CodeVO vo) throws Exception;
    
    void deleteCode(CodeVO vo) throws Exception;
    
    Integer selectCodeDetailListCnt(CodeDetailVO vo) throws Exception;
    
    List<CodeDetailVO> selectCodeDetailList(CodeDetailVO vo) throws Exception;
    
    List<CodeDetailVO> selectCodeDetailListAll(CodeDetailVO vo) throws Exception;
    
    CodeDetailVO selectCodeDetail(CodeDetailVO vo) throws Exception;
    
    Integer selectCodeDetailMaxOrderNo(CodeDetailVO vo) throws Exception;
    
    Integer checkCodeDetailId(CodeDetailVO vo) throws Exception;
    
    void insertCodeDetail(CodeDetailVO vo) throws Exception;
    
    void updateCodeDetail(CodeDetailVO vo) throws Exception;
    
    void deleteCodeDetail(CodeDetailVO vo) throws Exception;
    
}