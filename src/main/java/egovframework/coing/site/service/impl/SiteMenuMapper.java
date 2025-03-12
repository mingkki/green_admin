package egovframework.coing.site.service.impl;

import egovframework.coing.cmm.vo.SearchDocumentVO;
import egovframework.coing.site.vo.SiteMenuJsonVO;
import egovframework.coing.site.vo.SiteMenuVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper("siteMenuMapper")
public interface SiteMenuMapper {
    
    List<SiteMenuVO> selectSiteMenuList(SiteMenuVO vo) throws Exception;
    
    List<SiteMenuJsonVO> selectSiteMenuJsonList(SiteMenuJsonVO vo) throws Exception;
    
    List<SiteMenuVO> selectParentSiteMenuList(SiteMenuVO vo) throws Exception;
    
    List<SiteMenuVO> selectChildrenSiteMenuList(SiteMenuVO vo) throws Exception;
    
    Integer selectSiteMenuRootMenuId(SiteMenuVO vo) throws Exception;
    
    SiteMenuVO selectSiteMenu(SiteMenuVO vo) throws Exception;
    
    void insertSiteMenu(SiteMenuVO vo) throws Exception;
    
    void updateSiteMenu(SiteMenuVO vo) throws Exception;
    
    void updateSiteMenuMove(SiteMenuVO vo) throws Exception;
    
    void updateChildrenSiteMenu(SiteMenuVO vo) throws Exception;
    
    void updateSiteMenuLftForInsert(HashMap<String, Object> map) throws Exception;
    
    void updateSiteMenuRgtForInsert(HashMap<String, Object> map) throws Exception;
    
    void updateSiteMenuLftForDelete(HashMap<String, Object> map) throws Exception;
    
    void updateSiteMenuRgtForDelete(HashMap<String, Object> map) throws Exception;
    
    void deleteSiteMenu(SiteMenuVO vo) throws Exception;
    
    void deleteSiteMenuBySiteId(SiteMenuVO vo) throws Exception;
    
    List<SearchDocumentVO> selectSearchSiteMenuList(SearchDocumentVO vo) throws Exception;
    
    //추가건
    
    
    List<SiteMenuVO> selectParntsSiteMenuList(SiteMenuVO vo) throws Exception;
    
    List<SiteMenuVO> selectChldrnSiteMenuList(SiteMenuVO vo) throws Exception;
    
    void updateChldrnSiteMenu(SiteMenuVO vo) throws Exception;
    
}