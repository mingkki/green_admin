package egovframework.coing.survey.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.survey.service.SurveyService;
import egovframework.coing.survey.vo.*;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 설문 관리를 위한 컨트롤러  클래스
 * @author 기술개발부 장남종
 * @since 2019.08.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2019.08.15  장남종          최초 생성
 *  2020.01.22  곽민성          수정 및 적용
 *
 *  </pre>
 */
@Controller
@RequestMapping(value="/survey.do")
public class SurveyController {

    @Resource(name="propertiesService")
    protected EgovPropertyService propertyService;

	@Resource(name="surveyService")
	private SurveyService surveyService;
			
	private final String CONTENT_PATH = String.format("%s/egovframework/coing/survey/", CmsManager.getModulePath());
	
    /**
     * 프로그램내에서 사용할 파라미터를 초기화한다.
     */	
	private void initParam(SurveyInfoVO searchVO) {
		
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("progress", searchVO.getProgress());
    	param.put("svinUseYn", searchVO.getSvinUseYn());
    	param.put("searchCondition", searchVO.getSearchCondition());
    	param.put("searchKeyword", searchVO.getSearchKeyword());
    	param.put("pageIndex", searchVO.getPageIndex());

    	searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
	}
	
	@RequestMapping()
	public String list(@ModelAttribute("searchSurveyInfoVO") SurveyInfoVO searchVO,
			@RequestParam Map<String, Object> paramMap,
			HttpServletRequest request,
			ModelMap model) throws Exception {
				
		initParam(searchVO);
		
		searchVO.setPageUnit(propertyService.getInt("pageUnit"));
    	searchVO.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

    	Map<String, Object> param = new HashMap<String, Object>();
    	param.putAll(paramMap);
    	param.put("pageIndex", null);
    	String paginationQueryString = MapQuery.urlEncodeUTF8(param);

		Map<String, Object> map = surveyService.selectInfoList(searchVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));

