package egovframework.coing.search.service.impl;

import egovframework.coing.board.indexer.BoardFileIndexer;
import egovframework.coing.board.indexer.BoardWriteIndexer;
import egovframework.coing.board.service.impl.BoardInfoMapper;
import egovframework.coing.cmm.vo.SearchDocumentVO;
import egovframework.coing.search.service.SearchService;
import egovframework.coing.site.indexer.SiteContentIndexer;
import egovframework.coing.site.indexer.SiteMenuIndexer;
import egovframework.coing.site.service.impl.SiteMenuMapper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("searchService")
public class SearchServiceImpl extends EgovAbstractServiceImpl implements SearchService {
    
    private final SiteMenuMapper siteMenuMapper;
    private final BoardInfoMapper boardInfoMapper;

    @Override
    public void createIndexAll() throws Exception {
        
        SearchDocumentVO searchDocumentVO = null;
        List<SearchDocumentVO> searchDocumentList = null;
        
        searchDocumentVO = new SearchDocumentVO();
        searchDocumentList = siteMenuMapper.selectSearchSiteMenuList(searchDocumentVO);
        SiteMenuIndexer siteMenuIndexer = new SiteMenuIndexer();
        siteMenuIndexer.create(searchDocumentList);
        
        searchDocumentVO = new SearchDocumentVO();
        searchDocumentList = boardInfoMapper.selectSearchBoardWriteList(searchDocumentVO);
        BoardWriteIndexer boardWriteIndexer = new BoardWriteIndexer();
        boardWriteIndexer.create(searchDocumentList);
        
        searchDocumentVO = new SearchDocumentVO();
        searchDocumentList = boardInfoMapper.selectSearchBoardFileList(searchDocumentVO);
        BoardFileIndexer boardFileIndexer = new BoardFileIndexer();
        boardFileIndexer.create(searchDocumentList);
    }
    
}