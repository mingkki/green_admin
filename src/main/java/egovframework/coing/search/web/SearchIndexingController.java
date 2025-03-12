package egovframework.coing.search.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/search/indexing.do")
public class SearchIndexingController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/search/", CmsManager.getModulePath());
    private final SearchService searchService;
    
    @RequestMapping()
    public String index(ModelMap model) throws Exception {
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "indexing.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=indexing", method = RequestMethod.POST)
    public String indexing(@RequestParam(value = "gubun", required = false, defaultValue = "") String gubun, ModelMap model) throws Exception {
        
        searchService.createIndexAll();
        
        model.addAttribute("message", "common.success.process");
        return CmsManager.alert(CONTENT_PATH + "indexing_result.jsp", model);
    }
    
}