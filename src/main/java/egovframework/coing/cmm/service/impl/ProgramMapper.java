package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.vo.ProgramVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("programMapper")
public interface ProgramMapper {
    
    Integer selectProgramListCnt(ProgramVO vo) throws Exception;
    
    List<ProgramVO> selectProgramList(ProgramVO vo) throws Exception;
    
    List<ProgramVO> selectProgramListAll(ProgramVO vo) throws Exception;
    
    ProgramVO selectProgram(ProgramVO vo) throws Exception;
    
    void insertProgram(ProgramVO vo) throws Exception;
    
    void updateProgram(ProgramVO vo) throws Exception;
    
    void deleteProgram(ProgramVO vo) throws Exception;
    
}