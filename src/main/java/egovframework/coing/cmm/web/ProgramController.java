package egovframework.coing.cmm.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.ProgramService;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.cmm.vo.ProgramVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/program.do")
public class ProgramController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/", CmsManager.getModulePath());
    private final EgovPropertyService propertiesService;
    private final ProgramService programService;
    
    private void initParam(ProgramVO searchVO) {
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("prgUseYn", searchVO.getPrgUseYn());
        param.put("searchCondition", searchVO.getSearchCondition());
        param.put("searchKeyword", searchVO.getSearchKeyword());
        param.put("pageIndex", searchVO.getPageIndex());
        
        searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
    }
    
    @RequestMapping()
    public String list(@ModelAttribute("searchProgramVO") ProgramVO searchVO,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
        searchVO.setPageSize(propertiesService.getInt("pageSize"));
        
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
        
        Map<String, Object> map = programService.selectProgramList(searchVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));
        
        paginationInfo.setTotalRecordCount(totCnt);
        
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("paginationQueryString", paginationQueryString);
        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "program_list.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.GET)
    public String form(@ModelAttribute("searchProgramVO") ProgramVO searchVO,
                       @ModelAttribute("writeProgram") ProgramVO writeProgramVO,
                       @RequestParam(value = "prgId", required = false, defaultValue = "0") int prgId,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        String command = (prgId < 1)
                ? "insert"
                : "update";
        
        if("update".equals(command)) {
            writeProgramVO = programService.selectProgram(searchVO);
            if(writeProgramVO == null) {
                model.addAttribute("message", "program.nodata");
                return CmsManager.alert(CONTENT_PATH + "program_result.jsp", model);
            }
        } else {
            writeProgramVO.setPrgUseYn("Y");
        }
        
        model.addAttribute("command", command);
        model.addAttribute("writeProgram", writeProgramVO);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "program_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.POST)
    public String write(@ModelAttribute("searchProgramVO") ProgramVO searchVO,
                        @ModelAttribute("writeProgram") ProgramVO writeProgramVO,
                        @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                        @RequestParam Map<String, Object> paramMap,
                        HttpServletRequest request,
                        ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        String message = "";
        
        if("insert".equals(command)) {
            message = "common.success.insert";
            writeProgramVO.setPrgRegId(loginVO.getId());
            writeProgramVO.setPrgRegIp(request.getRemoteAddr());
            programService.insertProgram(writeProgramVO);
        } else {
            // 프로그램 원글 정보
            ProgramVO origVO = programService.selectProgram(searchVO);
            if(origVO == null) {
                model.addAttribute("message", "program.nodata");
                return CmsManager.alert(CONTENT_PATH + "program_result.jsp", model);
            }
            message = "common.success.update";
            writeProgramVO.setPrgUpdtId(loginVO.getId());
            writeProgramVO.setPrgUpdtIp(request.getRemoteAddr());
            programService.updateProgram(writeProgramVO);
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "program_result.jsp", model);
    }
    
    @RequestMapping(params = "act=delete", method = RequestMethod.POST)
    public String delete(@ModelAttribute("searchProgramVO") ProgramVO searchVO,
                         @RequestParam(value = "prgId", required = true) int prgId,
                         HttpServletRequest request,
                         ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        ProgramVO vo = programService.selectProgram(searchVO);
        if(vo == null) {
            model.addAttribute("message", "program.nodata");
            return CmsManager.alert(CONTENT_PATH + "program_result.jsp", model);
        }
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        vo.setPrgDelId(loginVO.getId());
        vo.setPrgDelIp(request.getRemoteAddr());
        programService.deleteProgram(vo);
        
        model.addAttribute("message", "common.success.delete");
        return CmsManager.alert(CONTENT_PATH + "program_result.jsp", model);
    }
    
}