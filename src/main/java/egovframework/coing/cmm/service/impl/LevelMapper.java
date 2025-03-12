package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.vo.LevelVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("levelMapper")
public interface LevelMapper {
    
    List<LevelVO> selectLevelList(LevelVO vo) throws Exception;
    
    LevelVO selectLevel(LevelVO vo) throws Exception;
    
    void insertLevel(LevelVO vo) throws Exception;
    
    void updateLevel(LevelVO vo) throws Exception;
    
    void deleteLevel(LevelVO vo) throws Exception;
    
}