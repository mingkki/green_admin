package egovframework.coing.survey.service.impl;

import egovframework.coing.cmm.vo.SearchDocumentVO;
import egovframework.coing.survey.vo.*;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("surveyMapper")
public interface SurveyMapper {

	Integer selectInfoListCnt(SurveyInfoVO vo) throws Exception;

	List<SurveyInfoVO> selectInfoList(SurveyInfoVO vo) throws Exception;

	SurveyInfoVO selectInfo(SurveyInfoVO vo) throws Exception;

	void insertInfo(SurveyInfoVO vo) throws Exception;

	void updateInfo(SurveyInfoVO vo) throws Exception;

	void deleteInfo(SurveyInfoVO vo) throws Exception;

	List<SurveyQuestionVO> selectQuestionList(SurveyQuestionVO vo) throws Exception;

	SurveyQuestionVO selectQuestion(SurveyQuestionVO vo) throws Exception;

	void insertQuestion(SurveyQuestionVO vo) throws Exception;

	void updateQuestion(SurveyQuestionVO vo) throws Exception;

	void deleteQuestion(SurveyQuestionVO vo) throws Exception;

	List<SurveyExampleVO> selectExampleList(SurveyExampleVO vo) throws Exception;

	SurveyExampleVO selectExample(SurveyExampleVO vo) throws Exception;

	void insertExample(SurveyExampleVO vo) throws Exception;

	void updateExample(SurveyExampleVO vo) throws Exception;

	void deleteExampleNotInKeys(SurveyExampleVO vo) throws Exception;

	void deleteExample(SurveyExampleVO vo) throws Exception;

	List<SurveyAnswerVO> selectAnswerList(SurveyAnswerVO vo) throws Exception;

	List<SurveyVoteVO> selectVoteList(SurveyVoteVO vo) throws Exception;

	List<SurveyAnswerVO> selectAnswerListBySvvoId(SurveyAnswerVO vo) throws Exception;
	
	List<SearchDocumentVO> selectSearchSurveyList(SearchDocumentVO vo) throws Exception;
}
