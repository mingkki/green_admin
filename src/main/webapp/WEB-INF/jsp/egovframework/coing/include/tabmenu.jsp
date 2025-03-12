<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="egovframework.coing.cmm.util.AdminMenuHelper" %>
<%@ page import="egovframework.coing.cmm.vo.AdminMenuVO" %>
<%@ include file="func.jsp" %>
<%
	if (PARNTS_ADMIN_MENU_LIST.get(2) != null) {
		List<AdminMenuVO> tabmenuList = amh.getMenuList(PARNTS_ADMIN_MENU_LIST.get(2).getAmeId());
		AdminMenuVO tabmenuVO;
		String addClass = "";		
		out.println("<ul class=\"nav nav-tabs\">");
		for (int k = 0; k < tabmenuList.size(); k++) {
			tabmenuVO = (AdminMenuVO)tabmenuList.get(k);
			
			addClass = "";
			if (PARNTS_ADMIN_MENU_LIST.get(2) != null && tabmenuVO.getAmeId() == PARNTS_ADMIN_MENU_LIST.get(3).getAmeId()) {
				addClass += " class=\"active\"";
			}
			
			out.println(String.format("<li%s>%s</li>", addClass, getMenuLink(request, tabmenuVO)));
		}
		out.println("</ul>");
	}
%>