package egovframework.coing.cmm.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.CodeService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.CodeVO;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/code.do")
public class CodeController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/", CmsManager.getModulePath());
    private final EgovPropertyService propertiesService;
    private final CodeService codeService;
    
    private void initParam(CodeVO searchVO) {
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("codUseYn", searchVO.getCodUseYn());
        param.put("searchCondition", searchVO.getSearchCondition());
        param.put("searchKeyword", searchVO.getSearchKeyword());
        param.put("pageIndex", searchVO.getPageIndex());
        
        searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
    }
    
    @RequestMapping()
    public String list(@ModelAttribute("searchCodeVO") CodeVO searchVO, @RequestParam Map<String, Object> paramMap, ModelMap model) throws Exception {
        
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
        
        Map<String, Object> map = codeService.selectCodeList(searchVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));
        
        paginationInfo.setTotalRecordCount(totCnt);
        
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("paginationQueryString", paginationQueryString);
        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "code_list.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.GET)
    public String form(@ModelAttribute("searchCodeVO") CodeVO searchVO,
                       @ModelAttribute("writeCode") CodeVO writeCodeVO,
                       @RequestParam(value = "codId", required = false, defaultValue = "") String codId,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        String command = ("".equals(codId))
                ? "insert"
                : "update";
        
        if("update".equals(command)) {
            writeCodeVO = codeService.selectCode(searchVO);
            if(writeCodeVO == null) {
                model.addAttribute("message", "code.nodata");
                return CmsManager.alert(CONTENT_PATH + "code_result.jsp", model);
            }
        } else {
            writeCodeVO.setCodUseYn("Y");
        }
        
        model.addAttribute("command", command);
        model.addAttribute("writeCode", writeCodeVO);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "code_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.POST)
    public String write(@ModelAttribute("searchCodeVO") CodeVO searchVO,
                        @ModelAttribute("writeCode") CodeVO writeCodeVO,
                        @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                        HttpServletRequest request,
                        ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        initParam(searchVO);
        
        writeCodeVO.setCodUseYn(EgovStringUtil.nullConvert(writeCodeVO.getCodUseYn(), "N"));
        
        String message = "";
        
        if("insert".equals(command)) {
            // 코드ID 중복체크
            int cnt = codeService.checkCodeId(writeCodeVO);
            if(cnt > 0) {
                model.addAttribute("message", "code.usedata");
                return CmsManager.alert(CONTENT_PATH + "code_result.jsp", model);
            }
            message = "common.success.insert";
            writeCodeVO.setCodRegId(loginVO.getId());
            writeCodeVO.setCodRegIp(request.getRemoteAddr());
            codeService.insertCode(writeCodeVO);
        } else {
            // 코드 원글 정보
            CodeVO origVO = codeService.selectCode(searchVO);
            if(origVO == null) {
                model.addAttribute("message", "code.nodata");
                return CmsManager.alert(CONTENT_PATH + "code_result.jsp", model);
            }
            message = "common.success.update";
            writeCodeVO.setCodUpdtId(loginVO.getId());
            writeCodeVO.setCodUpdtIp(request.getRemoteAddr());
            codeService.updateCode(writeCodeVO);
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "code_result.jsp", model);
    }
    
    @RequestMapping(params = "act=delete", method = RequestMethod.POST)
    public String delete(@ModelAttribute("searchCodeVO") CodeVO searchVO,
                         @RequestParam(value = "codId", required = true) String codId,
                         HttpServletRequest request,
                         ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        initParam(searchVO);
        
        CodeVO vo = codeService.selectCode(searchVO);
        if(vo == null) {
            model.addAttribute("message", "code.nodata");
            return CmsManager.alert(CONTENT_PATH + "code_result.jsp", model);
        }
        
        vo.setCodDelId(loginVO.getId());
        vo.setCodDelIp(request.getRemoteAddr());
        codeService.deleteCode(vo);
        
        model.addAttribute("message", "common.success.delete");
        return CmsManager.alert(CONTENT_PATH + "code_result.jsp", model);
    }
    
    @RequestMapping(params = "act=check")
    public @ResponseBody boolean check(CodeVO searchVO) throws Exception {
        
        int cnt = codeService.checkCodeId(searchVO);
        if(cnt > 0) {
            return false;
        }
        
        return true;
    }
    
}