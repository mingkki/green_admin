package egovframework.coing.survey.service;

import egovframework.coing.survey.vo.*;

import java.util.List;
import java.util.Map;

public interface SurveyService {
	
	Map<String, Object> selectInfoList(SurveyInfoVO vo) 
			throws Exception;
	
	SurveyInfoVO selectInfo(SurveyInfoVO vo) 
			throws Exception;
	
	void insertInfo(SurveyInfoVO vo) 
			throws Exception;
	
	void updateInfo(SurveyInfoVO vo) 
			throws Exception;
	
	void deleteInfo(SurveyInfoVO vo) 
			throws Exception;		
	
	void copyInfo(SurveyInfoVO vo) 
			throws Exception;
	
	List<SurveyQuestionVO> selectResultList(SurveyQuestionVO vo) 
			throws Exception;
	
	List<SurveyQuestionVO> selectQuestionList(SurveyQuestionVO vo) 
			throws Exception;
	
	SurveyQuestionVO selectQuestion(SurveyQuestionVO vo) 
			throws Exception;
	
	void insertQuestion(SurveyQuestionVO vo, SurveyExampleVO exampleVO) 
			throws Exception;
	
	void updateQuestion(SurveyQuestionVO vo, SurveyExampleVO exampleVO) 
			throws Exception;
	
	void deleteQuestion(SurveyQuestionVO surveyQuestionVO) 
			throws Exception;
	
	
	List<SurveyExampleVO> selectExampleList(SurveyExampleVO vo) 
			throws Exception;
	
	SurveyExampleVO selectExample(SurveyExampleVO vo) 
			throws Exception;
	
	void insertExample(SurveyExampleVO vo) 
			throws Exception;
	
	void updateExample(SurveyExampleVO vo) 
			throws Exception;
	
	void deleteExample(SurveyExampleVO vo) 
			throws Exception;		
	
	
	List<SurveyVoteVO> selectVoteList(SurveyVoteVO vo) 
			throws Exception;
	
	List<SurveyAnswerVO> selectAnswerListBySvvoId(SurveyAnswerVO vo) 
			throws Exception;
	
}