package egovframework.coing.cmm.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.LevelService;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.vo.LevelVO;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/level.do")
public class LevelController {
    
    private final EgovPropertyService propertiesService;
    private final LevelService levelService;
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/", CmsManager.getModulePath());
    
    private void initParam(LevelVO searchVO) {
    }
    
    @RequestMapping()
    public String list(@ModelAttribute("searchLevelVO") LevelVO searchVO,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        List<LevelVO> resultList = levelService.selectLevelList(searchVO);
        
        model.addAttribute("resultList", resultList);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "level_list.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.GET)
    public String form(@ModelAttribute("searchLevelVO") LevelVO searchVO,
                       @ModelAttribute("writeLevel") LevelVO writeLevelVO,
                       @RequestParam(value = "lvlId", required = false, defaultValue = "0") int lvlId,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        String command = (lvlId < 1)
                ? "insert"
                : "update";
        
        if("update".equals(command)) {
            writeLevelVO = levelService.selectLevel(searchVO);
            if(writeLevelVO == null) {
                model.addAttribute("message", "level.nodata");
                return CmsManager.alert(CONTENT_PATH + "level_result.jsp", model);
            }
        } else {
        }
        
        model.addAttribute("command", command);
        model.addAttribute("writeLevel", writeLevelVO);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "level_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.POST)
    public String write(@ModelAttribute("searchLevelVO") LevelVO searchVO,
                        @ModelAttribute("writeLevel") LevelVO writeLevelVO,
                        @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                        @RequestParam Map<String, Object> paramMap,
                        HttpServletRequest request,
                        ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        String message = "";
        
        if("insert".equals(command)) {
            LevelVO vo = new LevelVO();
            vo.setLvlId(writeLevelVO.getLvlId());
            vo = levelService.selectLevel(vo);
            if(vo != null) {
                model.addAttribute("message", "level.useId");
                return CmsManager.alert(CONTENT_PATH + "level_result.jsp", model);
            }
            
            message = "common.success.insert";
            writeLevelVO.setLvlRegId(loginVO.getId());
            writeLevelVO.setLvlRegIp(request.getRemoteAddr());
            levelService.insertLevel(writeLevelVO);
        } else {
            LevelVO vo = levelService.selectLevel(searchVO);
            if(vo == null) {
                model.addAttribute("message", "level.nodata");
                return CmsManager.alert(CONTENT_PATH + "level_result.jsp", model);
            }
            message = "common.success.update";
            writeLevelVO.setLvlUpdtId(loginVO.getId());
            writeLevelVO.setLvlUpdtIp(request.getRemoteAddr());
            levelService.updateLevel(writeLevelVO);
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "level_result.jsp", model);
    }
    
    @RequestMapping(params = "act=delete", method = RequestMethod.POST)
    public String delete(@ModelAttribute("searchLevelVO") LevelVO searchVO,
                         @RequestParam(value = "lvlId", required = true) int lvlId,
                         HttpServletRequest request,
                         ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        LevelVO vo = levelService.selectLevel(searchVO);
        if(vo == null) {
            model.addAttribute("message", "level.nodata");
            return CmsManager.alert(CONTENT_PATH + "level_result.jsp", model);
        }
        
        levelService.deleteLevel(vo);
        
        model.addAttribute("message", "common.success.delete");
        return CmsManager.alert(CONTENT_PATH + "level_result.jsp", model);
    }
    
}