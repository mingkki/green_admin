package egovframework.coing.site.service;

import egovframework.coing.site.vo.SiteCounterDayVO;
import egovframework.coing.site.vo.SiteCounterMenuVO;

import java.util.List;

public interface SiteCounterService {
    
    public List<SiteCounterDayVO> selectCounterDayListHour(SiteCounterDayVO vo) throws Exception;
    
    public List<SiteCounterDayVO> selectCounterDayListDay(SiteCounterDayVO vo) throws Exception;
    
    public List<SiteCounterDayVO> selectCounterDayListWeek(SiteCounterDayVO vo) throws Exception;
    
    public List<SiteCounterDayVO> selectCounterDayListMonth(SiteCounterDayVO vo) throws Exception;
    
    public List<SiteCounterDayVO> selectCounterDayListYear(SiteCounterDayVO vo) throws Exception;
    
    public SiteCounterDayVO selectCounterDayTotal(SiteCounterDayVO vo) throws Exception;
    
    /*페이지별 통계 추가건_곽민성*/
    List<SiteCounterMenuVO> selectCounterMenuList(SiteCounterMenuVO vo) throws Exception;
    
}