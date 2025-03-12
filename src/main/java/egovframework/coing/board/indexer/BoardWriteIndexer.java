package egovframework.coing.board.indexer;

import egovframework.coing.cmm.util.SearchAbstractUtil;
import egovframework.coing.cmm.util.SearchUtil;
import egovframework.coing.cmm.vo.SearchDocumentVO;

import java.util.List;

public class BoardWriteIndexer extends SearchAbstractUtil implements SearchUtil {
    
    @Override
    public void delete(SearchDocumentVO vo) throws Exception {
        if(vo == null) {
            return;
        }
        deleteIndexKey("BOARD", vo);
    }
    
    @Override
    public void create(SearchDocumentVO vo) throws Exception {
        if(vo == null) {
            return;
        }
        addIndex("BOARD", "CREATE", vo);
    }
    
    @Override
    public void create(List<SearchDocumentVO> list) throws Exception {
        if(list == null) {
            return;
        }
        deleteIndexType("BOARD");
        addIndexes("BOARD", "CREATE", list);
    }
}