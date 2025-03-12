package egovframework.coing.board.service.impl;

import egovframework.coing.board.service.BoardCategoryService;
import egovframework.coing.board.vo.BoardCategoryVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("boardCategoryService")
public class BoardCategoryServiceImpl extends EgovAbstractServiceImpl implements BoardCategoryService {
    
    private final BoardCategoryMapper boardCategoryMapper;
    
    @Override
    public List<BoardCategoryVO> selectBoardCategoryList(BoardCategoryVO vo) throws Exception {
        
        return boardCategoryMapper.selectBoardCategoryList(vo);
    }
    
    @Override
    public BoardCategoryVO selectBoardCategory(BoardCategoryVO vo) throws Exception {
        
        return boardCategoryMapper.selectBoardCategory(vo);
    }
    
    @Override
    public int selectBoardCategoryMaxOrderNo(BoardCategoryVO vo) throws Exception {
        
        return boardCategoryMapper.selectBoardCategoryMaxOrderNo(vo);
    }
    
    @Override
    public void insertBoardCategory(BoardCategoryVO vo) throws Exception {
        
        boardCategoryMapper.insertBoardCategory(vo);
    }
    
    @Override
    public void updateBoardCategory(BoardCategoryVO vo) throws Exception {
        
        boardCategoryMapper.updateBoardCategory(vo);
    }
    
    @Override
    public void deleteBoardCategory(BoardCategoryVO vo) throws Exception {
        
        boardCategoryMapper.deleteBoardCategory(vo);
    }
    
}