package egovframework.coing.resource.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.resource.service.ResourceService;
import egovframework.coing.resource.vo.ResourceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/resource.do")
public class ResourceController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/resource/", CmsManager.getModulePath());
    private final ResourceService resourceService;

    @ModelAttribute
    public void initParam(@ModelAttribute("searchVO") ResourceVO searchVO, ModelMap model) {
        Map<String, Object> param = new HashMap<>();
        param.put("searchCondition", searchVO.getSearchCondition());
        param.put("searchKeyword", searchVO.getSearchKeyword());
        param.put("pageIndex", searchVO.getPageIndex());

        searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));

        model.addAttribute("searchVO", searchVO);
    }

    @GetMapping()
    public String list(@ModelAttribute("searchVO") ResourceVO searchVO, @RequestParam Map<String, Object> paramMap, ModelMap model) throws Exception {

        List<ResourceVO> resultList = resourceService.select(searchVO);
        int resultCnt = resourceService.count(searchVO);

        System.out.println("resourceVOList ::: " + resourceService.select(searchVO));

        model.addAttribute("resultCnt", resultCnt);
        model.addAttribute("resultList", resultList);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "/list.jsp");
        return "egovframework/coing/common/admin_view";
    }

    @GetMapping(params = "act=write")
    public String write(@ModelAttribute ResourceVO writeVO, ModelMap model) throws Exception {
        writeVO = resourceService.selectOne(writeVO);

        String command = writeVO == null
                ? "insert"
                : "update";

        model.addAttribute("command", command);
        model.addAttribute("writeVO", writeVO);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "/write.jsp");
        return "egovframework/coing/common/admin_view";
    }

    @PostMapping(params = "act=write")
    public String writePost(@ModelAttribute ResourceVO writeVO, @RequestParam Map<String, Object> paramMap, ModelMap model) throws Exception {
        String message = "";

        if(writeVO.getId() == null) {
            resourceService.insert(writeVO);
            message = "common.success.insert";
        } else {
            resourceService.update(writeVO);
            message = "common.success.update";
        }

        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "result.jsp", model);
    }

    @PostMapping(params = "act=delete")
    public String delete(@ModelAttribute ResourceVO writeVO, ModelMap model) throws Exception {
        resourceService.delete(writeVO);

        String message = "common.success.delete";
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "result.jsp", model);
    }

}