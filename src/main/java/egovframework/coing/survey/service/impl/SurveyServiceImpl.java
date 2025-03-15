package egovframework.coing.survey.service.impl;

import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.survey.service.SurveyService;
import egovframework.coing.survey.vo.*;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("surveyService")
public class SurveyServiceImpl extends EgovAbstractServiceImpl implements SurveyService {

	@Resource(name="surveyMapper")
	private SurveyMapper surveyMapper;	
	
	@Resource(name="egovSurveyInfoIdGnrService")    
	private EgovIdGnrService egovSurveyInfoIdGnrService;
	
	@Resource(name="egovSurveyQuestionIdGnrService")    
	private EgovIdGnrService egovSurveyQuestionIdGnrService;
	
	@Resource(name="egovSurveyExampleIdGnrService")    
	private EgovIdGnrService egovSurveyExampleIdGnrService;
	
	@Override
	public Map<String, Object> selectInfoList(SurveyInfoVO vo) throws Exception {

		List<SurveyInfoVO> result = new ArrayList<SurveyInfoVO>();
		int cnt = surveyMapper.selectInfoListCnt(vo);
		if (cnt > 0) {
			result = surveyMapper.selectInfoList(vo);
		}
						
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return map;
	}	
	
	@Override
	public SurveyInfoVO selectInfo(SurveyInfoVO vo) throws Exception {
			
		return surveyMapper.selectInfo(vo);
	}

	@Override
	public void insertInfo(SurveyInfoVO vo) throws Exception {
						
		vo.setSvinId(egovSurveyInfoIdGnrService.getNextIntegerId());
		surveyMapper.insertInfo(vo);
	}

	@Override
	public void updateInfo(SurveyInfoVO vo) throws Exception {

		surveyMapper.updateInfo(vo);
	}
	
	@Override
	public void deleteInfo(SurveyInfoVO vo) throws Exception {
				
		surveyMapper.deleteInfo(vo);	
	}

	@Override
	public List<SurveyQuestionVO> selectQuestionList(SurveyQuestionVO vo) throws Exception {
		
		List<SurveyQuestionVO> questionList = surveyMapper.selectQuestionList(vo);
		if (questionList != null && questionList.size() > 0) {
			SurveyExampleVO searchSurveyExampleVO;
			for (int i = 0; i < questionList.size(); i++) {
				searchSurveyExampleVO = new SurveyExampleVO();
				searchSurveyExampleVO.setSvinId(questionList.get(i).getSvinId());
				searchSurveyExampleVO.setSvquId(questionList.get(i).getSvquId());
				questionList.get(i).setExampleList(surveyMapper.selectExampleList(searchSurveyExampleVO));
			}
		}
		
		return questionList;
	}

	@Override
	public SurveyQuestionVO selectQuestion(SurveyQuestionVO vo) throws Exception {
		
		return surveyMapper.selectQuestion(vo);
	}

	@Override
	public void insertQuestion(SurveyQuestionVO vo, SurveyExampleVO exampleVO) throws Exception {
		
		vo.setSvquId(egovSurveyQuestionIdGnrService.getNextIntegerId());
		//insertSurveyQuestionVO.setFrstRegisterId(((LoginVO)EgovUserDetailsHelper.getAuthenticatedUser()).getId());
		surveyMapper.insertQuestion(vo);
		
		if (exampleVO.getSvexTitleArr() != null && exampleVO.getSvexTitleArr().length > 0) {
			String[] exTitles = exampleVO.getSvexTitleArr();
			SurveyExampleVO insertExampleVO = null;
			for (int i = 0; i < exTitles.length; i++) {							
				insertExampleVO = new SurveyExampleVO();
				insertExampleVO.setSvinId(vo.getSvinId());
				insertExampleVO.setSvquId(vo.getSvquId());
				insertExampleVO.setSvexId(egovSurveyExampleIdGnrService.getNextIntegerId());
				insertExampleVO.setSvexKey(i + 1);
				insertExampleVO.setSvexTitle(EgovStringUtil.nullConvert(exTitles[i]));
				surveyMapper.insertExample(insertExampleVO);
			}						
		}		
	}

