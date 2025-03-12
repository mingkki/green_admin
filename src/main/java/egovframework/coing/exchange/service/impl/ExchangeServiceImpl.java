package egovframework.coing.exchange.service.impl;

import egovframework.coing.cmm.util.AddressUtil;
import egovframework.coing.exchange.service.ExchangeService;
import egovframework.coing.exchange.vo.ExchangeVO;
import egovframework.coing.member.vo.MemberGeneralVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("exchangeService")
@RequiredArgsConstructor
public class ExchangeServiceImpl extends EgovAbstractServiceImpl implements ExchangeService {
    
    private final ExchangeMapper exchangeMapper;
    private final AddressUtil addressUtil;
    
    @Override
    public List<ExchangeVO> select(ExchangeVO vo) throws Exception {
        return exchangeMapper.select(vo);
    }
    
    @Override
    public ExchangeVO selectOne(ExchangeVO vo) throws Exception {
        return exchangeMapper.selectOne(vo);
    }
    
    @Override
    public int count(ExchangeVO vo) throws Exception {
        return exchangeMapper.count(vo);
    }
    
    @Override
    public void insert(ExchangeVO vo) throws Exception {
        exchangeMapper.insert(vo);
    }
    
    @Override
    public void update(ExchangeVO vo) throws Exception {
        exchangeMapper.update(vo);
    }
    
    @Override
    public void delete(ExchangeVO vo) throws Exception {
        exchangeMapper.delete(vo.getId());
    }
    
    @Override
    public void delete(Long id) throws Exception {
        exchangeMapper.delete(id);
    }

    @Override
    public List<ExchangeVO> selectExchangeList() throws Exception {

        ExchangeVO vo = new ExchangeVO();
        return exchangeMapper.selectExchangeList(vo);
    }
    
}