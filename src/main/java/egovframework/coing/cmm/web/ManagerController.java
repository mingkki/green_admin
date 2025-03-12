package egovframework.coing.cmm.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.service.ManagerService;
import egovframework.coing.cmm.service.UserService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.cmm.vo.ManagerVO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/manager.do")
public class ManagerController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/", CmsManager.getModulePath());
    private final EgovPropertyService propertiesService;
    private final ManagerService managerService;
    private final UserService userService;
    private final CommonService commonService;
    
    private void initParam(ManagerVO searchVO) {
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("searchCondition", searchVO.getSearchCondition());
        param.put("searchKeyword", searchVO.getSearchKeyword());
        param.put("pageIndex", searchVO.getPageIndex());
        
        searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
    }
    
    @RequestMapping()
    public String list(@ModelAttribute("searchManagerVO") ManagerVO searchVO,
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
        
        Map<String, Object> map = managerService.selectManagerList(searchVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));
        
        paginationInfo.setTotalRecordCount(totCnt);
        
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("paginationQueryString", paginationQueryString);
        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "manager_list.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.GET)
    public String form(@ModelAttribute("searchManagerVO") ManagerVO searchVO,
                       @ModelAttribute("writeManager") ManagerVO writeManagerVO,
                       @RequestParam(value = "mngId", required = false, defaultValue = "") String mngId,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        String command = ("".equals(mngId))
                ? "insert"
                : "update";
        
        if("update".equals(command)) {
            writeManagerVO = managerService.selectManager(searchVO);
            if(writeManagerVO == null) {
                model.addAttribute("message", "manager.nodata");
                return CmsManager.alert(CONTENT_PATH + "manager_result.jsp", model);
            }
            
            String[] mngEmailArr = EgovStringUtil.nullConvert(writeManagerVO.getMngEmail()).split("@");
            String mngEmail1 = (mngEmailArr != null && mngEmailArr.length > 0)
                    ? mngEmailArr[0]
                    : "";
            String mngEmail2 = (mngEmailArr != null && mngEmailArr.length > 1)
                    ? mngEmailArr[1]
                    : "";
            writeManagerVO.setMngEmail1(mngEmail1);
            writeManagerVO.setMngEmail2(mngEmail2);
            
            String[] mngTelArr = EgovStringUtil.nullConvert(writeManagerVO.getMngTel()).split("-");
            String mngTel1 = (mngTelArr != null && mngTelArr.length > 0)
                    ? mngTelArr[0]
                    : "";
            String mngTel2 = (mngTelArr != null && mngTelArr.length > 1)
                    ? mngTelArr[1]
                    : "";
            String mngTel3 = (mngTelArr != null && mngTelArr.length > 2)
                    ? mngTelArr[2]
                    : "";
            writeManagerVO.setMngTel1(mngTel1);
            writeManagerVO.setMngTel2(mngTel2);
            writeManagerVO.setMngTel3(mngTel3);
            
            String[] mngHpArr = EgovStringUtil.nullConvert(writeManagerVO.getMngHp()).split("-");
            String mngHp1 = (mngHpArr != null && mngHpArr.length > 0)
                    ? mngHpArr[0]
                    : "";
            String mngHp2 = (mngHpArr != null && mngHpArr.length > 1)
                    ? mngHpArr[1]
                    : "";
            String mngHp3 = (mngHpArr != null && mngHpArr.length > 2)
                    ? mngHpArr[2]
                    : "";
            writeManagerVO.setMngHp1(mngHp1);
            writeManagerVO.setMngHp2(mngHp2);
            writeManagerVO.setMngHp3(mngHp3);
        } else {
        }
        
        CodeDetailVO codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("TEL");
        List<CodeDetailVO> telList = commonService.selectCodeDetailList(codeDetailVO);
        
        codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("HP");
        List<CodeDetailVO> hpList = commonService.selectCodeDetailList(codeDetailVO);
        
        codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("EMAIL");
        List<CodeDetailVO> emailList = commonService.selectCodeDetailList(codeDetailVO);
        
        model.addAttribute("telList", telList);
        model.addAttribute("hpList", hpList);
        model.addAttribute("emailList", emailList);
        model.addAttribute("command", command);
        model.addAttribute("writeManager", writeManagerVO);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "manager_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.POST)
    public String write(@ModelAttribute("searchManagerVO") ManagerVO searchVO,
                        @ModelAttribute("writeManager") ManagerVO writeManagerVO,
                        @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                        HttpServletRequest request,
                        ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        initParam(searchVO);
        
        String mngEmail1 = EgovStringUtil.nullConvert(writeManagerVO.getMngEmail1());
        String mngEmail2 = EgovStringUtil.nullConvert(writeManagerVO.getMngEmail2());
        writeManagerVO.setMngEmail(EgovStringUtil.getEmail(mngEmail1, mngEmail2));
        
        String mngTel1 = EgovStringUtil.nullConvert(writeManagerVO.getMngTel1());
        String mngTel2 = EgovStringUtil.nullConvert(writeManagerVO.getMngTel2());
        String mngTel3 = EgovStringUtil.nullConvert(writeManagerVO.getMngTel3());
        writeManagerVO.setMngTel(EgovStringUtil.getTel(mngTel1, mngTel2, mngTel3));
        
        String mngHp1 = EgovStringUtil.nullConvert(writeManagerVO.getMngHp1());
        String mngHp2 = EgovStringUtil.nullConvert(writeManagerVO.getMngHp2());
        String mngHp3 = EgovStringUtil.nullConvert(writeManagerVO.getMngHp3());
        writeManagerVO.setMngHp(EgovStringUtil.getTel(mngHp1, mngHp2, mngHp3));
        
        String message = "";
        
        if("insert".equals(command)) {
            // ID 중복체크
            LoginVO tmpVO = new LoginVO();
            tmpVO.setId(writeManagerVO.getMngId());
            tmpVO = userService.selectUserMaster(tmpVO);
            if(tmpVO != null) {
                model.addAttribute("message", "manager.usedata");
                return CmsManager.alert(CONTENT_PATH + "manager_result.jsp", model);
            }
            message = "common.success.insert";
            writeManagerVO.setMngRegId(loginVO.getId());
            writeManagerVO.setMngRegIp(request.getRemoteAddr());
            managerService.insertManager(writeManagerVO);
        } else {
            ManagerVO vo = managerService.selectManager(searchVO);
            if(vo == null) {
                model.addAttribute("message", "manager.nodata");
                return CmsManager.alert(CONTENT_PATH + "manager_result.jsp", model);
            }
            message = "common.success.update";
            writeManagerVO.setMngUpdtId(loginVO.getId());
            writeManagerVO.setMngUpdtIp(request.getRemoteAddr());
            managerService.updateManager(writeManagerVO);
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "manager_result.jsp", model);
    }
    
    @RequestMapping(params = "act=delete", method = RequestMethod.POST)
    public String delete(@ModelAttribute("searchManagerVO") ManagerVO searchVO,
                         @RequestParam(value = "mngId", required = true) String mngId,
                         HttpServletRequest request,
                         ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        ManagerVO vo = managerService.selectManager(searchVO);
        if(vo == null) {
            model.addAttribute("message", "manager.nodata");
            return CmsManager.alert(CONTENT_PATH + "manager_result.jsp", model);
        }
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        vo.setMngDelId(loginVO.getId());
        vo.setMngDelIp(request.getRemoteAddr());
        managerService.deleteManager(vo);
        
        model.addAttribute("message", "common.success.delete");
        return CmsManager.alert(CONTENT_PATH + "manager_result.jsp", model);
    }
    
}