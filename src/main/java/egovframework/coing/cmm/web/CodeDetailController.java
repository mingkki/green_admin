package egovframework.coing.cmm.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.CodeService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.CodeDetailVO;
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
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/code/detail.do")
public class CodeDetailController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/", CmsManager.getModulePath());
    private final EgovPropertyService propertiesService;
    private final CodeService codeService;
    
    private void initParam(CodeDetailVO searchVO) {
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("searchCodId", searchVO.getSearchCodId());
        param.put("cddUseYn", searchVO.getCddUseYn());
        param.put("searchCondition", searchVO.getSearchCondition());
        param.put("searchKeyword", searchVO.getSearchKeyword());
        param.put("pageIndex", searchVO.getPageIndex());
        
        searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
    }
    
    @RequestMapping()
    public String list(@ModelAttribute("searchCodeDetailVO") CodeDetailVO searchVO,
                       @RequestParam Map<String, Object> paramMap,
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
        
        Map<String, Object> map = codeService.selectCodeDetailList(searchVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));
        
        paginationInfo.setTotalRecordCount(totCnt);
        
        List<CodeVO> codeListAll = codeService.selectCodeListAll(null);
        
        model.addAttribute("codeListAll", codeListAll);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("paginationQueryString", paginationQueryString);
        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "code_detail_list.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.GET)
    public String form(@ModelAttribute("searchCodeDetailVO") CodeDetailVO searchVO,
                       @ModelAttribute("writeCodeDetail") CodeDetailVO writeCodeDetailVO,
                       @RequestParam(value = "codId", required = false, defaultValue = "") String codId,
                       @RequestParam(value = "cddId", required = false, defaultValue = "") String cddId,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        String command = ("".equals(cddId))
                ? "insert"
                : "update";
        
        if("update".equals(command)) {
            writeCodeDetailVO = codeService.selectCodeDetail(searchVO);
            if(writeCodeDetailVO == null) {
                model.addAttribute("message", "code.detail.nodata");
                return CmsManager.alert(CONTENT_PATH + "code_detail_result.jsp", model);
            }
        } else {
            String searchCodId = EgovStringUtil.nullConvert(searchVO.getSearchCodId());
            if(!"".equals(searchCodId)) {
                writeCodeDetailVO.setCodId(searchCodId);
                
                int maxOrderNo = codeService.selectCodeDetailMaxOrderNo(searchVO);
                writeCodeDetailVO.setCddOrderNo(maxOrderNo + 1);
            }
            writeCodeDetailVO.setCddUseYn("Y");
        }
        
        List<CodeVO> codeListAll = codeService.selectCodeListAll(null);
        
        model.addAttribute("command", command);
        model.addAttribute("codeListAll", codeListAll);
        model.addAttribute("writeCodeDetail", writeCodeDetailVO);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "code_detail_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.POST)
    public String write(@ModelAttribute("searchCodeDetailVO") CodeDetailVO searchVO,
                        @ModelAttribute("writeCodeDetail") CodeDetailVO writeCodeDetailVO,
                        @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                        HttpServletRequest request,
                        ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        initParam(searchVO);
        
        writeCodeDetailVO.setCddUseYn(EgovStringUtil.nullConvert(writeCodeDetailVO.getCddUseYn(), "N"));
        
        String message = "";
        
        if("insert".equals(command)) {
            // 상세코드ID 중복체크
            int cnt = codeService.checkCodeDetailId(writeCodeDetailVO);
            if(cnt > 0) {
                model.addAttribute("message", "code.detail.usedata");
                return CmsManager.alert(CONTENT_PATH + "code_detail_result.jsp", model);
            }
            message = "common.success.insert";
            writeCodeDetailVO.setCddRegId(loginVO.getId());
            writeCodeDetailVO.setCddRegIp(request.getRemoteAddr());
            codeService.insertCodeDetail(writeCodeDetailVO);
        } else {
            // 코드 원글 정보
            CodeDetailVO origVO = codeService.selectCodeDetail(searchVO);
            if(origVO == null) {
                model.addAttribute("message", "code.detail.nodata");
                return CmsManager.alert(CONTENT_PATH + "code_detail_result.jsp", model);
            }
            message = "common.success.update";
            writeCodeDetailVO.setCddUpdtId(loginVO.getId());
            writeCodeDetailVO.setCddUpdtIp(request.getRemoteAddr());
            codeService.updateCodeDetail(writeCodeDetailVO);
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "code_detail_result.jsp", model);
    }
    
    @RequestMapping(params = "act=delete", method = RequestMethod.POST)
    public String delete(@ModelAttribute("searchCodeDetailVO") CodeDetailVO searchVO,
                         @RequestParam(value = "codId", required = true) String codId,
                         @RequestParam(value = "cddId", required = true) String cddId,
                         HttpServletRequest request,
                         ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        initParam(searchVO);
        
        CodeDetailVO vo = codeService.selectCodeDetail(searchVO);
        if(vo == null) {
            model.addAttribute("message", "code.detail.nodata");
            return CmsManager.alert(CONTENT_PATH + "code_detail_result.jsp", model);
        }
        
        vo.setCddDelId(loginVO.getId());
        vo.setCddDelIp(request.getRemoteAddr());
        codeService.deleteCodeDetail(vo);
        
        model.addAttribute("message", "common.success.delete");
        return CmsManager.alert(CONTENT_PATH + "code_detail_result.jsp", model);
    }
    
    @RequestMapping(params = "act=check")
    public @ResponseBody boolean check(CodeDetailVO searchVO) throws Exception {
        
        int cnt = codeService.checkCodeDetailId(searchVO);
        if(cnt > 0) {
            return false;
        }
        
        return true;
    }
    
}