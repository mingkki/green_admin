package egovframework.coing.board.service;

import egovframework.coing.board.vo.BoardInfoVO;

import java.util.List;
import java.util.Map;

public interface BoardInfoService {
    
    Map<String, Object> selectBoardInfoList(BoardInfoVO vo) throws Exception;
    
    List<BoardInfoVO> selectBoardInfoListAll(BoardInfoVO vo) throws Exception;
    
    BoardInfoVO selectBoardInfo(BoardInfoVO vo) throws Exception;
    
    void insertBoardInfo(BoardInfoVO vo) throws Exception;
    
    void updateBoardInfo(BoardInfoVO vo) throws Exception;
    
    void deleteBoardInfo(BoardInfoVO vo) throws Exception;
    
}