package egovframework.coing.cmm.interceptor;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.service.AdminMenuService;
import egovframework.coing.cmm.service.ManagerService;
import egovframework.coing.cmm.service.SystemConfigService;
import egovframework.coing.cmm.service.UserService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.EgovUserDetailsHelper;
import egovframework.coing.cmm.vo.AdminMenuVO;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.cmm.vo.SystemConfigVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

@RequiredArgsConstructor
public class CommonInterceptor extends WebContentInterceptor {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonInterceptor.class);
    private final SystemConfigService systemConfigService;
    private final ManagerService managerService;
    private final UserService userService;
    private final AdminMenuService adminMenuService;
    private Runtime r = Runtime.getRuntime();
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        
        String contextPath = request.getContextPath();
        String forwardUri = (String) request.getAttribute("javax.servlet.forward.request_uri");
        String requestUri = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();
        
        // IP체크
        SystemConfigVO systemConfigVO = null;
        try {
            systemConfigVO = systemConfigService.selectSystemConfig();
            if(systemConfigVO == null) {
                ModelAndView mv = new ModelAndView("egovframework/coing/error/exception");
                mv.addObject("message", "환경설정 정보가 없습니다. 환경설정정보를 등록해 주세요.");
                throw new ModelAndViewDefiningException(mv);
            }
        } catch(Exception e) {
            LOGGER.error("systemConfigVO Exception", e);
            ModelAndView mv = new ModelAndView("egovframework/coing/error/exception");
            mv.addObject("message", e.getMessage());
            throw new ModelAndViewDefiningException(mv);
        }
        
        String cfgAdminAccessIp = EgovStringUtil.nullConvert(systemConfigVO.getCfgAdminAccessIp());
        if(!"".equals(cfgAdminAccessIp)) {
            if(!CmsManager.ipCheck(remoteAddr, cfgAdminAccessIp)) {
                ModelAndView mv = new ModelAndView("egovframework/coing/error/access_denied_system_ip");
                mv.addObject("remoteAddr", remoteAddr);
                throw new ModelAndViewDefiningException(mv);
            }
        }
        
        // 인증을 스킵할 URL
        ArrayList<String> skipUrlList = new ArrayList<String>();
        skipUrlList.add("/auth/login.do");
        skipUrlList.add("/auth/logout.do");
        skipUrlList.add("/auth/password/period.do");
        
        
        boolean isSkipUrl = false;
        for(int i = 0; i < skipUrlList.size(); i++) {
            if(requestUri.indexOf(skipUrlList.get(i)) > -1) {
                isSkipUrl = true;
                break;
            }
        }
        
        // 로그인페이지, 비밀번호 변경 기간 등의 URL은 인증체크를 스킵함.
        if(isSkipUrl) {
            request.setAttribute("CONTEXT_PATH", contextPath);
            request.setAttribute("REQUEST_URI", requestUri);
            request.setAttribute("SYSTEM_CONFIG_VO", systemConfigVO);
            return true;
        }
		
		/*if ("LOC".equals(Globals.RUN_MODE)) {
			try {
				if (!EgovUserDetailsHelper.isAuthenticated()) {
					LoginVO loginVO = new LoginVO();
					loginVO.setId("admin");
					loginVO.setPassword("rkdhs!23");
					loginVO.setIp(request.getRemoteAddr());
					loginVO.setReferrer(request.getHeader("referer"));
					loginVO.setUseragent(request.getHeader("User-Agent"));
					loginVO = userService.actionLogin(loginVO);
					request.getSession().setAttribute(Globals.LOGIN_SESSION_NM, loginVO);
				}
			} catch (Exception e) {
				
			}
		}	*/
        
        // 로그인 체크
        if(!EgovUserDetailsHelper.isAuthenticated()) {
            try {
                response.sendRedirect(request.getContextPath() + "/auth/login.do");
            } catch(IOException e) {
                LOGGER.debug("IO Exception", e);
            }
            return false;
        }
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        
        // 사용자구분이 MNG(관리자)이 아닌 경우
        if(!"MNG".equals(loginVO.getUserSe()) && !"STF".equals(loginVO.getUserSe())) {
            ModelAndView mv = new ModelAndView("egovframework/coing/error/access_denied_user_se");
            mv.addObject("loginVO", loginVO);
            throw new ModelAndViewDefiningException(mv);
        }
        //////////////////////////////////////////////////////////////////////////////////
        
        
        // 관리자 정보
		/*ManagerVO managerVO = null;
		try {
			managerVO = new ManagerVO();
			managerVO.setMngId(loginVO.getId());
			managerVO = managerService.selectManager(managerVO);			
			if (managerVO == null) {
				ModelAndView mv = new ModelAndView("egovframework/coing/error/exception");
				mv.addObject("message", "관리자 정보가 없습니다. 관리자 정보를 등록해 주세요.");
				throw new ModelAndViewDefiningException(mv);
			}
		} catch (Exception e) {
			LOGGER.error("managerVO Exception", e);
			ModelAndView mv = new ModelAndView("egovframework/coing/error/exception");
			mv.addObject("message", e.getMessage());
			throw new ModelAndViewDefiningException(mv);
		}*/
        //////////////////////////////////////////////////////////////////////////////////
        
        
        // 로그인 가능한 IP가 아닌 경우
		/*
		String mngAccessIp = EgovStringUtil.nullConvert(managerVO.getMngAccessIp());
		if (!"".equals(mngAccessIp)) {
			if (!CmsManager.ipCheck(remoteAddr, mngAccessIp)) {
				ModelAndView mv = new ModelAndView("egovframework/coing/error/access_denied_user_ip");
				mv.addObject("remoteAddr", remoteAddr);
				throw new ModelAndViewDefiningException(mv);				
			}
		}
		*/
        ///////////////////////////////////////////////////////////////////////////////////

		/*
		// 비밀번호변경기간인지 체크
		if (systemConfigVO.getCfgAdminPwchangeMonth() > 0) {
			boolean checked = false;
			String lastPwchangeDttm = "";
			try {
				lastPwchangeDttm = EgovStringUtil.nullConvert(loginVO.getPwchangeDttm()).replace("-", "").substring(0, 8);
				if (!"".equals(lastPwchangeDttm) && EgovDateUtil.checkDate(lastPwchangeDttm)) {
					String date = EgovDateUtil.addMonth(lastPwchangeDttm, systemConfigVO.getCfgAdminPwchangeMonth());
					String today = EgovDateUtil.getToday();
					if (Integer.parseInt(today) <= Integer.parseInt(date)) {
						checked = true;
					}
					loginVO.setPwchangeDday(EgovDateUtil.getDaysDiff(today, date));
				}
			} catch (Exception e) {
				LOGGER.debug("Exception", e);
			}

			if (!checked) {
				try {
					response.sendRedirect(request.getContextPath()+"/auth/password/period.do");
				} catch (IOException e) {
					LOGGER.debug("IO Exception", e);
				} 
				return false;	
			}				
		}
		*/
        ///////////////////////////////////////////////////////////////////////////////////////////
        
        
        // 메뉴정보
        AdminMenuVO adminMenuVO = null;
        if(forwardUri == null) {
            try {
                adminMenuVO = new AdminMenuVO();
                adminMenuVO.setAmeType("LINK_IN");
                adminMenuVO.setAmeLinkUrl(requestUri.replace(contextPath, ""));
                adminMenuVO = adminMenuService.selectAdminMenuByMenuTy(adminMenuVO);
            } catch(Exception e) {
                LOGGER.error("systemConfigVO Exception", e);
                ModelAndView mv = new ModelAndView("egovframework/coing/error/exception");
                mv.addObject("message", e.getMessage());
                throw new ModelAndViewDefiningException(mv);
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////////
        
        
        request.setAttribute("CONTEXT_PATH", contextPath);
        request.setAttribute("REQUEST_URI", requestUri);
        request.setAttribute("LOGIN_INFO", loginVO);
        request.setAttribute("SYSTEM_CONFIG_VO", systemConfigVO);
        request.setAttribute("IS_MASTER_ADMIN", EgovUserDetailsHelper.isMasterAdmin());
        //request.setAttribute("MANAGER_VO", managerVO);
        request.setAttribute("ADMIN_MENU_VO", adminMenuVO);
        
        return true;
    }
    
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        
        DecimalFormat format = new DecimalFormat("###,###,###.##");
        
        LOGGER.info("==> Memory: Total:" + format.format(r.totalMemory() / 1024));
        LOGGER.info("==> Memory: Free:" + format.format(r.freeMemory() / 1024));
    }
    
}