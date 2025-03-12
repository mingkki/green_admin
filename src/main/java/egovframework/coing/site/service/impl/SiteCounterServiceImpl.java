package egovframework.coing.site.service.impl;

import egovframework.coing.site.service.SiteCounterService;
import egovframework.coing.site.vo.SiteCounterDayVO;
import egovframework.coing.site.vo.SiteCounterMenuVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("siteCounterService")
public class SiteCounterServiceImpl extends EgovAbstractServiceImpl implements SiteCounterService {
    
    private final SiteCounterMapper siteCounterMapper;
    
    @Override
    public List<SiteCounterDayVO> selectCounterDayListHour(SiteCounterDayVO vo) throws Exception {
        
        return siteCounterMapper.selectCounterDayListHour(vo);
    }
    
    @Override
    public List<SiteCounterDayVO> selectCounterDayListDay(SiteCounterDayVO vo) throws Exception {
        
        return siteCounterMapper.selectCounterDayListDay(vo);
    }
    
    @Override
    public List<SiteCounterDayVO> selectCounterDayListWeek(SiteCounterDayVO vo) throws Exception {
        
        return siteCounterMapper.selectCounterDayListWeek(vo);
    }
    
    @Override
    public List<SiteCounterDayVO> selectCounterDayListMonth(SiteCounterDayVO vo) throws Exception {
        
        return siteCounterMapper.selectCounterDayListMonth(vo);
    }
    
    @Override
    public List<SiteCounterDayVO> selectCounterDayListYear(SiteCounterDayVO vo) throws Exception {
        
        return siteCounterMapper.selectCounterDayListYear(vo);
    }
    
    @Override
    public SiteCounterDayVO selectCounterDayTotal(SiteCounterDayVO vo) throws Exception {
        
        return siteCounterMapper.selectCounterDayTotal(vo);
    }
    
    /*페이지별 통계 추가건_곽민성*/
    @Override
    public List<SiteCounterMenuVO> selectCounterMenuList(SiteCounterMenuVO vo) throws Exception {
        return siteCounterMapper.selectCounterMenuList(vo);
    }
    
}