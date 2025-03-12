package egovframework.coing.point.service;

import egovframework.coing.point.vo.PointLogVO;

import java.util.List;

public interface PointLogService {
    
    List<PointLogVO> select(PointLogVO vo) throws Exception;
    
    PointLogVO selectOne(PointLogVO vo) throws Exception;
    
    int todayPoint() throws Exception;
    
    int totalPoint() throws Exception;
    
    int totalExchangePoint() throws Exception;
    
    int count(PointLogVO vo) throws Exception;
    
    void insert(PointLogVO vo) throws Exception;
    
    void update(PointLogVO vo) throws Exception;
    
    void delete(PointLogVO vo) throws Exception;
    
    void delete(Long id) throws Exception;
    
    
}