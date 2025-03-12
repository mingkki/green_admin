package egovframework.coing.board.web;

import egovframework.coing.board.service.BoardInfoService;
import egovframework.coing.board.vo.BoardInfoVO;
import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.Globals;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.GroupVO;
import egovframework.coing.cmm.vo.LevelVO;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/board/info.do")
public class BoardInfoController {
    
    private final EgovPropertyService propertiesService;
    private final BoardInfoService boardInfoService;
    private final CommonService commonService;
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/board/", CmsManager.getModulePath());
    
    /**
     * 프로그램내에서 사용할 파라미터를 초기화한다.
     */
    private void initParam(BoardInfoVO searchVO) {
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("brdUseYn", searchVO.getBrdUseYn());
        param.put("searchCondition", searchVO.getSearchCondition());
        param.put("searchKeyword", searchVO.getSearchKeyword());
        param.put("pageIndex", searchVO.getPageIndex());
        
        searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
    }
    
    /**
     * 게시판 정보 목록을 조회한다.
     */
    @RequestMapping()
    public String list(@ModelAttribute("searchBoardInfoVO") BoardInfoVO searchVO,
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
        
        Map<String, Object> map = boardInfoService.selectBoardInfoList(searchVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));
        
        paginationInfo.setTotalRecordCount(totCnt);
        
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("paginationQueryString", paginationQueryString);
        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "info_list.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    /**
     * 게시판 정보 등록/수정 폼을 출력한다.
     */
    @RequestMapping(params = "act=write", method = RequestMethod.GET)
    public String form(@ModelAttribute("searchBoardInfoVO") BoardInfoVO searchVO,
                       @ModelAttribute("writeBoardInfo") BoardInfoVO writeBoardInfoVO,
                       @RequestParam(value = "brdId", required = false, defaultValue = "") String brdId,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        String command = ("".equals(brdId))
                ? "insert"
                : "update";
        
        if("update".equals(command)) {
            writeBoardInfoVO = boardInfoService.selectBoardInfo(searchVO);
            if(writeBoardInfoVO == null) {
                model.addAttribute("message", "board.info.nodata");
                return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
            }
            
            String brdLimitRead = EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdLimitRead());
            writeBoardInfoVO.setBrdLimitReadArr(brdLimitRead.split(","));
            String brdLimitWrite = EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdLimitWrite());
            writeBoardInfoVO.setBrdLimitWriteArr(brdLimitWrite.split(","));
            String brdLimitReply = EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdLimitReply());
            writeBoardInfoVO.setBrdLimitReplyArr(brdLimitReply.split(","));
            String brdLimitComment = EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdLimitComment());
            writeBoardInfoVO.setBrdLimitCommentArr(brdLimitComment.split(","));
            String brdLimitUpload = EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdLimitUpload());
            writeBoardInfoVO.setBrdLimitUploadArr(brdLimitUpload.split(","));
            String brdLimitDownload = EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdLimitDownload());
            writeBoardInfoVO.setBrdLimitDownloadArr(brdLimitDownload.split(","));
            String brdLimitNotice = EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdLimitNotice());
            writeBoardInfoVO.setBrdLimitNoticeArr(brdLimitNotice.split(","));
            String brdLimitEditor = EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdLimitEditor());
            writeBoardInfoVO.setBrdLimitEditorArr(brdLimitEditor.split(","));
        } else {
            writeBoardInfoVO.setBrdUseYn("Y");
            writeBoardInfoVO.setBrdNewPeriod(1);
            writeBoardInfoVO.setBrdPerPage(15);
            writeBoardInfoVO.setBrdWriteBtnYn("N");
            writeBoardInfoVO.setBrdNoticeYn("Y");
            writeBoardInfoVO.setBrdEditorYn("Y");
            writeBoardInfoVO.setBrdReplyYn("N");
            writeBoardInfoVO.setBrdCommentYn("N");
            writeBoardInfoVO.setBrdCommentReplyYn("N");
            writeBoardInfoVO.setBrdPeriodYn("N");
            writeBoardInfoVO.setBrdCategoryYn("N");
            writeBoardInfoVO.setBrdProgressYn("N");
            writeBoardInfoVO.setBrdSecretAt("N");
            writeBoardInfoVO.setBrdViewDelYn("N");
            writeBoardInfoVO.setBrdRestoreYn("N");
            writeBoardInfoVO.setBrdMyselfYn("N");
            writeBoardInfoVO.setBrdRssYn("N");
            writeBoardInfoVO.setBrdHidenameYn("N");
            writeBoardInfoVO.setBrdUploadYn("N");
            writeBoardInfoVO.setBrdUploadCnt(5);
            writeBoardInfoVO.setBrdUploadSize(20);
            writeBoardInfoVO.setBrdUploadExt("zip,rar,hwp,xls,xlsx,doc,docx,ppt,pptx,pdf,jpg,gif,png");
            writeBoardInfoVO.setBrdIpGubun("N");
            writeBoardInfoVO.setBrdIdGubun("N");
            writeBoardInfoVO.setBrdLimitWordYn("Y");
        }
        
        List<LevelVO> levelList = commonService.selectLevelList();
        List<GroupVO> groupList = commonService.selectGroupList();
        
        // 스킨 목록
        String skinPath = Globals.DISTRIBUTE_PATH + "/WEB-INF/jsp/egovframework/coing/modules/board/skin/";
        skinPath = skinPath.replace('\\', File.separatorChar).replace('/', File.separatorChar);
        File skinPathFile = new File(skinPath);
        File[] skinFileList = skinPathFile.listFiles();
        LinkedHashMap<String, String> skinList = new LinkedHashMap<String, String>();
        if(skinFileList != null) {
            String name = "";
            File nameFile = null;
            BufferedReader br;
            for(int i = 0; i < skinFileList.length; i++) {
                if(skinFileList[i].isDirectory()) {
                    name = skinFileList[i].getAbsolutePath() + "/name.txt";
                    nameFile = new File(name);
                    if(nameFile.exists()) {
                        br = new BufferedReader(new InputStreamReader(new FileInputStream(name), "UTF-8"));
                        skinList.put(skinFileList[i].getName(), br.readLine());
                    } else {
                        skinList.put(skinFileList[i].getName(), skinFileList[i].getName());
                    }
                }
            }
        }
        
        model.addAttribute("levelList", levelList);
        model.addAttribute("groupList", groupList);
        model.addAttribute("skinList", skinList);
        model.addAttribute("command", command);
        model.addAttribute("writeBoardInfo", writeBoardInfoVO);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "info_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    /**
     * 게시판 정보를 등록/수정한다.
     */
    @RequestMapping(params = "act=write", method = RequestMethod.POST)
    public String write(@ModelAttribute("searchBoardInfoVO") BoardInfoVO searchVO,
                        @ModelAttribute("writeBoardInfo") BoardInfoVO writeBoardInfoVO,
                        @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                        @RequestParam Map<String, Object> paramMap,
                        HttpServletRequest request,
                        ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        writeBoardInfoVO.setBrdLimitRead(EgovStringUtil.join(writeBoardInfoVO.getBrdLimitReadArr(), ","));
        writeBoardInfoVO.setBrdRealnameReadYn(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdRealnameReadYn(), "N"));
        writeBoardInfoVO.setBrdLimitWrite(EgovStringUtil.join(writeBoardInfoVO.getBrdLimitWriteArr(), ","));
        writeBoardInfoVO.setBrdRealnameWriteYn(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdRealnameWriteYn(), "N"));
        writeBoardInfoVO.setBrdLimitReply(EgovStringUtil.join(writeBoardInfoVO.getBrdLimitReplyArr(), ","));
        writeBoardInfoVO.setBrdRealnameReplyYn(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdRealnameReplyYn(), "N"));
        writeBoardInfoVO.setBrdLimitComment(EgovStringUtil.join(writeBoardInfoVO.getBrdLimitCommentArr(), ","));
        writeBoardInfoVO.setBrdRealnameCommentYn(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdRealnameCommentYn(), "N"));
        writeBoardInfoVO.setBrdLimitUpload(EgovStringUtil.join(writeBoardInfoVO.getBrdLimitUploadArr(), ","));
        writeBoardInfoVO.setBrdRealnameUploadYn(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdRealnameUploadYn(), "N"));
        writeBoardInfoVO.setBrdLimitDownload(EgovStringUtil.join(writeBoardInfoVO.getBrdLimitDownloadArr(), ","));
        writeBoardInfoVO.setBrdRealnameDownloadYn(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdRealnameDownloadYn(), "N"));
        writeBoardInfoVO.setBrdLimitNotice(EgovStringUtil.join(writeBoardInfoVO.getBrdLimitNoticeArr(), ","));
        writeBoardInfoVO.setBrdLimitEditor(EgovStringUtil.join(writeBoardInfoVO.getBrdLimitEditorArr(), ","));
        writeBoardInfoVO.setBrdRealnameEditorYn(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdRealnameDownloadYn(), "N"));
        writeBoardInfoVO.setBrdWriteBtnYn(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdWriteBtnYn(), "N"));
        writeBoardInfoVO.setBrdReplyYn(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdReplyYn(), "N"));
        writeBoardInfoVO.setBrdCommentYn(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdCommentYn(), "N"));
        writeBoardInfoVO.setBrdCommentReplyYn(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdCommentReplyYn(), "N"));
        writeBoardInfoVO.setBrdSecretAt(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdSecretAt(), "N"));
        writeBoardInfoVO.setBrdMyselfYn(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdMyselfYn(), "N"));
        writeBoardInfoVO.setBrdRssYn(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdRssYn(), "N"));
        writeBoardInfoVO.setBrdHidenameYn(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdHidenameYn(), "N"));
        writeBoardInfoVO.setBrdIdGubun(EgovStringUtil.nullConvert(writeBoardInfoVO.getBrdIdGubun(), "N"));

        String message = "";

        if("insert".equals(command)) {
            message = "common.success.insert";
            writeBoardInfoVO.setBrdRegId(loginVO.getId());
            writeBoardInfoVO.setBrdRegIp(request.getRemoteAddr());
            boardInfoService.insertBoardInfo(writeBoardInfoVO);
        } else {
            // 게시판정보 원글 정보
            BoardInfoVO origVO = boardInfoService.selectBoardInfo(searchVO);
            if(origVO == null) {
                model.addAttribute("message", "board.info.nodata");
                return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
            }
            message = "common.success.update";
            writeBoardInfoVO.setBrdUpdtId(loginVO.getId());
            writeBoardInfoVO.setBrdUpdtIp(request.getRemoteAddr());
            boardInfoService.updateBoardInfo(writeBoardInfoVO);
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
    }
    
    /**
     * 게시판 정보를 삭제한다.
     */
    @RequestMapping(params = "act=delete", method = RequestMethod.POST)
    public String delete(@ModelAttribute("searchBoardInfoVO") BoardInfoVO searchVO,
                         @RequestParam(value = "brdId", required = true) String brdId,
                         HttpServletRequest request,
                         ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        BoardInfoVO vo = boardInfoService.selectBoardInfo(searchVO);
        if(vo == null) {
            model.addAttribute("message", "board.info.nodata");
            return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
        }
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        vo.setBrdDelId(loginVO.getId());
        vo.setBrdDelIp(request.getRemoteAddr());
        boardInfoService.deleteBoardInfo(vo);
        
        model.addAttribute("message", "common.success.delete");
        return CmsManager.alert(CONTENT_PATH + "info_result.jsp", model);
    }
    
}