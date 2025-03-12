package egovframework.coing.board.service.impl;

import egovframework.coing.board.vo.BoardCategoryVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("boardCategoryMapper")
public interface BoardCategoryMapper {
    
    List<BoardCategoryVO> selectBoardCategoryList(BoardCategoryVO vo) throws Exception;
    
    BoardCategoryVO selectBoardCategory(BoardCategoryVO vo) throws Exception;
    
    Integer selectBoardCategoryMaxOrderNo(BoardCategoryVO vo) throws Exception;
    
    void insertBoardCategory(BoardCategoryVO vo) throws Exception;
    
    void updateBoardCategory(BoardCategoryVO vo) throws Exception;
    
    void deleteBoardCategory(BoardCategoryVO vo) throws Exception;
    
}