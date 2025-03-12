package egovframework.coing.point.service.impl;

import egovframework.coing.point.vo.PointLogVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("pointLogMapper")
public interface PointLogMapper {
    
    List<PointLogVO> selectAll();
    
    List<PointLogVO> select(PointLogVO vo);
    
    PointLogVO selectOne(PointLogVO vo);
    
    int todayPoint();
    
    int totalPoint();
    
    int totalExchangePoint();
    
    int count(PointLogVO vo);
    
    void insert(PointLogVO vo);
    
    void update(PointLogVO vo);
    
    void delete(Long id);
    
}