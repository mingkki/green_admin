package egovframework.coing.results.service;

import egovframework.coing.results.vo.ResultsInfoVO;
import java.util.Map;

public interface ResultsService {
	
	Map<String, Object> selectInfoList(ResultsInfoVO vo)
			throws Exception;

	ResultsInfoVO selectInfo(ResultsInfoVO vo)
			throws Exception;
	
	void insertInfo(ResultsInfoVO vo)
			throws Exception;

	void updateInfo(ResultsInfoVO vo)
			throws Exception;
	
	void deleteInfo(ResultsInfoVO vo)
			throws Exception;		
	
}