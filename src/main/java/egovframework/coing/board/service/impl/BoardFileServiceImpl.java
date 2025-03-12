package egovframework.coing.board.service.impl;

import egovframework.coing.board.service.BoardFileService;
import egovframework.coing.board.vo.BoardFileVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service("boardFileService")
public class BoardFileServiceImpl extends EgovAbstractServiceImpl implements BoardFileService {
    
    private final BoardFileMapper boardFileMapper;
    
    @Override
    public Map<String, Object> selectBoardFileList(BoardFileVO vo) throws Exception {
        
        List<BoardFileVO> result = new ArrayList<BoardFileVO>();
        int cnt = boardFileMapper.selectBoardFileListCnt(vo);
        if(cnt > 0) {
            result = boardFileMapper.selectBoardFileList(vo);
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));
        
        return map;
    }
    
    @Override
    public List<BoardFileVO> selectBoardFileListAll(BoardFileVO vo) throws Exception {
        
        return boardFileMapper.selectBoardFileListAll(vo);
    }
    
    @Override
    public BoardFileVO selectBoardFile(BoardFileVO vo) throws Exception {
        
        return boardFileMapper.selectBoardFile(vo);
    }
    
    @Override
    public void insertBoardFile(BoardFileVO vo) throws Exception {
        
        boardFileMapper.insertBoardFile(vo);
    }
    
    @Override
    public void updateBoardFile(BoardFileVO vo) throws Exception {
        
        boardFileMapper.updateBoardFile(vo);
    }
    
    @Override
    public void deleteBoardFile(BoardFileVO vo) throws Exception {
        
        boardFileMapper.deleteBoardFile(vo);
    }
    
}