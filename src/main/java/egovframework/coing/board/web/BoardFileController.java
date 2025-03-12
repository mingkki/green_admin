package egovframework.coing.board.web;

import egovframework.coing.board.service.BoardFileService;
import egovframework.coing.board.vo.BoardFileVO;
import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.Globals;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/board/file.do")
public class BoardFileController {
    
    private final EgovPropertyService propertiesService;
    private final BoardFileService boardFileService;
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/board/", CmsManager.getModulePath());
    
    /**
     * 프로그램내에서 사용할 파라미터를 초기화한다.
     */
    private void initParam(BoardFileVO searchVO) {
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("searchCondition", searchVO.getSearchCondition());
        param.put("searchKeyword", searchVO.getSearchKeyword());
        param.put("pageIndex", searchVO.getPageIndex());
        
        searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
    }
    
    /**
     * 게시판 정보 목록을 조회한다.
     */
    @RequestMapping()
    public String list(@ModelAttribute("searchBoardFileVO") BoardFileVO searchVO,
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
        
        Map<String, Object> map = boardFileService.selectBoardFileList(searchVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));
        
        paginationInfo.setTotalRecordCount(totCnt);
        
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("paginationQueryString", paginationQueryString);
        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "file_list.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=download")
    public String download(BoardFileVO searchBoardFileVO,
                           @RequestParam(value = "bfiId", required = true) int bfiId,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           ModelMap model) throws Exception {
        
        BoardFileVO fileVO = boardFileService.selectBoardFile(searchBoardFileVO);
        if(fileVO == null) {
            model.addAttribute("message", "board.file.nodata");
            return CmsManager.alert(CONTENT_PATH + "file_result.jsp", model);
        }
        
        File uFile = null;
        try {
            uFile = new File(Globals.DISTRIBUTE_PATH + fileVO.getBfiName());
        } catch(Exception ex) {
            model.addAttribute("message", "board.file.notfound");
            return CmsManager.alert(CONTENT_PATH + "file_result.jsp", model);
        }
        
        model.addAttribute("boardFileVO", fileVO);
        model.addAttribute("boardFile", uFile);
        
        return "egovframework/coing/board/file_down";
    }
    
}