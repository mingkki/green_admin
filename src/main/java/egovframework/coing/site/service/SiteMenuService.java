package egovframework.coing.site.service;

import egovframework.coing.site.vo.SiteMenuVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SiteMenuService {
    
    List<SiteMenuVO> selectSiteMenuList(SiteMenuVO vo) throws Exception;
    
    List<SiteMenuVO> selectParentSiteMenuList(SiteMenuVO vo) throws Exception;
    
    List<SiteMenuVO> selectChildrenSiteMenuList(SiteMenuVO vo) throws Exception;
    
    SiteMenuVO selectSiteMenu(SiteMenuVO vo) throws Exception;
    
    int selectSiteMenuRootMenuId(SiteMenuVO vo) throws Exception;
    
    void insertSiteMenu(SiteMenuVO vo) throws Exception;
    
    void updateSiteMenu(SiteMenuVO vo) throws Exception;
    
    void deleteSiteMenu(SiteMenuVO vo) throws Exception;
    
    void deleteSiteMenuBySiteId(SiteMenuVO vo) throws Exception;
    
    void updateSiteMenuMove(String sinId, String jsonData) throws Exception;
    
    void siteMenuDistribute(String sinId) throws Exception;
    
    //추가사항
    
    List<SiteMenuVO> selectParntsSiteMenuList(SiteMenuVO vo) throws Exception;
    
    List<SiteMenuVO> selectChldrnSiteMenuList(SiteMenuVO vo) throws Exception;
    
    void siteMenuDistribute(HttpServletRequest request, String siteId) throws Exception;
    
}