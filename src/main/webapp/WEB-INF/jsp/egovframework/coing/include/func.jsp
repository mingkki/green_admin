<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="egovframework.coing.cmm.util.EgovUserDetailsHelper" %>
<%@ page import="egovframework.coing.cmm.util.AdminMenuHelper" %>
<%@ page import="egovframework.coing.cmm.vo.LoginVO" %>
<%@ page import="egovframework.coing.cmm.vo.AdminMenuVO" %>

<%
	AdminMenuHelper amh = new AdminMenuHelper(request);
	
	HashMap<Integer, AdminMenuVO> PARNTS_ADMIN_MENU_LIST = new HashMap<Integer, AdminMenuVO>();
	
	AdminMenuVO ADMIN_MENU_VO = (AdminMenuVO)request.getAttribute("ADMIN_MENU_VO");
	if (ADMIN_MENU_VO != null) {
		List<AdminMenuVO> list = amh.getParentMenuList(ADMIN_MENU_VO.getAmeId());
		for (int i = 0; i < list.size(); i++) {
			AdminMenuVO vo = (AdminMenuVO) list.get(i);
			PARNTS_ADMIN_MENU_LIST.put(i, vo);
		}		
		PARNTS_ADMIN_MENU_LIST.put(list.size(), ADMIN_MENU_VO);
	}	
	
	request.setAttribute("PARNTS_ADMIN_MENU_LIST", PARNTS_ADMIN_MENU_LIST);
%>

<%!
	public String getMenuLink(HttpServletRequest request,
			AdminMenuVO adminMenuVO) {
			
		String href = "";
		if ("LINK_IN".equals(adminMenuVO.getAmeType())) {			
			href = request.getContextPath() + adminMenuVO.getAmeLinkUrl(); 
		} else if ("LINK_OUT".equals(adminMenuVO.getAmeType())) {
			href = request.getContextPath() + adminMenuVO.getAmeLinkUrl();
		} else {
			href = request.getContextPath() + adminMenuVO.getAmeLinkUrl();
		}
		
		String target = (adminMenuVO.getAmeLinkTarget().equals("_BLANK")) ? "target=\"_blank\" title=\"새창\"" : "";
		String onclick = (adminMenuVO.getAmeLinkTarget().equals("_POPUP")) ? "onclick=\"window.open(this.href, 'menu" + adminMenuVO.getAmeId() + "', '" + adminMenuVO.getAmePopupParam() + "'); return false;\"" : "";
				
		String title = adminMenuVO.getAmeName();	
		
		/*
		String icon = EgovStringUtil.nullConvert(adminMenuVO.getMenuIcon());
		if (!"".equals(icon)) {
			icon = String.format("<i class=\"%s\"></i>", icon);
		}
		*/
		
		return String.format("<a href=\"%s\" %s %s>%s</a>", href, target, onclick, title);
	}
%>