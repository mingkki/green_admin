package egovframework.coing.board.service.impl;

import egovframework.coing.board.service.BoardInfoService;
import egovframework.coing.board.vo.BoardInfoVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service("boardInfoService")
public class BoardInfoServiceImpl extends EgovAbstractServiceImpl implements BoardInfoService {
    
    private final BoardInfoMapper boardInfoMapper;
    private final EgovIdGnrService egovBoardIdGnrService;
    
    @Override
    public Map<String, Object> selectBoardInfoList(BoardInfoVO vo) throws Exception {
        
        List<BoardInfoVO> result = new ArrayList<BoardInfoVO>();
        int cnt = boardInfoMapper.selectBoardInfoListCnt(vo);
        if(cnt > 0) {
            result = boardInfoMapper.selectBoardInfoList(vo);
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));
        
        return map;
    }
    
    @Override
    public List<BoardInfoVO> selectBoardInfoListAll(BoardInfoVO vo) throws Exception {
        
        return boardInfoMapper.selectBoardInfoListAll(vo);
    }
    
    @Override
    public BoardInfoVO selectBoardInfo(BoardInfoVO vo) throws Exception {
        
        return boardInfoMapper.selectBoardInfo(vo);
    }
    
    @Override
    public void insertBoardInfo(BoardInfoVO vo) throws Exception {
        
        vo.setBrdId(egovBoardIdGnrService.getNextStringId());
        boardInfoMapper.insertBoardInfo(vo);
    }
    
    @Override
    public void updateBoardInfo(BoardInfoVO vo) throws Exception {
        
        boardInfoMapper.updateBoardInfo(vo);
    }
    
    @Override
    public void deleteBoardInfo(BoardInfoVO vo) throws Exception {
        
        boardInfoMapper.deleteBoardInfo(vo);
    }
    
}