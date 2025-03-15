package egovframework.coing.results.service.impl;

import egovframework.coing.results.vo.ResultsInfoVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("resultsMapper")
public interface ResultsMapper {

	Integer selectInfoListCnt(ResultsInfoVO vo) throws Exception;

	List<ResultsInfoVO> selectInfoList(ResultsInfoVO vo) throws Exception;

	ResultsInfoVO selectInfo(ResultsInfoVO vo) throws Exception;

	void insertInfo(ResultsInfoVO vo) throws Exception;

	void updateInfo(ResultsInfoVO vo) throws Exception;

	void deleteInfo(ResultsInfoVO vo) throws Exception;


}
