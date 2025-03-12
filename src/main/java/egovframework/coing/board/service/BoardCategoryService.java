package egovframework.coing.board.service;

import egovframework.coing.board.vo.BoardCategoryVO;

import java.util.List;

public interface BoardCategoryService {
    
    List<BoardCategoryVO> selectBoardCategoryList(BoardCategoryVO vo) throws Exception;
    
    BoardCategoryVO selectBoardCategory(BoardCategoryVO vo) throws Exception;
    
    int selectBoardCategoryMaxOrderNo(BoardCategoryVO vo) throws Exception;
    
    void insertBoardCategory(BoardCategoryVO vo) throws Exception;
    
    void updateBoardCategory(BoardCategoryVO vo) throws Exception;
    
    void deleteBoardCategory(BoardCategoryVO vo) throws Exception;
    
}