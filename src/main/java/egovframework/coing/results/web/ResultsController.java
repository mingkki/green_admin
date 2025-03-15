package egovframework.coing.results.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.results.service.ResultsService;
import egovframework.coing.results.vo.ResultsInfoVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
@RequiredArgsConstructor
@RequestMapping(value="/results.do")
public class ResultsController {

    @Resource(name="propertiesService")
    protected EgovPropertyService propertyService;

	@Resource(name="resultsService")
	private ResultsService resultsService;

	private final CommonService commonService;
			
	private final String CONTENT_PATH = String.format("%s/egovframework/coing/results/", CmsManager.getModulePath());

    /**
     * 프로그램내에서 사용할 파라미터를 초기화한다.
     */	
	private void initParam(ResultsInfoVO searchVO) {
		
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("progress", searchVO.getProgress());
    	param.put("searchCondition", searchVO.getSearchCondition());
    	param.put("searchKeyword", searchVO.getSearchKeyword());
    	param.put("pageIndex", searchVO.getPageIndex());

    	searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
	}
	
	@RequestMapping()
	public String list(@ModelAttribute("searchResultsInfoVO") ResultsInfoVO searchVO,
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

		Map<String, Object> map = resultsService.selectInfoList(searchVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));

		paginationInfo.setTotalRecordCount(totCnt);

		CodeDetailVO codeDetailVO = new CodeDetailVO();
		codeDetailVO.setSearchCodId("LOV_ITEM");
		List<CodeDetailVO> itemList = commonService.selectCodeDetailList(codeDetailVO);

		codeDetailVO = new CodeDetailVO();
		codeDetailVO.setSearchCodId("LOV_COMPANY");
		List<CodeDetailVO> companyList = commonService.selectCodeDetailList(codeDetailVO);

		model.addAttribute("itemList", itemList);
		model.addAttribute("companyList", companyList);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("paginationQueryString", paginationQueryString);
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
    	model.addAttribute("CONTENT_FILE", CONTENT_PATH + "info_list.jsp");
		return "egovframework/coing/common/admin_view";
	}
	
    @RequestMapping(params="act=write", method=RequestMethod.GET)
	public String form(@ModelAttribute("searchResultsInfoVO") ResultsInfoVO searchVO,
			@ModelAttribute("writeResultsInfo") ResultsInfoVO writeResultsInfoVO,
			@RequestParam(value="rsinId", required=false, defaultValue="0") int rsinId,
			ModelMap model) throws Exception {

    	initParam(searchVO);
    	
    	String command = (rsinId < 1) ? "insert" : "update";

		if ("update".equals(command)) {
    		writeResultsInfoVO = resultsService.selectInfo(searchVO);
    		if (writeResultsInfoVO == null) {
    			model.addAttribute("message", "info.nodata.msg");
    			return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
    		}
			writeResultsInfoVO.setRsinItemId(writeResultsInfoVO.getRsinItemId());
			writeResultsInfoVO.setRsinCompanyId(writeResultsInfoVO.getRsinCompanyId());
    	} else {

    	}
		CodeDetailVO codeDetailVO = new CodeDetailVO();
		codeDetailVO.setSearchCodId("LOV_ITEM");
		List<CodeDetailVO> itemList = commonService.selectCodeDetailList(codeDetailVO);

		codeDetailVO = new CodeDetailVO();
		codeDetailVO.setSearchCodId("LOV_COMPANY");
		List<CodeDetailVO> companyList = commonService.selectCodeDetailList(codeDetailVO);
    	
	    model.addAttribute("command", command);
		model.addAttribute("itemList", itemList);
		model.addAttribute("companyList", companyList);
	    model.addAttribute("writeResultsInfo", writeResultsInfoVO);
		model.addAttribute("CONTENT_FILE", CONTENT_PATH + "info_write.jsp");
		return "egovframework/coing/common/admin_view";
    }
    
    
    @RequestMapping(params="act=write", method=RequestMethod.POST)
	public String write(@ModelAttribute("searchResultsInfoVO") ResultsInfoVO searchVO,
			@ModelAttribute("writeResultsInfo") ResultsInfoVO writeResultsInfoVO,
			@RequestParam(value="command", required=true, defaultValue="insert") String command,
			HttpServletRequest request,
			ModelMap model) throws Exception {

    	initParam(searchVO);
    	    	
    	LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	    	
    	String message = "";

    	if ("insert".equals(command)) {
   			message = "common.success.insert";
   			writeResultsInfoVO.setRsinRegId(loginVO.getId());
   			writeResultsInfoVO.setRsinRegIp(request.getRemoteAddr());
   			resultsService.insertInfo(writeResultsInfoVO);
    	} else {
			ResultsInfoVO origVO = resultsService.selectInfo(searchVO);
    		if (origVO == null) {
    			model.addAttribute("message", "info.nodata.msg");
    			return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
    		}	
		
    		message = "common.success.update";
    		resultsService.updateInfo(writeResultsInfoVO);
    	}

    	model.addAttribute("message", message);
    	return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
    }    

    @RequestMapping(params="act=delete", method=RequestMethod.POST)
	public String delete(@ModelAttribute("searchResultsInfoVO") ResultsInfoVO searchVO,
			@RequestParam(value="svinId", required=true) int svinId,
			HttpServletRequest request,
			ModelMap model) throws Exception {

    	initParam(searchVO);

		ResultsInfoVO vo = resultsService.selectInfo(searchVO);
		if (vo == null) {
			model.addAttribute("message", "info.nodata.msg");
			return CmsManager.alert(CONTENT_PATH + "copy_result.jsp", model);
		}    	
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		resultsService.deleteInfo(vo);

    	model.addAttribute("message", "common.success.delete");
    	return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
    }

}