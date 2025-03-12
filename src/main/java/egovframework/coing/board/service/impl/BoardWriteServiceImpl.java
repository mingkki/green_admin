package egovframework.coing.board.service.impl;

import egovframework.coing.board.service.BoardWriteService;
import egovframework.coing.board.vo.BoardWriteVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service("boardWriteService")
public class BoardWriteServiceImpl extends EgovAbstractServiceImpl implements BoardWriteService {
    
    private final BoardWriteMapper boardWriteMapper;
    
    @Override
    public int selectBoardWriteListCnt(BoardWriteVO vo) throws Exception {
        
        return boardWriteMapper.selectBoardWriteListCnt(vo);
    }
    
    @Override
    public List<BoardWriteVO> selectBoardWriteListAll(BoardWriteVO vo) throws Exception {
        
        return boardWriteMapper.selectBoardWriteListAll(vo);
    }
    
    @Override
    public Map<String, Object> selectBoardWriteList(BoardWriteVO vo) throws Exception {
        
        List<BoardWriteVO> result = new ArrayList<BoardWriteVO>();
        int cnt = boardWriteMapper.selectBoardWriteListCnt(vo);
        if(cnt > 0) {
            result = boardWriteMapper.selectBoardWriteList(vo);
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));
        
        return map;
    }
    
    
}