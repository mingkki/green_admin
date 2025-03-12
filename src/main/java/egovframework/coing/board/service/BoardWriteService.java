package egovframework.coing.board.service;

import egovframework.coing.board.vo.BoardWriteVO;

import java.util.List;
import java.util.Map;

public interface BoardWriteService {
    
    int selectBoardWriteListCnt(BoardWriteVO vo) throws Exception;
    
    List<BoardWriteVO> selectBoardWriteListAll(BoardWriteVO vo) throws Exception;
    
    Map<String, Object> selectBoardWriteList(BoardWriteVO vo) throws Exception;
    
}