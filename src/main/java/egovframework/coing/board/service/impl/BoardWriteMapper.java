package egovframework.coing.board.service.impl;

import egovframework.coing.board.vo.BoardWriteVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

@Mapper("boardWriteMapper")
public interface BoardWriteMapper {
    
    List<BoardWriteVO> selectBoardWriteListAll(BoardWriteVO vo) throws Exception;
    
    List<Map<String, Object>> selectBoardWriteListHits(Map<String, Object> map) throws Exception;
    
    Integer selectBoardWriteListCnt(BoardWriteVO vo) throws Exception;
    
    List<BoardWriteVO> selectBoardWriteList(BoardWriteVO vo) throws Exception;
    
}