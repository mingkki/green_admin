package egovframework.coing.board.service;

import egovframework.coing.board.vo.BoardFileVO;

import java.util.List;
import java.util.Map;

public interface BoardFileService {
    
    Map<String, Object> selectBoardFileList(BoardFileVO vo) throws Exception;
    
    List<BoardFileVO> selectBoardFileListAll(BoardFileVO vo) throws Exception;
    
    BoardFileVO selectBoardFile(BoardFileVO vo) throws Exception;
    
    void insertBoardFile(BoardFileVO vo) throws Exception;
    
    void updateBoardFile(BoardFileVO vo) throws Exception;
    
    void deleteBoardFile(BoardFileVO vo) throws Exception;
    
}