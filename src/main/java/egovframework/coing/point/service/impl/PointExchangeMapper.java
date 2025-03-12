package egovframework.coing.point.service.impl;

import egovframework.coing.point.vo.PointExchangeVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("pointExchangeMapper")
public interface PointExchangeMapper {
    
    List<PointExchangeVO> selectAll();
    
    List<PointExchangeVO> select(PointExchangeVO vo);
    
    PointExchangeVO selectOne(PointExchangeVO vo);
    
    int count(PointExchangeVO vo);
    
    void insert(PointExchangeVO vo);
    
    void update(PointExchangeVO vo);
    
    void updateStatusConfirm(PointExchangeVO vo);
    
}