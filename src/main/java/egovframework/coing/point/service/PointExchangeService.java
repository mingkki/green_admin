package egovframework.coing.point.service;

import egovframework.coing.point.vo.PointExchangeVO;

import java.util.List;

public interface PointExchangeService {
    
    List<PointExchangeVO> select(PointExchangeVO vo) throws Exception;
    
    PointExchangeVO selectOne(PointExchangeVO vo) throws Exception;
    
    int count(PointExchangeVO vo) throws Exception;
    
    void insert(PointExchangeVO vo) throws Exception;
    
    void update(PointExchangeVO vo) throws Exception;
    
    void updateStatusConfirm(Long id);
    
}