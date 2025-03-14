package egovframework.coing.member.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.service.UserService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.util.MapQuery;
import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.cmm.vo.GroupVO;
import egovframework.coing.cmm.vo.LevelVO;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.member.service.MemberGeneralService;
import egovframework.coing.member.vo.MemberGeneralVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/member/general/{memStatus}.do")
public class MemberGeneralController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/member/", CmsManager.getModulePath());
    private final EgovPropertyService propertiesService;
    private final MemberGeneralService memberGeneralService;
    private final CommonService commonService;
    private final UserService userService;

    private void initParam(MemberGeneralVO searchVO, String memStatus) {
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("memLevel", searchVO.getMemLevel());
        param.put("memGroups", searchVO.getMemGroups());
        param.put("memGender", searchVO.getMemGender());
        param.put("searchCondition", searchVO.getSearchCondition());
        param.put("searchKeyword", searchVO.getSearchKeyword());
        param.put("pageIndex", searchVO.getPageIndex());
        
        searchVO.setMemStatus(memStatus.toUpperCase());
        
        searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
    }
    
    @RequestMapping()
    public String list(@PathVariable String memStatus,
                       @ModelAttribute("searchMemberGeneralVO") MemberGeneralVO searchVO,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO, memStatus);
        
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
        
        Map<String, Object> map = memberGeneralService.selectMemberGeneralList(searchVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));
        
        paginationInfo.setTotalRecordCount(totCnt);
        
        List<GroupVO> groupList = commonService.selectGroupList();
        
        CodeDetailVO codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("USTATUS");
        List<CodeDetailVO> statusList = commonService.selectCodeDetailList(codeDetailVO);
        
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("paginationQueryString", paginationQueryString);
        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("groupList", groupList);
        model.addAttribute("statusList", statusList);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "general_list_" + memStatus.toLowerCase() + ".jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    
    @RequestMapping(params = "act=searchjson")
    @ResponseBody
    public Map<String, Object> listJson(@PathVariable String memStatus,
                                        @RequestParam(value = "searchKeyword", required = true) String searchKeyword) throws Exception {
        
        MemberGeneralVO searchVO = new MemberGeneralVO();
        searchVO.setMemStatus(memStatus.toUpperCase());
        searchVO.setSearchCondition("ID_NAME");
        searchVO.setSearchKeyword(searchKeyword);
        searchVO.setRecordCountPerPage(50);
        searchVO.setFirstIndex(0);
        
        List<MemberGeneralVO> result = memberGeneralService.getMepper().selectMemberGeneralList(searchVO);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", result);
        map.put("result", 1);
        return map;
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.GET)
    public String form(@PathVariable String memStatus,
                       @ModelAttribute("searchMemberGeneralVO") MemberGeneralVO searchVO,
                       @ModelAttribute("writeMemberGeneral") MemberGeneralVO writeMemberGeneralVO,
                       @RequestParam(value = "memId", required = false, defaultValue = "") String memId,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO, memStatus);
        
        String command = ("".equals(memId))
                ? "insert"
                : "update";
        
        if("update".equals(command)) {
            writeMemberGeneralVO = memberGeneralService.selectMemberGeneral(searchVO);
            if(writeMemberGeneralVO == null) {
                model.addAttribute("message", "member.general.nodata");
                return CmsManager.alert(CONTENT_PATH + "general_result.jsp", model);
            }
            
            String[] memEmailArr = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemEmail()).split("@");
            String memEmail1 = (memEmailArr != null && memEmailArr.length > 0)
                    ? memEmailArr[0]
                    : "";
            String memEmail2 = (memEmailArr != null && memEmailArr.length > 1)
                    ? memEmailArr[1]
                    : "";
            writeMemberGeneralVO.setMemEmail1(memEmail1);
            writeMemberGeneralVO.setMemEmail2(memEmail2);
            
            String[] memTelArr = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemTel()).split("-");
            String memTel1 = (memTelArr != null && memTelArr.length > 0)
                    ? memTelArr[0]
                    : "";
            String memTel2 = (memTelArr != null && memTelArr.length > 1)
                    ? memTelArr[1]
                    : "";
            String memTel3 = (memTelArr != null && memTelArr.length > 2)
                    ? memTelArr[2]
                    : "";
            writeMemberGeneralVO.setMemTel1(memTel1);
            writeMemberGeneralVO.setMemTel2(memTel2);
            writeMemberGeneralVO.setMemTel3(memTel3);
            
            String[] memHpArr = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemHp()).split("-");
            String memHp1 = (memHpArr != null && memHpArr.length > 0)
                    ? memHpArr[0]
                    : "";
            String memHp2 = (memHpArr != null && memHpArr.length > 1)
                    ? memHpArr[1]
                    : "";
            String memHp3 = (memHpArr != null && memHpArr.length > 2)
                    ? memHpArr[2]
                    : "";
            writeMemberGeneralVO.setMemHp1(memHp1);
            writeMemberGeneralVO.setMemHp2(memHp2);
            writeMemberGeneralVO.setMemHp3(memHp3);
            
            String[] memFaxArr = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemFax()).split("-");
            String memFax1 = (memFaxArr != null && memFaxArr.length > 0)
                    ? memFaxArr[0]
                    : "";
            String memFax2 = (memFaxArr != null && memFaxArr.length > 1)
                    ? memFaxArr[1]
                    : "";
            String memFax3 = (memFaxArr != null && memFaxArr.length > 2)
                    ? memFaxArr[2]
                    : "";
            writeMemberGeneralVO.setMemFax1(memFax1);
            writeMemberGeneralVO.setMemFax2(memFax2);
            writeMemberGeneralVO.setMemFax3(memFax3);
            
            String memGroups = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemGroups());
            writeMemberGeneralVO.setMemGroupArr(memGroups.split(","));
            
        } else {
            writeMemberGeneralVO.setMemIdentifyYn("N");
        }
        
        List<LevelVO> levelList = commonService.selectLevelList();
        List<GroupVO> groupList = commonService.selectGroupList("N");

        CodeDetailVO codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("USTATUS");
        List<CodeDetailVO> statusList = commonService.selectCodeDetailList(codeDetailVO);
        
        codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("UGUBUN");
        List<CodeDetailVO> gubunList = commonService.selectCodeDetailList(codeDetailVO);
        
        codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("TEL");
        List<CodeDetailVO> telList = commonService.selectCodeDetailList(codeDetailVO);
        
        codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("HP");
        List<CodeDetailVO> hpList = commonService.selectCodeDetailList(codeDetailVO);
        
        codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("EMAIL");
        List<CodeDetailVO> emailList = commonService.selectCodeDetailList(codeDetailVO);
        
        codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("FAX");
        List<CodeDetailVO> faxList = commonService.selectCodeDetailList(codeDetailVO);

        codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("BANK");
        List<CodeDetailVO> bankList = commonService.selectCodeDetailList(codeDetailVO);

        codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("EMD");
        List<CodeDetailVO> emdList = commonService.selectCodeDetailList(codeDetailVO);
        
        model.addAttribute("command", command);
        model.addAttribute("writeMemberGeneral", writeMemberGeneralVO);
        model.addAttribute("bankList", bankList);
        model.addAttribute("emdList", emdList);
        model.addAttribute("levelList", levelList);
        model.addAttribute("groupList", groupList);
        model.addAttribute("gubunList", gubunList);
        model.addAttribute("statusList", statusList);
        model.addAttribute("telList", telList);
        model.addAttribute("hpList", hpList);
        model.addAttribute("emailList", emailList);
        model.addAttribute("faxList", faxList);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "general_write.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    
    @RequestMapping(params = "act=write", method = RequestMethod.POST)
    public String write(@PathVariable String memStatus,
                        @ModelAttribute("searchMemberGeneralVO") MemberGeneralVO searchVO,
                        @ModelAttribute("writeMemberGeneral") MemberGeneralVO writeMemberGeneralVO,
                        @RequestParam(value = "command", required = true, defaultValue = "insert") String command,
                        @RequestParam Map<String, Object> paramMap,
                        HttpServletRequest request,
                        ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        initParam(searchVO, memStatus);
        
        writeMemberGeneralVO.setMemEmailYn(EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemEmailYn(), "N"));
        writeMemberGeneralVO.setMemHpYn(EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemHpYn(), "N"));
        writeMemberGeneralVO.setMemIdentifyYn(EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemIdentifyYn(), "N"));
        writeMemberGeneralVO.setMemGender(EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemGender(), "N"));
        writeMemberGeneralVO.setMemBirthGb(EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemBirthGb(), "N"));
        
        String memEmail1 = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemEmail1());
        String memEmail2 = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemEmail2());
        writeMemberGeneralVO.setMemEmail(EgovStringUtil.getEmail(memEmail1, memEmail2));
        
        String memTel1 = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemTel1());
        String memTel2 = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemTel2());
        String memTel3 = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemTel3());
        writeMemberGeneralVO.setMemTel(EgovStringUtil.getTel(memTel1, memTel2, memTel3));
        
        String memHp1 = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemHp1());
        String memHp2 = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemHp2());
        String memHp3 = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemHp3());
        writeMemberGeneralVO.setMemHp(EgovStringUtil.getTel(memHp1, memHp2, memHp3));
        
        String memFax1 = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemFax1());
        String memFax2 = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemFax2());
        String memFax3 = EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemFax3());
        writeMemberGeneralVO.setMemFax(EgovStringUtil.getTel(memFax1, memFax2, memFax3));
        
        writeMemberGeneralVO.setMemGroups(EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemGroups(), "GNR"));

        writeMemberGeneralVO.setMemBank(EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemBank()));
        writeMemberGeneralVO.setMemBankAccountNumber(EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemBankAccountNumber()));
        writeMemberGeneralVO.setMemHp(EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemHp()));
        writeMemberGeneralVO.setMemComAddress1(EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemComAddress1()));
        writeMemberGeneralVO.setMemComAddress2(EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemComAddress2()));

        if("".equals(writeMemberGeneralVO.getMemExchangeId())){
            writeMemberGeneralVO.setMemExchangeId("0");
        }else{
            writeMemberGeneralVO.setMemExchangeId(EgovStringUtil.nullConvert(writeMemberGeneralVO.getMemExchangeId()));
        }

        String message = "";
        
        //기관회원 일반회원 레벨 값 하드코딩.
        //수정할것
        if("MCO".equals(writeMemberGeneralVO.getMemGroups())) {
            writeMemberGeneralVO.setMemLevel(2);
        } else {
            writeMemberGeneralVO.setMemLevel(1);
        }
        if("insert".equals(command)) {
            // ID 중복체크
            LoginVO tmpVO = new LoginVO();
            tmpVO.setId(writeMemberGeneralVO.getMemId());
            tmpVO = userService.selectUserMaster(tmpVO);
            if(tmpVO != null) {
                model.addAttribute("message", "member.general.usedata");
                return CmsManager.alert(CONTENT_PATH + "general_result.jsp", model);
            }
            message = "common.success.insert";
            writeMemberGeneralVO.setMemRegId(loginVO.getId());
            writeMemberGeneralVO.setMemRegIp(request.getRemoteAddr());
            memberGeneralService.insertMemberGeneral(writeMemberGeneralVO);
        } else {
            MemberGeneralVO vo = memberGeneralService.selectMemberGeneral(searchVO);
            if(vo == null) {
                model.addAttribute("message", "member.general.nodata");
                return CmsManager.alert(CONTENT_PATH + "general_result.jsp", model);
            }
            message = "common.success.update";
            writeMemberGeneralVO.setMemHomepage(vo.getMemHomepage());
            writeMemberGeneralVO.setMemLevel(vo.getMemLevel());
            writeMemberGeneralVO.setMemGroups(vo.getMemGroups());
            writeMemberGeneralVO.setMemFax(vo.getMemFax());
            writeMemberGeneralVO.setMemComZipcd(vo.getMemComZipcd());
            writeMemberGeneralVO.setMemComName(vo.getMemComName());
            writeMemberGeneralVO.setMemSosok(vo.getMemSosok());
            writeMemberGeneralVO.setMemCeoName(vo.getMemCeoName());
            writeMemberGeneralVO.setMemUpdtId(loginVO.getId());
            writeMemberGeneralVO.setMemUpdtIp(request.getRemoteAddr());
            memberGeneralService.updateMemberGeneral(writeMemberGeneralVO);
        }
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "general_result.jsp", model);
    }
    
    @RequestMapping(params = "act=delete", method = RequestMethod.POST)
    public String delete(@PathVariable String memStatus,
                         @ModelAttribute("searchMemberGeneralVO") MemberGeneralVO searchVO,
                         @RequestParam(value = "memId", required = true) String memId,
                         HttpServletRequest request,
                         ModelMap model) throws Exception {
        
        initParam(searchVO, memStatus);
        
        MemberGeneralVO vo = memberGeneralService.selectMemberGeneral(searchVO);
        if(vo == null) {
            model.addAttribute("message", "member.general.nodata");
            return CmsManager.alert(CONTENT_PATH + "general_result.jsp", model);
        }
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        vo.setMemDelId(loginVO.getId());
        vo.setMemDelIp(request.getRemoteAddr());
        memberGeneralService.deleteMemberGeneral(vo);
        
        model.addAttribute("message", "common.success.delete");
        return CmsManager.alert(CONTENT_PATH + "general_result.jsp", model);
    }
    
    @RequestMapping(params = "act=updateLevel", method = RequestMethod.POST)
    public String updateLevel(@PathVariable String memStatus,
                              @ModelAttribute("searchMemberGeneralVO") MemberGeneralVO searchVO,
                              @RequestParam(value = "chks", required = true) String[] chks,
                              @RequestParam(value = "changeLevel", required = true, defaultValue = "0") int changeLevel,
                              HttpServletRequest request,
                              ModelMap model) throws Exception {
        
        initParam(searchVO, memStatus);
        
        searchVO.setMemIdArr(chks);
        searchVO.setMemLevel(changeLevel);
        memberGeneralService.updateMemberGeneralCheckedLevel(searchVO);
        
        model.addAttribute("message", "common.success.process");
        return CmsManager.alert(CONTENT_PATH + "general_result.jsp", model);
    }
    
    @RequestMapping(params = "act=updateStatus", method = RequestMethod.POST)
    public String updateStatus(@PathVariable String memStatus,
                               @ModelAttribute("searchMemberGeneralVO") MemberGeneralVO searchVO,
                               @RequestParam(value = "chks", required = true) String[] chks,
                               @RequestParam(value = "changeStatus", required = true) String changeStatus,
                               HttpServletRequest request,
                               ModelMap model) throws Exception {
        
        initParam(searchVO, memStatus);
        
        searchVO.setMemIdArr(chks);
        searchVO.setMemStatus(changeStatus);
        memberGeneralService.updateMemberGeneralCheckedStatus(searchVO);
        
        model.addAttribute("message", "common.success.process");
        return CmsManager.alert(CONTENT_PATH + "general_result.jsp", model);
    }
    
    //제공인력 중복확인 체크
    @RequestMapping(params = "act=checkComRegNum", method = RequestMethod.POST)
    public @ResponseBody boolean checkComRegNum(@RequestParam(value = "memComRegNum", required = true) String memComRegNum,
                                                @RequestParam(value = "memGroups", required = true) String memGroups) throws Exception {
        boolean isChecked = true;
        MemberGeneralVO searchVO = new MemberGeneralVO();
        searchVO.setMemComRegNum(memComRegNum);
        searchVO.setMemGroups(memGroups);
        int cnt = memberGeneralService.selectCompanyRegistNumberCnt(searchVO);
        if(cnt > 0) {
            isChecked = false;
        }
        return isChecked;
    }
    
}