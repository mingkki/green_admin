package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.service.LevelService;
import egovframework.coing.cmm.vo.LevelVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("levelService")
public class LevelServiceImpl extends EgovAbstractServiceImpl implements LevelService {
    
    private final LevelMapper levelMapper;
    
    @Override
    public List<LevelVO> selectLevelList(LevelVO vo) throws Exception {
        
        return levelMapper.selectLevelList(vo);
    }
    
    @Override
    public LevelVO selectLevel(LevelVO vo) throws Exception {
        
        return levelMapper.selectLevel(vo);
    }
    
    @Override
    public void insertLevel(LevelVO vo) throws Exception {
        
        levelMapper.insertLevel(vo);
    }
    
    @Override
    public void updateLevel(LevelVO vo) throws Exception {
        
        levelMapper.updateLevel(vo);
    }
    
    @Override
    public void deleteLevel(LevelVO vo) throws Exception {
        
        levelMapper.deleteLevel(vo);
    }
    
}