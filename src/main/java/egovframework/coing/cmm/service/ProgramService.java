package egovframework.coing.cmm.service;

import egovframework.coing.cmm.vo.ProgramVO;

import java.util.List;
import java.util.Map;

public interface ProgramService {
    
    Map<String, Object> selectProgramList(ProgramVO vo) throws Exception;
    
    List<ProgramVO> selectProgramListAll(ProgramVO vo) throws Exception;
    
    ProgramVO selectProgram(ProgramVO vo) throws Exception;
    
    void insertProgram(ProgramVO vo) throws Exception;
    
    void updateProgram(ProgramVO vo) throws Exception;
    
    void deleteProgram(ProgramVO vo) throws Exception;
    
}