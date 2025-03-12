package egovframework.coing.point.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.point.service.PointExchangeService;
import egovframework.coing.point.vo.PointExchangeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/point/exchange.do")
public class PointExchangeController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/point/", CmsManager.getModulePath());
    private final PointExchangeService pointExchangeService;
    private final CommonService commonService;
    
    @ModelAttribute
    public void initParam(@ModelAttribute("searchVO") PointExchangeVO searchVO, ModelMap model) {
        Map<String, Object> param = new HashMap<>();
        param.put("searchCondition", searchVO.getSearchCondition());
        param.put("searchKeyword", searchVO.getSearchKeyword());
        param.put("pageIndex", searchVO.getPageIndex());
        
        searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
        
        model.addAttribute("searchVO", searchVO);
    }
    
    @RequestMapping()
    public String list(@ModelAttribute("searchVO") PointExchangeVO searchVO, ModelMap model) throws Exception {
        
        List<PointExchangeVO> resultList = pointExchangeService.select(searchVO);
        
        int resultCnt = pointExchangeService.count(searchVO);
        
        model.addAttribute("resultCnt", resultCnt);
        model.addAttribute("resultList", resultList);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "/exchange_list.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    
    @GetMapping(params = "act=write")
    public String write(@ModelAttribute PointExchangeVO writeVO, ModelMap model) throws Exception {
        writeVO = pointExchangeService.selectOne(writeVO);
        String command = "update";
        
        if(writeVO == null) {
            writeVO = new PointExchangeVO();
            command = "insert";
        }
        
        CodeDetailVO codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("EMD");
        List<CodeDetailVO> emdList = commonService.selectCodeDetailList(codeDetailVO);
        
        model.addAttribute("emdList", emdList);
        model.addAttribute("command", command);
        model.addAttribute("writeVO", writeVO);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "/exchange_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @PostMapping(params = "act=write")
    public String writePost(@ModelAttribute PointExchangeVO writeVO, @RequestParam Map<String, Object> paramMap, ModelMap model) throws Exception {
        String message = "";
        
        if(writeVO.getId() == null) {
            pointExchangeService.insert(writeVO);
            message = "common.success.insert";
        } else {
            PointExchangeVO beforePointExchange = pointExchangeService.selectOne(writeVO);
            
            if("WAIT".equals(beforePointExchange.getStatus())) {
                pointExchangeService.updateStatusConfirm(beforePointExchange.getId());
            }
            
            pointExchangeService.update(writeVO);
            message = "common.success.update";
        }
        
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "/result.jsp", model);
    }
    
}