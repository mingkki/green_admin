package egovframework.coing.cmm.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.Globals;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.service.ImageService;
import egovframework.coing.cmm.util.EgovFileMngUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.FileVO;
import egovframework.coing.cmm.vo.ImageFileVO;
import egovframework.coing.cmm.vo.ImageInfoVO;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/image/info.do")
public class ImageController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/", CmsManager.getModulePath());
    private final EgovPropertyService propertiesService;
    private final ImageService imageService;
    private final CommonService commonService;
    private final EgovFileMngUtil egovFileMngUtil;
    
    /**
     * 프로그램내에서 사용할 파라미터를 초기화한다.
     */
    private void initParam(ImageInfoVO searchVO) {
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("imgUseYn", searchVO.getImgUseYn());
        param.put("searchCondition", searchVO.getSearchCondition());
        param.put("searchKeyword", searchVO.getSearchKeyword());
        param.put("pageIndex", searchVO.getPageIndex());
        
        searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
    }
    
    /**
     * 프로그램 정보 목록을 조회한다.
     */
    @RequestMapping()
    public String list(@ModelAttribute("searchImageInfoVO") ImageInfoVO searchVO,
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
        
        Map<String, Object> map = imageService.selectImageInfoList(searchVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));
        
        paginationInfo.setTotalRecordCount(totCnt);
        
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("paginationQueryString", paginationQueryString);
        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "image_list.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    /**
     * 프로그램 정보 등록/수정 폼을 출력한다.
     */
    @RequestMapping(params = "act=write", method = RequestMethod.GET)
    public String form(@ModelAttribute("searchImageInfoVO") ImageInfoVO searchVO,
                       @ModelAttribute("writeImageInfo") ImageInfoVO writeImageInfoVO,
                       @RequestParam(value = "imgId", required = false, defaultValue = "0") int imgId,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        String command = (imgId < 1)
                ? "insert"
                : "update";
        
        if("update".equals(command)) {
            writeImageInfoVO = imageService.selectImageInfo(searchVO);
            if(writeImageInfoVO == null) {
                model.addAttribute("message", "image.info.nodata");
                return CmsManager.alert(CONTENT_PATH + "image_result.jsp", model);
            }
        } else {
            writeImageInfoVO.setImgUseYn("Y");
        }
        
        // 첨부파일 리스트
        List<ImageFileVO> imageFileList = null;
        if("update".equals(command)) {
            ImageFileVO imageInfoFileVO = new ImageFileVO();
            imageInfoFileVO.setImgId(imgId);
            imageFileList = imageService.selectImageFileList(imageInfoFileVO);
        }
        
        model.addAttribute("command", command);
        model.addAttribute("writeImageInfo", writeImageInfoVO);
        model.addAttribute("imageFileList", imageFileList);
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "image_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    /**
     * 프로그램 정보를 등록/수정한다.
     */
    @RequestMapping(params = "act=write", method = RequestMethod.POST)
    public String write(@ModelAttribute("searchImageInfoVO") ImageInfoVO searchVO,
                        @ModelAttribute("writeImageInfo") ImageInfoVO writeImageInfoVO,
                        @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                        HttpServletRequest request,
                        MultipartHttpServletRequest multiRequest,
                        ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        initParam(searchVO);
        
        // 첨부파일
        List<FileVO> uploadFileList = uploadFile(model, multiRequest);
        
        String message = "";
        
        if("insert".equals(command)) {
            message = "common.success.insert";
            writeImageInfoVO.setImgRegId(loginVO.getId());
            writeImageInfoVO.setImgRegIp(request.getRemoteAddr());
            imageService.insertImageInfo(writeImageInfoVO, uploadFileList);
        } else {
            // 원글 정보
            ImageInfoVO origVO = imageService.selectImageInfo(searchVO);
            if(origVO == null) {
                model.addAttribute("message", "image.info.nodata");
                return CmsManager.alert(CONTENT_PATH + "image_result.jsp", model);
            }
            message = "common.success.update";
            writeImageInfoVO.setImgUpdtId(loginVO.getId());
            writeImageInfoVO.setImgUpdtIp(request.getRemoteAddr());
            imageService.updateImageInfo(writeImageInfoVO, uploadFileList);
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "image_result.jsp", model);
    }
    
    @RequestMapping(params = "act=download")
    public String download(@ModelAttribute("searchImageInfoVO") ImageInfoVO searchVO,
                           @RequestParam(value = "imgId", required = true) int imgId,
                           @RequestParam(value = "imfNo", required = true) int imfNo,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        ImageInfoVO vo = imageService.selectImageInfo(searchVO);
        if(vo == null) {
            model.addAttribute("message", "image.info.nodata");
            return CmsManager.alert(CONTENT_PATH + "image_result.jsp", model);
        }
        
        ImageFileVO fileVO = new ImageFileVO();
        fileVO.setImgId(imgId);
        fileVO.setImfNo(imfNo + "");
        fileVO = imageService.selectImageFile(fileVO);
        if(fileVO == null) {
            model.addAttribute("message", "common.fail.file.nodata");
            return CmsManager.alert(CONTENT_PATH + "image_result.jsp", model);
        }
        
        File uFile = null;
        try {
            uFile = new File(Globals.DISTRIBUTE_UPLOAD_PATH + fileVO.getImfName());
        } catch(Exception ex) {
            model.addAttribute("message", "common.fail.file.notfound");
            return CmsManager.alert(CONTENT_PATH + "image_result.jsp", model);
        }
        
        model.addAttribute("imageInfoFileVO", fileVO);
        model.addAttribute("imageInfoFile", uFile);
        return "egovframework/coing/image/image_filedown";
    }
    
    /**
     * 프로그램 정보를 삭제한다.
     */
    @RequestMapping(params = "act=delete", method = RequestMethod.POST)
    public String delete(@ModelAttribute("searchImageInfoVO") ImageInfoVO searchVO,
                         @RequestParam(value = "imgId", required = true) int imgId,
                         HttpServletRequest request,
                         ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        ImageInfoVO vo = imageService.selectImageInfo(searchVO);
        if(vo == null) {
            model.addAttribute("message", "image.info.nodata");
            return CmsManager.alert(CONTENT_PATH + "image_result.jsp", model);
        }
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        vo.setImgDelId(loginVO.getId());
        vo.setImgDelIp(request.getRemoteAddr());
        imageService.deleteImageInfo(vo);
        
        model.addAttribute("message", "common.success.delete");
        return CmsManager.alert(CONTENT_PATH + "image_result.jsp", model);
    }
    
    private List<FileVO> uploadFile(ModelMap model, MultipartHttpServletRequest multiRequest) throws Exception {
        
        String uploadPath = Globals.DISTRIBUTE_UPLOAD_PATH + "/image/";
        String atchFileId = "-1";
        List<MultipartFile> atchFile = multiRequest.getFiles("uploadFile");
        
        List<FileVO> atchFileList = null;
        try {
            atchFileList = egovFileMngUtil.parseFileInf(atchFile, "", 0, atchFileId, uploadPath, "gif,jpg,png,bmp,ppt,pptx,doc,docx,xls,xlsx,hwp,txt", 20);
            if(atchFileList != null) {
                String fileExt = "";
                for(int i = 0; i < atchFileList.size(); i++) {
                    fileExt = (atchFileList.get(i).getFileExtsn()).toLowerCase();
                    if("jpg".equals(fileExt) || "jpeg".equals(fileExt) || "gif".equals(fileExt) || "png".equals(fileExt) || "bmp".equals(fileExt)) {
                        try {
                            File thumbnailImage = new File(uploadPath + "thumb." + atchFileList.get(i).getStreFileNm());
                            Thumbnails.of(uploadPath + atchFileList.get(i).getStreFileNm()).size(400, 400).toFile(thumbnailImage);
                            //String thumbfile = thumbnailImage.getAbsolutePath().replace('\\', '/').replace(Globals.ROOT_PATH, "");
                            atchFileList.get(i).setThumbFileNm("thumb." + atchFileList.get(i).getStreFileNm());
                            
                            thumbnailImage = new File(uploadPath + "thumb2." + atchFileList.get(i).getStreFileNm());
                            Thumbnails.of(uploadPath + atchFileList.get(i).getStreFileNm()).size(1000, 1000).toFile(thumbnailImage);
                            //String thumbfile = thumbnailImage.getAbsolutePath().replace('\\', '/').replace(Globals.ROOT_PATH, "");
                            atchFileList.get(i).setThumb2FileNm("thumb2." + atchFileList.get(i).getStreFileNm());
                        } catch(Exception e) {
                            System.out.println("board thumb1 error -------->" + e.getMessage());
                        }
                    }
                }
            }
        } catch(Exception e) {
            model.addAttribute("message", e.getMessage());
            throw new ModelAndViewDefiningException(CmsManager.alertMV(CONTENT_PATH + "image_result.jsp", model));
        }
        
        return atchFileList;
    }
    
}