package egovframework.coing.survey.vo;

import java.util.ArrayList;
import java.util.List;

public class SurveyQuestionVO extends SurveyQuestion {

	private int totalAnswerCnt = 0;
	
	private List<SurveyExampleVO> exampleList = new ArrayList<SurveyExampleVO>();
	
	private List<SurveyAnswerVO> answerList = new ArrayList<SurveyAnswerVO>();
		
	public int getTotalAnswerCnt() {
		return totalAnswerCnt;
	}

	public void setTotalAnswerCnt(int totalAnswerCnt) {
		this.totalAnswerCnt = totalAnswerCnt;
	}

	public List<SurveyExampleVO> getExampleList() {
		return exampleList;
	}

	public void setExampleList(List<SurveyExampleVO> exampleList) {
		this.exampleList = exampleList;
	}

	public List<SurveyAnswerVO> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<SurveyAnswerVO> answerList) {
		this.answerList = answerList;
	}	
	
}
