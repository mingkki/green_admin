package egovframework.coing.point.service.impl;

import egovframework.coing.point.service.PointExchangeService;
import egovframework.coing.point.vo.PointExchangeVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("pointExchangeService")
@RequiredArgsConstructor
public class PointExchangeServiceImpl extends EgovAbstractServiceImpl implements PointExchangeService {
    
    
    private final PointExchangeMapper pointExchangeMapper;
    
    @Override
    public List<PointExchangeVO> select(PointExchangeVO vo) throws Exception {
        return pointExchangeMapper.select(vo);
    }
    
    @Override
    public PointExchangeVO selectOne(PointExchangeVO vo) throws Exception {
        return pointExchangeMapper.selectOne(vo);
    }
    
    @Override
    public int count(PointExchangeVO vo) throws Exception {
        return pointExchangeMapper.count(vo);
    }
    
    @Override
    public void insert(PointExchangeVO vo) throws Exception {
        pointExchangeMapper.insert(vo);
    }
    
    @Override
    public void update(PointExchangeVO vo) throws Exception {
        pointExchangeMapper.update(vo);
    }
    
    @Override
    public void updateStatusConfirm(Long id) {
        PointExchangeVO vo = new PointExchangeVO();
        vo.setId(id);
        
        pointExchangeMapper.updateStatusConfirm(vo);
    }
    
}