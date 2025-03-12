package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.service.UserService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.cmm.vo.UserLoginLogVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import eu.bitwalker.useragentutils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@Service("userService")
public class UserServiceImpl extends EgovAbstractServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    private final CommonService commonService;
    
    @Override
    public LoginVO actionLogin(LoginVO vo) throws Exception {
        
        // 1. 입력한 비밀번호를 암호화한다.
        String enpassword = commonService.encryptPassword(vo.getPassword(), vo.getId());
        vo.setPassword(enpassword);
        
        String successYn = "N";
        String message = "";
        
        // 2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
        LoginVO loginVO = userMapper.actionLogin(vo);
        if(loginVO != null && !"".equals(EgovStringUtil.nullConvert(loginVO.getId()))) {
            
            // 비밀번호가 일치하지 않는 경우
            if(!enpassword.equals(loginVO.getPassword())) {
                // 로그인 실패 횟수가 5회 이상이고
                // 마지막 로그인 실패 시간부터 30분이 지나지 않은 경우 로그인이 불가능하다.
                long time1 = 0;
                long time2 = System.currentTimeMillis();
                try {
                    Date loginFailDttm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(loginVO.getLoginfailDttm());
                    time1 = loginFailDttm.getTime() + (1000 * 60) * 30;
                } catch(Exception e) {
                    time1 = System.currentTimeMillis();
                }
                
                if(loginVO.getLoginfailCnt() >= 5 && time1 >= time2) {
                    loginVO.setStatus("X2");
                    message = "비밀번호 5회 이상 틀려서 30분 잠김";
                } else {
                    // 마지막 로그인 실패 시간부터 30분이 지나면 로그인실패기록을 리셋한다.
                    if(time2 >= time1) {
                        userMapper.updateLoginFailReset(loginVO);
                    }
                    
                    loginVO.setIp(vo.getIp());
                    userMapper.updateLoginFail(loginVO);
                    
                    loginVO.setStatus("X1");
                    loginVO.setLoginfailCnt(loginVO.getLoginfailCnt() + 1);
                    message = "비밀번호 일치하지 않음";
                }
                // 가입대기
            } else if("A".equals(loginVO.getStatus())) {
                message = "가입대기회원";
                // 잠김계정
            } else if("L".equals(loginVO.getStatus())) {
                message = "계정잠김";
                // 휴면
            } else if("H".equals(loginVO.getStatus())) {
                message = "휴면회원";
                // 로그인 성공
            } else {
                loginVO.setIp(vo.getIp());
                userMapper.updateLoginInfo(loginVO);
                message = "";
                successYn = "Y";
            }
            
        } else {
            loginVO = new LoginVO();
            message = "일치하는 회원정보 없음";
        }
        
        // https://www.bitwalker.eu/software/user-agent-utils
        UserAgent userAgent = UserAgent.parseUserAgentString(vo.getUseragent());
        Browser browser = userAgent.getBrowser();
        BrowserType browserType = browser.getBrowserType();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        DeviceType deviceType = operatingSystem.getDeviceType();
        
        UserLoginLogVO log = new UserLoginLogVO();
        log.setUllSuccessYn(successYn);
        log.setUllUserId(vo.getId());
        log.setUllIp(vo.getIp());
        log.setUllMessage(message);
        log.setUllUseragent(vo.getUseragent());
        log.setUllReferer(vo.getReferrer());
        log.setUllBrowser(browser.getName());
        log.setUllBrowserType(browserType.getName());
        log.setUllOs(operatingSystem.getName());
        log.setUllDeviceType(deviceType.getName());
        userMapper.insertLoginLog(log);
        
        return loginVO;
    }
    
    
    @Override
    public LoginVO selectUserMaster(LoginVO vo) throws Exception {
        
        return userMapper.selectUserMaster(vo);
    }
    
    @Override
    public Map<String, Object> selectLoginLogList(UserLoginLogVO vo) throws Exception {
        
        List<UserLoginLogVO> result = new ArrayList<UserLoginLogVO>();
        int cnt = userMapper.selectLoginLogListCnt(vo);
        if(cnt > 0) {
            result = userMapper.selectLoginLogList(vo);
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));
        
        return map;
    }
    
    
    @Override
    public void updatePassword(LoginVO vo) throws Exception {
        
        String enpassword = commonService.encryptPassword(vo.getPassword(), vo.getId());
        vo.setPassword(enpassword);
        
        userMapper.updatePassword(vo);
    }
    
    
    @Override
    public UserMapper getMapper() {
        return this.userMapper;
    }
    
    
    @Override
    public List<UserLoginLogVO> selectLoginLogListAll(UserLoginLogVO vo) throws Exception {
        
        return userMapper.selectLoginLogListAll(vo);
    }
    
    
}