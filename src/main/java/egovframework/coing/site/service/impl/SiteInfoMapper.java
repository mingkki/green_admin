package egovframework.coing.site.service.impl;

import egovframework.coing.site.vo.SiteInfoVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("siteInfoMapper")
public interface SiteInfoMapper {
    
    Integer selectSiteInfoListCnt(SiteInfoVO vo) throws Exception;
    
    List<SiteInfoVO> selectSiteInfoList(SiteInfoVO vo) throws Exception;
    
    List<SiteInfoVO> selectSiteInfoListAll(SiteInfoVO vo) throws Exception;
    
    SiteInfoVO selectSiteInfo(SiteInfoVO vo) throws Exception;
    
    Integer checkSiteId(SiteInfoVO vo) throws Exception;
    
    Integer checkSiteDomain(SiteInfoVO vo) throws Exception;
    
    void insertSiteInfo(SiteInfoVO vo) throws Exception;
    
    void updateSiteInfo(SiteInfoVO vo) throws Exception;
    
    void deleteSiteInfo(SiteInfoVO vo) throws Exception;
    
    //추가건 없음
    
    
}