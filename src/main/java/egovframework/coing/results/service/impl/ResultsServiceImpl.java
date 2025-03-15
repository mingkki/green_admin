package egovframework.coing.results.service.impl;

import egovframework.coing.results.service.ResultsService;
import egovframework.coing.results.vo.ResultsInfoVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("resultsService")
public class ResultsServiceImpl extends EgovAbstractServiceImpl implements ResultsService {

	@Resource(name="resultsMapper")
	private ResultsMapper resultsMapper;

	@Override
	public Map<String, Object> selectInfoList(ResultsInfoVO vo) throws Exception {

		List<ResultsInfoVO> result = new ArrayList<ResultsInfoVO>();
		int cnt = resultsMapper.selectInfoListCnt(vo);
		if (cnt > 0) {
			result = resultsMapper.selectInfoList(vo);
		}
						
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return map;
	}	
	
	@Override
	public ResultsInfoVO selectInfo(ResultsInfoVO vo) throws Exception {
			
		return resultsMapper.selectInfo(vo);
	}

	@Override
	public void insertInfo(ResultsInfoVO vo) throws Exception {

		resultsMapper.insertInfo(vo);
	}

	@Override
	public void updateInfo(ResultsInfoVO vo) throws Exception {

		resultsMapper.updateInfo(vo);
	}
	
	@Override
	public void deleteInfo(ResultsInfoVO vo) throws Exception {

		resultsMapper.deleteInfo(vo);
	}

}