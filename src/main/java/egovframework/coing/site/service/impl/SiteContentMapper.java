package egovframework.coing.site.service.impl;

import egovframework.coing.cmm.vo.SearchDocumentVO;
import egovframework.coing.site.vo.SiteContentCommentVO;
import egovframework.coing.site.vo.SiteContentVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("siteContentMapper")
public interface SiteContentMapper {
    
    SiteContentVO selectSiteContent(SiteContentVO vo) throws Exception;
    
    void insertSiteContent(SiteContentVO vo) throws Exception;
    
    void updateSiteContent(SiteContentVO vo) throws Exception;
    
    void updateSiteContentPublish(SiteContentVO vo) throws Exception;
    
    void deleteSiteContent(SiteContentVO vo) throws Exception;
    
    SearchDocumentVO selectSearchSiteContent(SearchDocumentVO vo) throws Exception;
    
    List<SearchDocumentVO> selectSearchSiteContentList(SearchDocumentVO vo) throws Exception;
    
    //추가사항
    
    SiteContentVO selectSiteContentByMenu(SiteContentVO vo) throws Exception;
    
    void deleteSiteContentBySiteId(SiteContentVO vo) throws Exception;
    
    Integer selectSiteContentCommentListCnt(SiteContentCommentVO vo) throws Exception;
    
    List<SiteContentCommentVO> selectSiteContentCommentList(SiteContentCommentVO vo) throws Exception;
    
    
}