package egovframework.coing.survey.vo;

import java.util.ArrayList;
import java.util.List;

public class SurveyExampleVO extends SurveyExample {

	private int answerCnt;
	
	private String[] svexIdArr;
	
	private String[] svexTitleArr;
	
	private ArrayList<Integer> insertOrUpdateExIds = new ArrayList<Integer>();
	
	private List<SurveyAnswerVO> answerList = new ArrayList<SurveyAnswerVO>();
	
	public int getAnswerCnt() {
		return answerCnt;
	}

	public void setAnswerCnt(int answerCnt) {
		this.answerCnt = answerCnt;
	}

	public String[] getSvexIdArr() {
		return svexIdArr;
	}

	public void setSvexIdArr(String[] svexIdArr) {
		this.svexIdArr = svexIdArr;
	}

	public String[] getSvexTitleArr() {
		return svexTitleArr;
	}

	public void setSvexTitleArr(String[] svexTitleArr) {
		this.svexTitleArr = svexTitleArr;
	}

	public ArrayList<Integer> getInsertOrUpdateExIds() {
		return insertOrUpdateExIds;
	}

	public void setInsertOrUpdateExIds(ArrayList<Integer> insertOrUpdateExIds) {
		this.insertOrUpdateExIds = insertOrUpdateExIds;
	}

	public List<SurveyAnswerVO> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<SurveyAnswerVO> answerList) {
		this.answerList = answerList;
	}
		
}