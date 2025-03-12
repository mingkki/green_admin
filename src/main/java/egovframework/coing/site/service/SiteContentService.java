package egovframework.coing.site.service;

import egovframework.coing.site.vo.SiteContentCommentVO;
import egovframework.coing.site.vo.SiteContentVO;

import java.util.Map;

public interface SiteContentService {
    
    SiteContentVO selectSiteContent(SiteContentVO vo) throws Exception;
    
    void insertSiteContent(SiteContentVO vo) throws Exception;
    
    void updateSiteContent(SiteContentVO vo, SiteContentVO origVO) throws Exception;
    
    void updateSiteContentPublish(SiteContentVO vo) throws Exception;
    
    void deleteSiteContent(SiteContentVO vo) throws Exception;
    
    //추가사항 restoreSiteContent는 만들지 말지 스탑
    
    
    SiteContentVO selectSiteContentByMenu(SiteContentVO vo) throws Exception;
    
    void updateSiteContent(SiteContentVO vo) throws Exception;
    
    boolean restoreSiteContent(SiteContentVO vo, int historyId) throws Exception;
    
    void deleteSiteContentBySiteId(SiteContentVO vo) throws Exception;
    
    Map<String, Object> selectSiteContentCommentList(SiteContentCommentVO vo) throws Exception;
    
}