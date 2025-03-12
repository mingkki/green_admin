package egovframework.coing.cmm.service;

import egovframework.coing.cmm.vo.LevelVO;

import java.util.List;

public interface LevelService {
    
    List<LevelVO> selectLevelList(LevelVO vo) throws Exception;
    
    LevelVO selectLevel(LevelVO vo) throws Exception;
    
    void insertLevel(LevelVO vo) throws Exception;
    
    void updateLevel(LevelVO vo) throws Exception;
    
    void deleteLevel(LevelVO vo) throws Exception;
    
}