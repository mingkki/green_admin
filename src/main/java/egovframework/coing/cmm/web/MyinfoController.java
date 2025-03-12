package egovframework.coing.cmm.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.service.ManagerService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.cmm.vo.ManagerVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyinfoController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/", CmsManager.getModulePath());
    private final ManagerService managerService;
    private final CommonService commonService;
    
    @RequestMapping(value = "/myinfo.do", method = RequestMethod.GET)
    public String myinfoForm(@ModelAttribute("writeManager") ManagerVO writeManagerVO, ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        writeManagerVO.setMngId(loginVO.getId());
        writeManagerVO = managerService.selectManager(writeManagerVO);
        if(writeManagerVO == null) {
            model.addAttribute("message", "manager.nodata");
            return CmsManager.alert(CONTENT_PATH + "myinfo_form_result.jsp", model);
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
        model.addAttribute("writeManager", writeManagerVO);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "myinfo_form.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=write", method = RequestMethod.POST)
    public String write(@ModelAttribute("writeManager") ManagerVO writeManagerVO, HttpServletRequest request, ModelMap model) throws Exception {
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
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
        
        ManagerVO vo = new ManagerVO();
        vo.setMngId(loginVO.getId());
        vo = managerService.selectManager(vo);
        if(vo == null) {
            model.addAttribute("message", "manager.nodata");
            return CmsManager.alert(CONTENT_PATH + "myinfo_form.jsp", model);
        }
        message = "common.success.update";
        writeManagerVO.setMngId(loginVO.getId());
        writeManagerVO.setMngUpdtId(loginVO.getId());
        writeManagerVO.setMngUpdtIp(request.getRemoteAddr());
        managerService.updateManagerMyinfo(writeManagerVO);
        
        model.addAttribute("message", message);
        return CmsManager.alert(CONTENT_PATH + "myinfo_form_result.jsp", model);
    }
    
}