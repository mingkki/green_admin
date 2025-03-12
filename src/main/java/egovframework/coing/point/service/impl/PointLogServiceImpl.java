package egovframework.coing.point.service.impl;

import egovframework.coing.point.service.PointLogService;
import egovframework.coing.point.vo.PointLogVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("pointLogService")
@RequiredArgsConstructor
public class PointLogServiceImpl extends EgovAbstractServiceImpl implements PointLogService {
    
    private final PointLogMapper pointLogMapper;
    
    @Override
    public List<PointLogVO> select(PointLogVO vo) throws Exception {
        return pointLogMapper.select(vo);
    }
    
    @Override
    public PointLogVO selectOne(PointLogVO vo) throws Exception {
        return pointLogMapper.selectOne(vo);
    }
    
    @Override
    public int todayPoint() {
        return pointLogMapper.todayPoint();
    }
    
    @Override
    public int totalPoint() {
        return pointLogMapper.totalPoint();
    }
    
    @Override
    public int totalExchangePoint() {
        return pointLogMapper.totalExchangePoint();
    }
    
    @Override
    public int count(PointLogVO vo) throws Exception {
        return pointLogMapper.count(vo);
    }
    
    @Override
    public void insert(PointLogVO vo) throws Exception {
        pointLogMapper.insert(vo);
    }
    
    @Override
    public void update(PointLogVO vo) throws Exception {
        pointLogMapper.update(vo);
    }
    
    @Override
    public void delete(PointLogVO vo) throws Exception {
        pointLogMapper.delete(vo.getId());
    }
    
    @Override
    public void delete(Long id) throws Exception {
        pointLogMapper.delete(id);
    }
    
}