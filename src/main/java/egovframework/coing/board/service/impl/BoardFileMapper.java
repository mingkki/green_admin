package egovframework.coing.board.service.impl;

import egovframework.coing.board.vo.BoardFileVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("boardFileMapper")
public interface BoardFileMapper {
    
    Integer selectBoardFileListCnt(BoardFileVO vo) throws Exception;
    
    List<BoardFileVO> selectBoardFileList(BoardFileVO vo) throws Exception;
    
    List<BoardFileVO> selectBoardFileListAll(BoardFileVO vo) throws Exception;
    
    BoardFileVO selectBoardFile(BoardFileVO vo) throws Exception;
    
    void insertBoardFile(BoardFileVO vo) throws Exception;
    
    void updateBoardFile(BoardFileVO vo) throws Exception;
    
    void deleteBoardFile(BoardFileVO vo) throws Exception;
    
}