		paginationInfo.setTotalRecordCount(totCnt);

		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("paginationQueryString", paginationQueryString);
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
    	model.addAttribute("CONTENT_FILE", CONTENT_PATH + "info_list.jsp");
		return "egovframework/coing/common/admin_view";
	}
	
    @RequestMapping(params="act=write", method=RequestMethod.GET)
	public String form(@ModelAttribute("searchSurveyInfoVO") SurveyInfoVO searchVO,
			@ModelAttribute("writeSurveyInfo") SurveyInfoVO writeSurveyInfoVO,
			@RequestParam(value="svinId", required=false, defaultValue="0") int svinId,
			ModelMap model) throws Exception {

    	initParam(searchVO);
    	
    	String command = (svinId < 1) ? "insert" : "update";
    	
    	if ("update".equals(command)) {
    		writeSurveyInfoVO = surveyService.selectInfo(searchVO);
    		if (writeSurveyInfoVO == null) {
    			model.addAttribute("message", "survey.info.nodata");
    			return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
    		}
    	} else {
    		writeSurveyInfoVO.setSvinOpenYn("N");
    		writeSurveyInfoVO.setSvinDplctnYn("Y");
    		writeSurveyInfoVO.setSvinResopenYn("Y");
    		writeSurveyInfoVO.setSvinUseYn("Y");
    	}
    	
	    model.addAttribute("command", command);
	    model.addAttribute("writeSurveyInfo", writeSurveyInfoVO);
		model.addAttribute("CONTENT_FILE", CONTENT_PATH + "info_write.jsp");
		return "egovframework/coing/common/admin_view";
    }
    
    
    @RequestMapping(params="act=write", method=RequestMethod.POST)
	public String write(@ModelAttribute("searchSurveyInfoVO") SurveyInfoVO searchVO,
			@ModelAttribute("writeSurveyInfo") SurveyInfoVO writeSurveyInfoVO,
			@RequestParam(value="command", required=true, defaultValue="insert") String command,
			HttpServletRequest request,
			ModelMap model) throws Exception {

    	initParam(searchVO);
    	    	
    	LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	    	
    	String message = "";

    	if ("insert".equals(command)) {
   			message = "common.success.insert";
   			writeSurveyInfoVO.setSvinRegId(loginVO.getId());
   			writeSurveyInfoVO.setSvinRegIp(request.getRemoteAddr());
   			surveyService.insertInfo(writeSurveyInfoVO);
    	} else {
        	SurveyInfoVO origVO = surveyService.selectInfo(searchVO);
    		if (origVO == null) {
    			model.addAttribute("message", "survey.info.nodata");
    			return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
    		}	
		
    		message = "common.success.update";
    		writeSurveyInfoVO.setSvinUpdtId(loginVO.getId());
    		writeSurveyInfoVO.setSvinUpdtIp(request.getRemoteAddr());
    		surveyService.updateInfo(writeSurveyInfoVO);
    	}

    	model.addAttribute("message", message);
    	return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
    }    
    
    @RequestMapping(params="act=qlist")
	public String qlist(@ModelAttribute("searchSurveyInfoVO") SurveyInfoVO searchVO,
			@RequestParam(value="svinId", required=true) int svinId,
			ModelMap model) throws Exception {

    	initParam(searchVO);
    	
    	SurveyInfoVO surveyInfoVO = surveyService.selectInfo(searchVO);
		if (surveyInfoVO == null) {
			model.addAttribute("message", "survey.info.nodata");
			return CmsManager.alert(CONTENT_PATH + "question_result.jsp", model);
		} 
		
		SurveyQuestionVO searchQuestionVO = new SurveyQuestionVO();
		searchQuestionVO.setSvinId(svinId);
		List<SurveyQuestionVO> questionList = surveyService.selectQuestionList(searchQuestionVO);
		
		model.addAttribute("surveyInfoVO", surveyInfoVO);
		model.addAttribute("surveyQuestionList", questionList);		
		model.addAttribute("CONTENT_FILE", CONTENT_PATH + "question_list.jsp");
		return "egovframework/coing/common/admin_view";
    }       
	
    @RequestMapping(params="act=qwrite", method=RequestMethod.GET)
	public String qwriteForm(@ModelAttribute("searchSurveyInfoVO") SurveyInfoVO searchVO,
			@ModelAttribute("searchSurveyQuestionVO") SurveyQuestionVO searchSurveyQuestionVO,
			@ModelAttribute("writeSurveyQuestion") SurveyQuestionVO writeSurveyQuestionVO,
			@RequestParam(value="svinId", required=true) int svinId,
			@RequestParam(value="svquId", required=false, defaultValue="0") int svquId,
			ModelMap model) throws Exception {

    	initParam(searchVO);
    	    	
		List<SurveyExampleVO> surveyExampleList = new ArrayList<SurveyExampleVO>();
		
    	String command = (svquId < 1) ? "insert" : "update";

    	if ("insert".equals(command)) {
    		writeSurveyQuestionVO.setSvquType("A");
    		writeSurveyQuestionVO.setSvquReqYn("Y");
    	} else {
    		writeSurveyQuestionVO = surveyService.selectQuestion(searchSurveyQuestionVO);
    		if (writeSurveyQuestionVO == null) {
    			model.addAttribute("message", "survey.question.nodata");
    			return CmsManager.alert(CONTENT_PATH + "question_result.jsp", model);
    		}    		
    		
    		SurveyExampleVO searchExampleVO = new SurveyExampleVO();
    		searchExampleVO.setSvinId(svinId);
    		searchExampleVO.setSvquId(svquId);
    		surveyExampleList = surveyService.selectExampleList(searchExampleVO);
    	}

    	model.addAttribute("command", command);
    	model.addAttribute("surveyExampleList", surveyExampleList);
		model.addAttribute("writeSurveyQuestion", writeSurveyQuestionVO);		
		model.addAttribute("CONTENT_FILE", CONTENT_PATH + "question_write.jsp");
		return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params="act=qwrite", method=RequestMethod.POST)
	public String writeSubmit(@ModelAttribute("searchSurveyInfoVO") SurveyInfoVO searchVO,
			@ModelAttribute("writeSurveyQuestion") SurveyQuestionVO writeSurveyQuestionVO,
			@RequestParam(value="command", required=true, defaultValue="insert") String command,
			@RequestParam(value="svinId", required=true) int svinId,
			HttpServletRequest request,
			ModelMap model) throws Exception {

    	initParam(searchVO);
    	
    	String[] exIds = request.getParameterValues("ex_ids");
    	String[] exTitles = request.getParameterValues("ex_titles");
    	
    	SurveyExampleVO writeSurveyExampleVO = new SurveyExampleVO();
    	writeSurveyExampleVO.setSvexIdArr(exIds);
    	writeSurveyExampleVO.setSvexTitleArr(exTitles);
    	
    	String message = "";
    	if ("insert".equals(command)) {
    		message = "common.success.insert";    		
    		surveyService.insertQuestion(writeSurveyQuestionVO, writeSurveyExampleVO);
    	} else {
    		message = "common.success.update";
    		surveyService.updateQuestion(writeSurveyQuestionVO, writeSurveyExampleVO);
    	}
    	
    	model.addAttribute("message", message);
    	return CmsManager.alert(CONTENT_PATH + "question_result.jsp", model);	
    }
    
    @RequestMapping(params="act=copy", method=RequestMethod.POST)
	public String copy(@ModelAttribute("searchSurveyInfoVO") SurveyInfoVO searchVO,
			@RequestParam(value="svinId", required=true) int svinId,
			ModelMap model) throws Exception {
    	initParam(searchVO);    	
    	
		SurveyInfoVO vo = surveyService.selectInfo(searchVO);
		if (vo == null) {
			model.addAttribute("message", "survey.info.nodata");
			return CmsManager.alert(CONTENT_PATH + "copy_result.jsp", model);
		}    	
    	
    	String message = "common.success.process";
    	surveyService.copyInfo(vo);

    	model.addAttribute("message", message);
    	return CmsManager.alert(CONTENT_PATH + "info_copy_result.jsp", model);	
    }
    
    @RequestMapping(params="act=delete", method=RequestMethod.POST)
	public String delete(@ModelAttribute("searchSurveyInfoVO") SurveyInfoVO searchVO,
			@RequestParam(value="svinId", required=true) int svinId,
			HttpServletRequest request,
			ModelMap model) throws Exception {

    	initParam(searchVO);
    	
		SurveyInfoVO vo = surveyService.selectInfo(searchVO);
		if (vo == null) {
			model.addAttribute("message", "survey.info.nodata");
			return CmsManager.alert(CONTENT_PATH + "copy_result.jsp", model);
		}    	
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		vo.setSvinDelId(loginVO.getId());
		vo.setSvinDelIp(request.getRemoteAddr());
		surveyService.deleteInfo(vo);

    	model.addAttribute("message", "common.success.delete");
    	return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
    }
    
    @RequestMapping(params="act=qdelete", method=RequestMethod.POST)
	public String qdelete(@ModelAttribute("searchSurveyInfoVO") SurveyInfoVO searchVO,
			@RequestParam(value="svinId", required=true) int svinId,
			@RequestParam(value="svquId", required=true) int svquId,
			HttpServletRequest request,
			ModelMap model) throws Exception {

    	initParam(searchVO);    	
    	
    	SurveyQuestionVO surveyQuestionVO = new SurveyQuestionVO();
    	surveyQuestionVO.setSvinId(svinId);
    	surveyQuestionVO.setSvquId(svquId);    	
    	surveyQuestionVO = surveyService.selectQuestion(surveyQuestionVO);
    	
    	if (surveyQuestionVO == null)  {
			model.addAttribute("message", "survey.question.nodata");
			return CmsManager.alert(CONTENT_PATH + "question_result.jsp", model);    		
    	}   	
    	    	
    	/*String message = "common.success.delete";*/
    	surveyService.deleteQuestion(surveyQuestionVO);

    	model.addAttribute("message", "common.success.delete");
    	return CmsManager.alert(CONTENT_PATH + "question_result.jsp", model);
    }
    
    @RequestMapping(params="act=excel")
	public String excel(@ModelAttribute("searchSurveyInfoVO") SurveyInfoVO searchVO,
			@RequestParam(value="svinId", required=true) int svinId,
			ModelMap model) throws Exception {

    	initParam(searchVO); 
    	
    	SurveyInfoVO surveyInfoVO = surveyService.selectInfo(searchVO);
		if (surveyInfoVO == null) {
			model.addAttribute("message", "survey.info.nodata");
			return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);    
		} 
		
		SurveyQuestionVO searchQuestionVO = new SurveyQuestionVO();
		searchQuestionVO.setSvinId(svinId);
		List<SurveyQuestionVO> surveyQuestionList = surveyService.selectResultList(searchQuestionVO);
		
		model.addAttribute("surveyInfoVO", surveyInfoVO);
		model.addAttribute("surveyQuestionList", surveyQuestionList);	
		return "egovframework/coing/survey/info_excel";
    }      
    
    @RequestMapping(params="act=excel2")
	public String excel2(@ModelAttribute("searchSurveyInfoVO") SurveyInfoVO searchVO,
			@RequestParam(value="svinId", required=true) int svinId,
			ModelMap model) throws Exception {

       	initParam(searchVO);

    	SurveyInfoVO surveyInfoVO = surveyService.selectInfo(searchVO);
		if (surveyInfoVO == null) {
			model.addAttribute("message", "survey.info.nodata");
			return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);    
		} 
		
		SurveyQuestionVO searchQuestionVO = new SurveyQuestionVO();
		searchQuestionVO.setSvinId(svinId);
		List<SurveyQuestionVO> surveyQuestionList = surveyService.selectQuestionList(searchQuestionVO);
		
		SurveyVoteVO searchVoteVO = new SurveyVoteVO();
		searchVoteVO.setSvinId(svinId);
		List<SurveyVoteVO> surveyVoteList = surveyService.selectVoteList(searchVoteVO);
		
		LinkedHashMap<Integer, HashMap<Integer, String>> surveyVoteResultList = new LinkedHashMap<Integer, HashMap<Integer, String>>();
		
		if (surveyVoteList != null && surveyVoteList.size() > 0) {
			SurveyVoteVO tvo = null;
			SurveyAnswerVO avo = null;
			List<SurveyAnswerVO> alist = null;
			for (int i = 0; i < surveyVoteList.size(); i++) {
				tvo = surveyVoteList.get(i);
				avo = new SurveyAnswerVO();
				avo.setSvinId(svinId);
				avo.setSvvoId(tvo.getSvvoId());
				alist = surveyService.selectAnswerListBySvvoId(avo);
				if (alist != null && alist.size() > 0) {
					SurveyAnswerVO avo2 = null;
					HashMap<Integer, String> n = new HashMap<Integer, String>();
					for (int j = 0; j < alist.size(); j++) {
						avo2 = alist.get(j);
						if (avo2.getSvexId() < 1) {
							n.put(avo2.getSvquId(), avo2.getSvanRemarks());
						} else {
							if (!"".equals(EgovStringUtil.nullConvert(avo2.getSvanRemarks()))) {
								n.put(avo2.getSvquId(), avo2.getSvexTitle()+"|"+avo2.getSvanRemarks());
							} else {
								n.put(avo2.getSvquId(), avo2.getSvexTitle()+"");
							}
						}
					}
					surveyVoteResultList.put(tvo.getSvvoId(), n);
				}
			}
		}

		model.addAttribute("surveyInfoVO", surveyInfoVO);
		model.addAttribute("surveyQuestionList", surveyQuestionList);
		model.addAttribute("surveyVoteList", surveyVoteList);
		model.addAttribute("surveyVoteResultList", surveyVoteResultList);		
		return "egovframework/coing/survey/info_excel2";
    }

}