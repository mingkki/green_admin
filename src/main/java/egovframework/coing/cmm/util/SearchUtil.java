package egovframework.coing.cmm.util;

import egovframework.coing.cmm.vo.SearchDocumentVO;

import java.util.List;

public interface SearchUtil {
    
    void delete(SearchDocumentVO vo) throws Exception;
    
    void create(SearchDocumentVO vo) throws Exception;
    
    void create(List<SearchDocumentVO> list) throws Exception;
    
}