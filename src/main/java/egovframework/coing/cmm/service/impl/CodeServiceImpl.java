package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.service.CodeService;
import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.cmm.vo.CodeVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service("codeService")
public class CodeServiceImpl extends EgovAbstractServiceImpl implements CodeService {
    
    private final CodeMapper codeMapper;
    
    @Override
    public Map<String, Object> selectCodeList(CodeVO vo) throws Exception {
        
        List<CodeVO> result = new ArrayList<CodeVO>();
        int cnt = codeMapper.selectCodeListCnt(vo);
        if(cnt > 0) {
            result = codeMapper.selectCodeList(vo);
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));
        
        return map;
    }
    
    
    @Override
    public List<CodeVO> selectCodeListAll(CodeVO vo) throws Exception {
        
        return codeMapper.selectCodeListAll(vo);
    }
    
    @Override
    public CodeVO selectCode(CodeVO vo) throws Exception {
        
        return codeMapper.selectCode(vo);
    }
    
    @Override
    public int checkCodeId(CodeVO vo) throws Exception {
        
        return codeMapper.checkCodeId(vo);
    }
    
    @Override
    public void insertCode(CodeVO vo) throws Exception {
        
        codeMapper.insertCode(vo);
    }
    
    @Override
    public void updateCode(CodeVO vo) throws Exception {
        
        codeMapper.updateCode(vo);
    }
    
    @Override
    public void deleteCode(CodeVO vo) throws Exception {
        
        codeMapper.deleteCode(vo);
    }
    
    @Override
    public Map<String, Object> selectCodeDetailList(CodeDetailVO vo) throws Exception {
        
        List<CodeDetailVO> result = new ArrayList<CodeDetailVO>();
        int cnt = codeMapper.selectCodeDetailListCnt(vo);
        if(cnt > 0) {
            result = codeMapper.selectCodeDetailList(vo);
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));
        
        return map;
    }
    
    @Override
    public List<CodeDetailVO> selectCodeDetailListAll(CodeDetailVO vo) throws Exception {
        
        return codeMapper.selectCodeDetailListAll(vo);
    }
    
    @Override
    public CodeDetailVO selectCodeDetail(CodeDetailVO vo) throws Exception {
        
        return codeMapper.selectCodeDetail(vo);
    }
    
    @Override
    public int selectCodeDetailMaxOrderNo(CodeDetailVO vo) throws Exception {
        
        return codeMapper.selectCodeDetailMaxOrderNo(vo);
    }
    
    @Override
    public int checkCodeDetailId(CodeDetailVO vo) throws Exception {
        
        return codeMapper.checkCodeDetailId(vo);
    }
    
    @Override
    public void insertCodeDetail(CodeDetailVO vo) throws Exception {
        
        codeMapper.insertCodeDetail(vo);
    }
    
    @Override
    public void updateCodeDetail(CodeDetailVO vo) throws Exception {
        
        codeMapper.updateCodeDetail(vo);
    }
    
    @Override
    public void deleteCodeDetail(CodeDetailVO vo) throws Exception {
        
        codeMapper.deleteCodeDetail(vo);
    }
    
}