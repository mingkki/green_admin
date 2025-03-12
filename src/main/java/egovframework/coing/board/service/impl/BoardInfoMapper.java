package egovframework.coing.board.service.impl;

import egovframework.coing.board.vo.BoardInfoVO;
import egovframework.coing.cmm.vo.SearchDocumentVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("boardInfoMapper")
public interface BoardInfoMapper {
    
    Integer selectBoardInfoListCnt(BoardInfoVO vo) throws Exception;
    
    List<BoardInfoVO> selectBoardInfoList(BoardInfoVO vo) throws Exception;
    
    List<BoardInfoVO> selectBoardInfoListAll(BoardInfoVO vo) throws Exception;
    
    BoardInfoVO selectBoardInfo(BoardInfoVO vo) throws Exception;
    
    void insertBoardInfo(BoardInfoVO vo) throws Exception;
    
    void updateBoardInfo(BoardInfoVO vo) throws Exception;
    
    void deleteBoardInfo(BoardInfoVO vo) throws Exception;
    
    List<SearchDocumentVO> selectSearchBoardWriteList(SearchDocumentVO vo) throws Exception;
    
    List<SearchDocumentVO> selectSearchBoardFileList(SearchDocumentVO vo) throws Exception;
    
}