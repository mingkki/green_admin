package egovframework.coing.site.service;

import egovframework.coing.site.vo.SiteInfoVO;

import java.util.List;
import java.util.Map;

public interface SiteInfoService {
    
    Map<String, Object> selectSiteInfoList(SiteInfoVO vo) throws Exception;
    
    List<SiteInfoVO> selectSiteInfoListAll(SiteInfoVO vo) throws Exception;
    
    SiteInfoVO selectSiteInfo(SiteInfoVO vo) throws Exception;
    
    int checkSiteId(SiteInfoVO vo) throws Exception;
    
    int checkSiteDomain(SiteInfoVO vo) throws Exception;
    
    void insertSiteInfo(SiteInfoVO vo) throws Exception;
    
    void updateSiteInfo(SiteInfoVO vo) throws Exception;
    
    void deleteSiteInfo(SiteInfoVO vo) throws Exception;
    
    //추가사항 없음
    
}