package egovframework.coing.point.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.point.service.PointLogService;
import egovframework.coing.point.vo.PointLogVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/point/log.do")
public class PointLogController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/point/", CmsManager.getModulePath());
    private final PointLogService pointLogService;
    private final CommonService commonService;
    
    @ModelAttribute
    public void initParam(@ModelAttribute("searchVO") PointLogVO searchVO, ModelMap model) {
        Map<String, Object> param = new HashMap<>();
        param.put("searchCondition", searchVO.getSearchCondition());
        param.put("searchKeyword", searchVO.getSearchKeyword());
        param.put("pageIndex", searchVO.getPageIndex());
        
        searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
        
        model.addAttribute("searchVO", searchVO);
    }
    
    @RequestMapping()
    public String list(@ModelAttribute("searchVO") PointLogVO searchVO, ModelMap model) throws Exception {
        
        List<PointLogVO> resultList = pointLogService.select(searchVO);
        
        int resultCnt = pointLogService.count(searchVO);
        
        model.addAttribute("resultCnt", resultCnt);
        model.addAttribute("resultList", resultList);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "/log_list.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    
    @GetMapping(params = "act=write")
    public String write(@ModelAttribute PointLogVO writeVO, ModelMap model) throws Exception {
        writeVO = pointLogService.selectOne(writeVO);
        String command = "update";
        
        if(writeVO == null) {
            writeVO = new PointLogVO();
            command = "insert";
        }
        
        CodeDetailVO codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("EMD");
        List<CodeDetailVO> emdList = commonService.selectCodeDetailList(codeDetailVO);
        
        model.addAttribute("emdList", emdList);
        model.addAttribute("command", command);
        model.addAttribute("writeVO", writeVO);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "/log_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @PostMapping(params = "act=write")
    public String writePost(@ModelAttribute PointLogVO writeVO, @RequestParam Map<String, Object> paramMap, ModelMap model) throws Exception {
        String message = "";
        
        if(writeVO.getId() == null) {
            pointLogService.insert(writeVO);
            message = "common.success.insert";
        } else {
            pointLogService.update(writeVO);
            message = "common.success.update";
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "log_result.jsp", model);
    }
    
    @PostMapping(params = "act=delete")
    public String delete(@ModelAttribute PointLogVO writeVO, ModelMap model) throws Exception {
        pointLogService.delete(writeVO);
        
        String message = "common.success.delete";
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "log_result.jsp", model);
    }
    
}