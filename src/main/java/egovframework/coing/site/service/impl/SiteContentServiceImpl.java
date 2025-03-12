package egovframework.coing.site.service.impl;

import egovframework.coing.cmm.Globals;
import egovframework.coing.cmm.util.EgovFileTool;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.cmm.vo.SearchDocumentVO;
import egovframework.coing.site.indexer.SiteContentIndexer;
import egovframework.coing.site.service.SiteContentService;
import egovframework.coing.site.vo.SiteContentCommentVO;
import egovframework.coing.site.vo.SiteContentVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service("siteContentService")
public class SiteContentServiceImpl extends EgovAbstractServiceImpl implements SiteContentService {
    
    private final SiteContentMapper siteContentMapper;
    private final EgovIdGnrService egovSiteContentIdGnrService;
    
    @Override
    public SiteContentVO selectSiteContent(SiteContentVO vo) throws Exception {
        
        return siteContentMapper.selectSiteContent(vo);
    }
    
    @Override//추가건
    public SiteContentVO selectSiteContentByMenu(SiteContentVO vo) throws Exception {
        
        return siteContentMapper.selectSiteContentByMenu(vo);
    }
    
    @Override
    public void insertSiteContent(SiteContentVO vo) throws Exception {
        
        vo.setContentId(egovSiteContentIdGnrService.getNextIntegerId());
        siteContentMapper.insertSiteContent(vo);
    }
    
    @Override
    public void updateSiteContent(SiteContentVO vo, SiteContentVO origVO) throws Exception {
        
        siteContentMapper.updateSiteContent(vo);
        
        String scoContent = EgovStringUtil.nullConvert(vo.getContent());
        String scoContentOrig = EgovStringUtil.nullConvert(origVO.getContent());
        
        if(!scoContent.equals(scoContentOrig)) {
        
        }
    }
    
    @Override
    public void updateSiteContentPublish(SiteContentVO vo) throws Exception {
        
        writeContentFile(vo);
        
        vo.setScoVersion(vo.getScoVersion() + 1);
        siteContentMapper.updateSiteContentPublish(vo);
        
        if(vo.getSmeId() > 0) {
            SearchDocumentVO searchDocumentVO = new SearchDocumentVO();
            searchDocumentVO.setSiteId(vo.getSinId());
            searchDocumentVO.setCode(vo.getSmeId() + "");
            searchDocumentVO = siteContentMapper.selectSearchSiteContent(searchDocumentVO);
            SiteContentIndexer siteContentIndexer = new SiteContentIndexer();
            siteContentIndexer.create(searchDocumentVO);
        }
    }
    
    @Override
    public void deleteSiteContent(SiteContentVO vo) throws Exception {
        
        siteContentMapper.deleteSiteContent(vo);
        
        if(vo.getSmeId() > 0) {
            SearchDocumentVO searchDocumentVO = new SearchDocumentVO();
            searchDocumentVO.setSiteId(vo.getSinId());
            searchDocumentVO.setCode(vo.getSmeId() + "");
            searchDocumentVO = siteContentMapper.selectSearchSiteContent(searchDocumentVO);
            SiteContentIndexer siteContentIndexer = new SiteContentIndexer();
            siteContentIndexer.delete(searchDocumentVO);
        }
    }
    
    @Override//추가건
    public void deleteSiteContentBySiteId(SiteContentVO vo) throws Exception {
        
        siteContentMapper.deleteSiteContentBySiteId(vo);
    }
    
    
    public void writeContentFile(SiteContentVO writeSiteContentVO) throws Exception {
        
        String fileNm = "";
        String fileContent = "";
        
        // 메인컨텐츠인 경우
        if(writeSiteContentVO.getSmeId() < 1) {
            fileContent = EgovStringUtil.nullConvert(writeSiteContentVO.getContent());
            fileNm = "main.jsp";
        } else {
            String head = "<%@ page contentType=\"text/html; charset=utf-8\" pageEncoding=\"utf-8\" %>\n";
            fileContent = head.concat(EgovStringUtil.nullConvert(writeSiteContentVO.getContent()));
            fileNm = writeSiteContentVO.getSmeId() + ".jsp";
        }
        
        String filePath = Globals.DISTRIBUTE_PATH + "/" + writeSiteContentVO.getSinId() + "/content/";
        filePath = filePath.replace('\\', File.separatorChar).replace('/', File.separatorChar);
        
        String fileAbsolutePath = EgovFileTool.createNewFile(filePath.concat(fileNm));
        FileUtils.writeStringToFile(new File(fileAbsolutePath), fileContent, "UTF-8");
    }

    @Override //추가건 : 사이트 컨텐츠 백업 보류
    public boolean restoreSiteContent(SiteContentVO vo, int historyId) throws Exception {



        return true;
    }

    @Override //추가건
    public Map<String, Object> selectSiteContentCommentList(SiteContentCommentVO vo) throws Exception {
        
        List<SiteContentCommentVO> result = new ArrayList<SiteContentCommentVO>();
        int cnt = siteContentMapper.selectSiteContentCommentListCnt(vo);
        if(cnt > 0) {
            result = siteContentMapper.selectSiteContentCommentList(vo);
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));
        
        return map;
    }
    
    @Override
    public void updateSiteContent(SiteContentVO vo) throws Exception {
        siteContentMapper.updateSiteContent(vo);
        
        writeContentFile(vo);
    }

}