	@Override
	public void updateQuestion(SurveyQuestionVO vo, SurveyExampleVO exampleVO) throws Exception {
		
		//updateSurveyQuestionVO.setLastUpdusrId(((LoginVO)EgovUserDetailsHelper.getAuthenticatedUser()).getId());
		surveyMapper.updateQuestion(vo);

		
		SurveyExampleVO exampleDeleteVO = new SurveyExampleVO();
		exampleDeleteVO.setSvinId(vo.getSvinId());
		exampleDeleteVO.setSvquId(vo.getSvquId());
		exampleDeleteVO.getInsertOrUpdateExIds().add(-1);
		
		if (exampleVO.getSvexTitleArr() != null && exampleVO.getSvexTitleArr().length > 0) {
			SurveyExampleVO exvo = null;
			String[] exIds = exampleVO.getSvexIdArr();
			String[] exTitles = exampleVO.getSvexTitleArr();
			Integer exId = 0;
			String exTitle = "";
			for (int i = 0; i < exTitles.length; i++) {
				try {
					exId = Integer.parseInt(EgovStringUtil.nullConvert(exIds[i]));
				} catch (Exception e) {					
				}
				exTitle = EgovStringUtil.nullConvert(exTitles[i]);
				System.out.println(exId);
				if (exId == 0) {
					exvo = new SurveyExampleVO();
					exvo.setSvinId(vo.getSvinId());
					exvo.setSvquId(vo.getSvquId());
					exvo.setSvexId(egovSurveyExampleIdGnrService.getNextIntegerId());
					exvo.setSvexKey(i + 1);
					exvo.setSvexTitle(exTitle);
					surveyMapper.insertExample(exvo);
					exampleDeleteVO.getInsertOrUpdateExIds().add(exvo.getSvexId());
				} else {
					exvo = new SurveyExampleVO();
					exvo.setSvinId(vo.getSvinId());
					exvo.setSvquId(vo.getSvquId());
					exvo.setSvexId(exId);
					exvo.setSvexKey(i + 1);
					exvo.setSvexTitle(exTitle);
					surveyMapper.updateExample(exvo);
					exampleDeleteVO.getInsertOrUpdateExIds().add(exvo.getSvexId());
				}
			}						
		}
		
		surveyMapper.deleteExampleNotInKeys(exampleDeleteVO);
	}

	@Override
	public void deleteQuestion(SurveyQuestionVO vo) throws Exception {
		
		surveyMapper.deleteQuestion(vo);			
	}
	
	@Override
	public List<SurveyExampleVO> selectExampleList(SurveyExampleVO vo) throws Exception {
		
		return surveyMapper.selectExampleList(vo);
	}

	@Override
	public SurveyExampleVO selectExample(SurveyExampleVO vo) throws Exception {
		
		return surveyMapper.selectExample(vo);
	}

	@Override
	public void insertExample(SurveyExampleVO vo) throws Exception {
		
		vo.setSvexId(egovSurveyExampleIdGnrService.getNextIntegerId());
		surveyMapper.insertExample(vo);
	}

	@Override
	public void updateExample(SurveyExampleVO vo) throws Exception {
		
		surveyMapper.updateExample(vo);
	}

	@Override
	public void deleteExample(SurveyExampleVO vo) throws Exception {
		
		surveyMapper.deleteExample(vo);
	}

	@Override
	public List<SurveyQuestionVO> selectResultList(SurveyQuestionVO vo) throws Exception {
		
		List<SurveyQuestionVO> questionList = surveyMapper.selectQuestionList(vo);
		if (questionList != null && questionList.size() > 0) {
			SurveyExampleVO searchSurveyExampleVO;
			SurveyAnswerVO searchSurveyAnswerVO;
			for (int i = 0; i < questionList.size(); i++) {
				String qstnTy = questionList.get(i).getSvquType();
				if ("C".equals(qstnTy)) {
					searchSurveyAnswerVO = new SurveyAnswerVO();
					searchSurveyAnswerVO.setSvinId(questionList.get(i).getSvinId());
					searchSurveyAnswerVO.setSvquId(questionList.get(i).getSvquId());
					questionList.get(i).setAnswerList(surveyMapper.selectAnswerList(searchSurveyAnswerVO));
				} else {
					searchSurveyExampleVO = new SurveyExampleVO();
					searchSurveyExampleVO.setSvinId(questionList.get(i).getSvinId());
					searchSurveyExampleVO.setSvquId(questionList.get(i).getSvquId());
					questionList.get(i).setExampleList(surveyMapper.selectExampleList(searchSurveyExampleVO));
					if ("D".equals(qstnTy) || "E".equals(qstnTy)) {
						if (questionList.get(i).getExampleList() != null && questionList.get(i).getExampleList().size() > 0)
						for (int j = 0; j < questionList.get(i).getExampleList().size(); j++) {
							if ((j + 1) == questionList.get(i).getExampleList().size()) {
								searchSurveyAnswerVO = new SurveyAnswerVO();
								searchSurveyAnswerVO.setSvinId(questionList.get(i).getSvinId());
								searchSurveyAnswerVO.setSvquId(questionList.get(i).getSvquId());
								searchSurveyAnswerVO.setSvexId(questionList.get(i).getExampleList().get(j).getSvexId());
								questionList.get(i).getExampleList().get(j).setAnswerList(surveyMapper.selectAnswerList(searchSurveyAnswerVO));
							}
						}
					}
				}
				
			}
		}
		
		return questionList;
	}

	@Override
	public List<SurveyVoteVO> selectVoteList(SurveyVoteVO vo) throws Exception {

		return surveyMapper.selectVoteList(vo);
	}

	@Override
	public List<SurveyAnswerVO> selectAnswerListBySvvoId(SurveyAnswerVO vo) throws Exception {

		return surveyMapper.selectAnswerListBySvvoId(vo);
	}		
	
}