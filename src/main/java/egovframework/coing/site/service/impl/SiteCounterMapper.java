package egovframework.coing.site.service.impl;

import egovframework.coing.site.vo.SiteCounterDayVO;
import egovframework.coing.site.vo.SiteCounterMenuVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("siteCounterMapper")
public interface SiteCounterMapper {
    
    public List<SiteCounterDayVO> selectCounterDayListHour(SiteCounterDayVO vo) throws Exception;
    
    public List<SiteCounterDayVO> selectCounterDayListDay(SiteCounterDayVO vo) throws Exception;
    
    public List<SiteCounterDayVO> selectCounterDayListWeek(SiteCounterDayVO vo) throws Exception;
    
    public List<SiteCounterDayVO> selectCounterDayListMonth(SiteCounterDayVO vo) throws Exception;
    
    public List<SiteCounterDayVO> selectCounterDayListYear(SiteCounterDayVO vo) throws Exception;
    
    public SiteCounterDayVO selectCounterDayTotal(SiteCounterDayVO vo) throws Exception;
    
    /*페이지별 통계 추가건_곽민성*/
    List<SiteCounterMenuVO> selectCounterMenuList(SiteCounterMenuVO vo) throws Exception;
    
    /*여기까지*/
    
    
    //추가건 : DomainVO 추가인지아닌지 보류
	
	/*List<SiteDomainVO> selectSiteDomainList(SiteDomainVO vo) throws Exception;
	
	SiteDomainVO selectSiteDomain(SiteDomainVO vo) throws Exception;	
	
	SiteDomainVO selectSiteDomainByUrl(SiteDomainVO vo) throws Exception;
	
	SiteDomainVO selectSiteDomainBySiteId(SiteDomainVO vo) throws Exception;
		
	void insertSiteDomain(SiteDomainVO vo) throws Exception;
	
	void updateSiteDomain(SiteDomainVO vo) throws Exception;
	
	void deleteSiteDomain(SiteDomainVO vo) throws Exception;*/
 
 
}