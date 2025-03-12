package egovframework.coing.exchange.service.impl;

import egovframework.coing.exchange.vo.ExchangeVO;
import egovframework.coing.member.vo.MemberGeneralVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("exchangeMapper")
public interface ExchangeMapper {
    
    List<ExchangeVO> selectAll();
    
    List<ExchangeVO> select(ExchangeVO vo);
    
    ExchangeVO selectOne(ExchangeVO vo);
    
    int count(ExchangeVO vo);
    
    void insert(ExchangeVO vo);
    
    void update(ExchangeVO vo);
    
    void delete(Long id);

    List<ExchangeVO> selectExchangeList(ExchangeVO vo) throws Exception;
    
}