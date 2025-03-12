package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.service.ProgramService;
import egovframework.coing.cmm.vo.ProgramVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service("programService")
public class ProgramServiceImpl extends EgovAbstractServiceImpl implements ProgramService {
    
    private final ProgramMapper programMapper;
    private final EgovIdGnrService egovProgramIdGnrService;
    
    @Override
    public Map<String, Object> selectProgramList(ProgramVO vo) throws Exception {
        
        List<ProgramVO> result = new ArrayList<ProgramVO>();
        int cnt = programMapper.selectProgramListCnt(vo);
        if(cnt > 0) {
            result = programMapper.selectProgramList(vo);
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));
        
        return map;
    }
    
    @Override
    public List<ProgramVO> selectProgramListAll(ProgramVO vo) throws Exception {
        
        return programMapper.selectProgramListAll(vo);
    }
    
    @Override
    public ProgramVO selectProgram(ProgramVO vo) throws Exception {
        
        return programMapper.selectProgram(vo);
    }
    
    @Override
    public void insertProgram(ProgramVO vo) throws Exception {
        
        vo.setPrgId(egovProgramIdGnrService.getNextIntegerId());
        programMapper.insertProgram(vo);
    }
    
    @Override
    public void updateProgram(ProgramVO vo) throws Exception {
        
        programMapper.updateProgram(vo);
    }
    
    @Override
    public void deleteProgram(ProgramVO vo) throws Exception {
        
        programMapper.deleteProgram(vo);
    }
    
}