package egovframework.coing.board.web;

import egovframework.coing.board.service.BoardCategoryService;
import egovframework.coing.board.service.BoardInfoService;
import egovframework.coing.board.vo.BoardCategoryVO;
import egovframework.coing.board.vo.BoardInfoVO;
import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/board/info.do")
public class BoardCategoryController {
    
    private final BoardInfoService boardInfoService;
    private final BoardCategoryService boardCategoryService;
    private final CommonService commonService;
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/board/", CmsManager.getModulePath());
    
    /**
     * 프로그램내에서 사용할 파라미터를 초기화한다.
     * @throws Exception
     */
    private void initParam(BoardInfoVO searchBoardInfoVO) throws Exception {
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("brdUseYn", searchBoardInfoVO.getBrdUseYn());
        param.put("searchCondition", searchBoardInfoVO.getSearchCondition());
        param.put("searchKeyword", searchBoardInfoVO.getSearchKeyword());
        param.put("pageIndex", searchBoardInfoVO.getPageIndex());
        
        searchBoardInfoVO.setQueryString(MapQuery.urlEncodeUTF8(param));
    }
    
    /**
     * 게시판 정보 목록을 조회한다.
     */
    @RequestMapping(params = "act=category", method = RequestMethod.GET)
    public String categoryForm(@ModelAttribute("searchBoardInfoVO") BoardInfoVO searchBoardInfoVO,
                               @ModelAttribute("searchBoardCategoryVO") BoardCategoryVO searchBoardCategoryVO,
                               @ModelAttribute("writeBoardCategory") BoardCategoryVO writeBoardCategoryVO,
                               @RequestParam(value = "brdId", required = true) String brdId,
                               @RequestParam(value = "bcaId", required = false, defaultValue = "0") int bcaId,
                               @RequestParam(value = "mode", required = false, defaultValue = "") String mode,
                               ModelMap model) throws Exception {
        
        initParam(searchBoardInfoVO);
        
        BoardInfoVO boardInfoVO = boardInfoService.selectBoardInfo(searchBoardInfoVO);
        if(boardInfoVO == null) {
            model.addAttribute("message", "board.info.nodata");
            return CmsManager.alert(CONTENT_PATH + "category_result.jsp", model);
        }
        
        String command = (bcaId < 1)
                ? "insert"
                : "update";
        if("insert".equals(command)) {
            searchBoardCategoryVO.setBcaType("C");
            writeBoardCategoryVO.setBcaUseYn("Y");
            
            int maxOrderNo = boardCategoryService.selectBoardCategoryMaxOrderNo(searchBoardCategoryVO);
            writeBoardCategoryVO.setBcaOrderNo(maxOrderNo + 1);
        } else {
            searchBoardCategoryVO.setBcaType("C");
            writeBoardCategoryVO = boardCategoryService.selectBoardCategory(searchBoardCategoryVO);
            if(writeBoardCategoryVO == null) {
                model.addAttribute("message", "board.category.nodata");
                return CmsManager.alert(CONTENT_PATH + "category_result.jsp", model);
            }
        }
        
        List<BoardCategoryVO> boardCategoryList = boardCategoryService.selectBoardCategoryList(searchBoardCategoryVO);
        
        model.addAttribute("mode", mode);
        model.addAttribute("boardInfoVO", boardInfoVO);
        model.addAttribute("boardCategoryList", boardCategoryList);
        model.addAttribute("command", command);
        model.addAttribute("writeBoardCategory", writeBoardCategoryVO);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "category_form.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    
    @RequestMapping(params = "act=category", method = RequestMethod.POST)
    public String categorySubmit(@ModelAttribute("searchBoardInfoVO") BoardInfoVO searchBoardInfoVO,
                                 @ModelAttribute("searchBoardCategoryVO") BoardCategoryVO searchBoardCategoryVO,
                                 @ModelAttribute("writeBoardCategory") BoardCategoryVO writeBoardCategoryVO,
                                 @RequestParam(value = "brdId", required = true) String brdId,
                                 @RequestParam(value = "bcaId", required = false, defaultValue = "0") int bcaId,
                                 @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                                 HttpServletRequest request,
                                 ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        initParam(searchBoardInfoVO);
        
        String message = "";
        
        if("insert".equals(command)) {
            message = "common.success.insert";
            writeBoardCategoryVO.setBcaType("C");
            writeBoardCategoryVO.setBcaRegId(loginVO.getId());
            writeBoardCategoryVO.setBcaRegIp(request.getRemoteAddr());
            boardCategoryService.insertBoardCategory(writeBoardCategoryVO);
        } else {
            searchBoardCategoryVO.setBcaType("C");
            BoardCategoryVO origVO = boardCategoryService.selectBoardCategory(searchBoardCategoryVO);
            if(origVO == null) {
                model.addAttribute("message", "board.category.nodata");
                return CmsManager.alert(CONTENT_PATH + "category_result.jsp", model);
            }
            message = "common.success.update";
            writeBoardCategoryVO.setBcaType("C");
            writeBoardCategoryVO.setBcaUpdtId(loginVO.getId());
            writeBoardCategoryVO.setBcaUpdtIp(request.getRemoteAddr());
            boardCategoryService.updateBoardCategory(writeBoardCategoryVO);
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "category_result.jsp", model);
    }
    
    @RequestMapping(params = "act=cdelete", method = RequestMethod.POST)
    public String cdelete(@ModelAttribute("searchBoardInfoVO") BoardInfoVO searchBoardInfoVO,
                          @ModelAttribute("searchBoardCategoryVO") BoardCategoryVO searchBoardCategoryVO,
                          @RequestParam(value = "brdId", required = true) String brdId,
                          @RequestParam(value = "bcaId", required = true, defaultValue = "0") int bcaId,
                          HttpServletRequest request,
                          ModelMap model) throws Exception {
        
        initParam(searchBoardInfoVO);
        
        searchBoardCategoryVO.setBcaType("C");
        BoardCategoryVO boardCategoryVO = boardCategoryService.selectBoardCategory(searchBoardCategoryVO);
        if(boardCategoryVO == null) {
            model.addAttribute("message", "board.category.nodata");
            return CmsManager.alert(CONTENT_PATH + "category_result.jsp", model);
        }
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        boardCategoryVO.setBcaDelId(loginVO.getId());
        boardCategoryVO.setBcaDelIp(request.getRemoteAddr());
        boardCategoryService.deleteBoardCategory(boardCategoryVO);
        
        model.addAttribute("message", "common.success.delete");
        return CmsManager.alert(CONTENT_PATH + "category_result.jsp", model);
    }
    
}