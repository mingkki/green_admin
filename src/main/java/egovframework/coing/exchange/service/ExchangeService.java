package egovframework.coing.exchange.service;

import egovframework.coing.exchange.vo.ExchangeVO;
import egovframework.coing.member.vo.MemberGeneralVO;

import java.util.List;

public interface ExchangeService {
    
    List<ExchangeVO> select(ExchangeVO vo) throws Exception;
    
    ExchangeVO selectOne(ExchangeVO vo) throws Exception;
    
    int count(ExchangeVO vo) throws Exception;
    
    void insert(ExchangeVO vo) throws Exception;
    
    void update(ExchangeVO vo) throws Exception;
    
    void delete(ExchangeVO vo) throws Exception;
    
    void delete(Long id) throws Exception;

    List<ExchangeVO> selectExchangeList() throws Exception;
    
}