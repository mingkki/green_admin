package egovframework.coing.popup.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.Globals;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.util.EgovFileMngUtil;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.cmm.vo.FileVO;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.popup.service.PopupService;
import egovframework.coing.popup.vo.PopupVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/popup/{popType}.do")
public class PopupController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/popup/", CmsManager.getModulePath());
    private final EgovPropertyService propertiesService;
    private final PopupService popupService;
    private final CommonService commonService;
    private final EgovFileMngUtil egovFileMngUtil;
    
    /**
     * 프로그램내에서 사용할 파라미터를 초기화한다.
     */
    private void initParam(String popType, PopupVO searchVO) {
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("popCategory", searchVO.getPopCategory());
        param.put("popUseYn", searchVO.getPopUseYn());
        param.put("progress", searchVO.getProgress());
        param.put("searchCondition", searchVO.getSearchCondition());
        param.put("searchKeyword", searchVO.getSearchKeyword());
        param.put("pageIndex", searchVO.getPageIndex());
        
        searchVO.setPopType(popType.toUpperCase());
        searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
    }
    
    /**
     * 팝업 목록을 조회한다.
     */
    @RequestMapping()
    public String list(@PathVariable String popType,
                       @ModelAttribute("searchPopupVO") PopupVO searchVO,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(popType, searchVO);
        
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
        
        Map<String, Object> map = popupService.selectPopupList(searchVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));
        
        paginationInfo.setTotalRecordCount(totCnt);
        
        CodeDetailVO codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("POP" + popType.toUpperCase());
        List<CodeDetailVO> popCategoryList = commonService.selectCodeDetailList(codeDetailVO);
        
        model.addAttribute("popCategoryList", popCategoryList);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("paginationQueryString", paginationQueryString);
        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "popup_list.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    /**
     * 팝업 등록/수정 폼을 출력한다.
     */
    @RequestMapping(params = "act=write", method = RequestMethod.GET)
    public String form(@PathVariable String popType,
                       @ModelAttribute("searchPopupVO") PopupVO searchVO,
                       @ModelAttribute("writePopup") PopupVO writePopupVO,
                       @RequestParam(value = "popId", required = false, defaultValue = "0") int popId,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(popType, searchVO);
        
        String command = (popId < 1)
                ? "insert"
                : "update";
        if("update".equals(command)) {
            writePopupVO = popupService.selectPopup(searchVO);
            if(writePopupVO == null) {
                model.addAttribute("message", "popup.nodata");
                return CmsManager.alert(CONTENT_PATH + "popup_result.jsp", model);
            }
            
            String[] popStartDttmArr = EgovStringUtil.nullConvert(writePopupVO.getPopStartDttm()).split(" ");
            String popStartDt = (popStartDttmArr != null && popStartDttmArr.length > 0)
                    ? popStartDttmArr[0]
                    : "";
            String popStartTm = (popStartDttmArr != null && popStartDttmArr.length > 1)
                    ? popStartDttmArr[1]
                    : "";
            String[] popStartTmArr = EgovStringUtil.nullConvert(popStartTm).split(":");
            String popStartHH = (popStartTmArr != null && popStartTmArr.length > 0)
                    ? popStartTmArr[0]
                    : "00";
            String popStartMM = (popStartTmArr != null && popStartTmArr.length > 1)
                    ? popStartTmArr[1]
                    : "00";
            writePopupVO.setPopStartDttm(popStartDt);
            writePopupVO.setPopStartHH(popStartHH);
            writePopupVO.setPopStartMM(popStartMM);
            
            String[] popEndDttmArr = EgovStringUtil.nullConvert(writePopupVO.getPopEndDttm()).split(" ");
            String popEndDt = (popEndDttmArr != null && popEndDttmArr.length > 0)
                    ? popEndDttmArr[0]
                    : "";
            String popEndTm = (popEndDttmArr != null && popEndDttmArr.length > 1)
                    ? popEndDttmArr[1]
                    : "";
            String[] popEndTmArr = EgovStringUtil.nullConvert(popEndTm).split(":");
            String popEndHH = (popEndTmArr != null && popEndTmArr.length > 0)
                    ? popEndTmArr[0]
                    : "00";
            String popEndMM = (popEndTmArr != null && popEndTmArr.length > 1)
                    ? popEndTmArr[1]
                    : "00";
            writePopupVO.setPopEndDttm(popEndDt);
            writePopupVO.setPopEndHH(popEndHH);
            writePopupVO.setPopEndMM(popEndMM);
            
            String popShares = EgovStringUtil.nullConvert(writePopupVO.getPopShares());
            writePopupVO.setPopShareArr(popShares.split(","));
        } else {
            writePopupVO.setPopPeriodYn("N");
            writePopupVO.setPopLinkTarget("_BLANK");
            writePopupVO.setPopUseYn("Y");
            writePopupVO.setPopTopUnit("px");
            writePopupVO.setPopLeftUnit("px");
            writePopupVO.setPopWidthUnit("px");
            writePopupVO.setPopHeightUnit("px");
            writePopupVO.setPopScrollAt("no");
            writePopupVO.setPopCookieDay(1);
            
            String popCategory = EgovStringUtil.nullConvert(searchVO.getPopCategory());
            if(!"".equals(popCategory)) {
                int maxOrderNo = popupService.selectPopupMaxOrderNo(searchVO);
                writePopupVO.setPopOrderNo(maxOrderNo + 1);
            }
        }
        
        CodeDetailVO codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("POP" + popType.toUpperCase());
        List<CodeDetailVO> popCategoryList = commonService.selectCodeDetailList(codeDetailVO);
        
        ArrayList<String> hourList = new ArrayList<String>();
        for(int i = 0; i < 24; i++) {
            hourList.add(String.format("%02d", i));
        }
        
        ArrayList<String> minuteList = new ArrayList<String>();
        for(int i = 0; i < 60; i++) {
            minuteList.add(String.format("%02d", i));
        }
        
        model.addAttribute("command", command);
        model.addAttribute("writePopup", writePopupVO);
        model.addAttribute("popCategoryList", popCategoryList);
        model.addAttribute("hourList", hourList);
        model.addAttribute("minuteList", minuteList);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "popup_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    /**
     * 팝업를 등록/수정한다.
     */
    @RequestMapping(params = "act=write", method = RequestMethod.POST)
    public String write(@PathVariable String popType,
                        @ModelAttribute("searchPopupVO") PopupVO searchVO,
                        @ModelAttribute("writePopup") PopupVO writePopupVO,
                        @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                        HttpServletRequest request,
                        MultipartHttpServletRequest multiRequest,
                        ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        initParam(popType, searchVO);
        
        writePopupVO.setPopShares(EgovStringUtil.join(writePopupVO.getPopShareArr(), ","));
        writePopupVO.setPopLinkTarget(EgovStringUtil.nullConvert(writePopupVO.getPopLinkTarget(), "_SELF"));
        writePopupVO.setPopUseYn(EgovStringUtil.nullConvert(writePopupVO.getPopUseYn(), "Y"));
        writePopupVO.setPopTopUnit("px");
        writePopupVO.setPopLeftUnit("px");
        writePopupVO.setPopWidthUnit("px");
        writePopupVO.setPopHeightUnit("px");
        writePopupVO.setPopScrollAt("no");
        
        // 날짜제한하는 경우
        String popPeriodYn = EgovStringUtil.nullConvert(writePopupVO.getPopPeriodYn());
        if("Y".equals(popPeriodYn)) {
            String popStartDt = EgovStringUtil.nullConvert(writePopupVO.getPopStartDttm());
            String popStartHH = String.format("%02d", Integer.parseInt(EgovStringUtil.nullConvert(writePopupVO.getPopStartHH(), "0")));
            String popStartMM = String.format("%02d", Integer.parseInt(EgovStringUtil.nullConvert(writePopupVO.getPopStartMM(), "0")));
            String popEndDt = EgovStringUtil.nullConvert(writePopupVO.getPopEndDttm());
            String popEndHH = String.format("%02d", Integer.parseInt(EgovStringUtil.nullConvert(writePopupVO.getPopEndHH(), "0")));
            String popEndMM = String.format("%02d", Integer.parseInt(EgovStringUtil.nullConvert(writePopupVO.getPopEndMM(), "0")));
            writePopupVO.setPopStartDttm(popStartDt + " " + popStartHH + ":" + popStartMM);
            writePopupVO.setPopEndDttm(popEndDt + " " + popEndHH + ":" + popEndMM);
        } else {
            writePopupVO.setPopStartDttm("");
            writePopupVO.setPopEndDttm("");
        }
        
        // 팝업파일삭제를 체크한 경우 로고파일변수에 빈문자열을 넣는다.
        if(EgovStringUtil.nullConvert(writePopupVO.getDeleteFile()).equals("Y")) {
            writePopupVO.setPopFile("");
        }
        
        // 팝업파일 업로드
        FileVO uploadFileVO = uploadFile(multiRequest, popType);
        if(uploadFileVO != null) {
            writePopupVO.setPopFile("/popup/" + popType.toLowerCase() + "/" + uploadFileVO.getStreFileNm());
        }
        
        String message = "";
        
        if("insert".equals(command)) {
            message = "common.success.insert";
            writePopupVO.setPopRegId(loginVO.getId());
            writePopupVO.setPopRegIp(request.getRemoteAddr());
            popupService.insertPopup(writePopupVO);
        } else {
            // 팝업 원글 정보
            PopupVO origVO = popupService.selectPopup(searchVO);
            if(origVO == null) {
                model.addAttribute("message", "popup.nodata");
                return CmsManager.alert(CONTENT_PATH + "popup_result.jsp", model);
            }
            message = "common.success.update";
            writePopupVO.setPopUpdtId(loginVO.getId());
            writePopupVO.setPopUpdtIp(request.getRemoteAddr());
            popupService.updatePopup(writePopupVO);
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "popup_result.jsp", model);
    }
    
    /**
     * 팝업을 삭제한다.
     */
    @RequestMapping(params = "act=delete", method = RequestMethod.POST)
    public String delete(@PathVariable String popType,
                         @ModelAttribute("searchPopupVO") PopupVO searchVO,
                         @RequestParam(value = "popId", required = true) int popId,
                         HttpServletRequest request,
                         ModelMap model) throws Exception {
        
        initParam(popType, searchVO);
        
        PopupVO vo = popupService.selectPopup(searchVO);
        if(vo == null) {
            model.addAttribute("message", "popup.nodata");
            return CmsManager.alert(CONTENT_PATH + "popup_result.jsp", model);
        }
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        vo.setPopDelId(loginVO.getId());
        vo.setPopDelIp(request.getRemoteAddr());
        popupService.deletePopup(vo);
        
        model.addAttribute("message", "common.success.delete");
        return CmsManager.alert(CONTENT_PATH + "popup_result.jsp", model);
    }
    
    /**
     * 팝얼 파일을 업로드한다.
     */
    private FileVO uploadFile(MultipartHttpServletRequest multiRequest, String popType) throws Exception {
        
        String uploadPath = Globals.DISTRIBUTE_UPLOAD_PATH + "/popup/" + popType.toLowerCase() + "/";
        
        String atchFileId = "-1";
        List<MultipartFile> atchFile = multiRequest.getFiles("atchFile");
        
        List<FileVO> atchFileList = null;
        try {
            atchFileList = egovFileMngUtil.parseFileInf(atchFile, "", 0, atchFileId, uploadPath, "gif,jpg,png,bmp", 20);
        } catch(Exception e) {
            System.out.println(e);
        }
        
        return (atchFileList != null && atchFileList.size() > 0)
                ? atchFileList.get(0)
                : null;
    }
    
}