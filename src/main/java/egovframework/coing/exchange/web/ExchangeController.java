package egovframework.coing.exchange.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.exchange.service.ExchangeService;
import egovframework.coing.exchange.vo.ExchangeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/exchange.do")
public class ExchangeController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/exchange/", CmsManager.getModulePath());
    private final ExchangeService exchangeService;
    private final CommonService commonService;
    
    @ModelAttribute
    public void initParam(@ModelAttribute("searchVO") ExchangeVO searchVO, ModelMap model) {
        Map<String, Object> param = new HashMap<>();
        param.put("searchCondition", searchVO.getSearchCondition());
        param.put("searchKeyword", searchVO.getSearchKeyword());
        param.put("pageIndex", searchVO.getPageIndex());
        
        searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
        
        model.addAttribute("searchVO", searchVO);
    }
    
    @RequestMapping()
    public String list(@ModelAttribute("searchVO") ExchangeVO searchVO, ModelMap model) throws Exception {
        
        List<ExchangeVO> resultList = exchangeService.select(searchVO);
        int resultCnt = exchangeService.count(searchVO);
        
        model.addAttribute("resultCnt", resultCnt);
        model.addAttribute("resultList", resultList);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "/list.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    
    @GetMapping(params = "act=write")
    public String write(@ModelAttribute ExchangeVO writeVO, ModelMap model) throws Exception {
        writeVO = exchangeService.selectOne(writeVO);
        String command = "update";
        
        if(writeVO == null) {
            writeVO = new ExchangeVO();
            command = "insert";
        }
        
        CodeDetailVO codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("EMD");
        List<CodeDetailVO> emdList = commonService.selectCodeDetailList(codeDetailVO);
        
        model.addAttribute("emdList", emdList);
        model.addAttribute("command", command);
        model.addAttribute("writeVO", writeVO);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "/write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @PostMapping(params = "act=write")
    public String writePost(@ModelAttribute ExchangeVO writeVO, @RequestParam Map<String, Object> paramMap, ModelMap model) throws Exception {
        String message = "";
        
        if(writeVO.getId() == null) {
            exchangeService.insert(writeVO);
            message = "common.success.insert";
        } else {
            exchangeService.update(writeVO);
            message = "common.success.update";
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "result.jsp", model);
    }
    
    @PostMapping(params = "act=delete")
    public String delete(@ModelAttribute ExchangeVO writeVO, ModelMap model) throws Exception {
        exchangeService.delete(writeVO);
        
        String message = "common.success.delete";
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "result.jsp", model);
    }
    
}