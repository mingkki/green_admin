<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
	String requestUri = (String)request.getAttribute("javax.servlet.forward.request_uri");
	if (requestUri.lastIndexOf("/auth/login") > -1) {
		request.setAttribute("LAYOUT", "blank_layout.jsp");
	} else if (requestUri.lastIndexOf("/board") > -1
			&& "category".equals(EgovStringUtil.nullConvert(request.getParameter("act")))) {
		request.setAttribute("LAYOUT", "popup_layout.jsp");
	} else if (requestUri.lastIndexOf("/site/menu.do") > -1
		&& "search".equals(EgovStringUtil.nullConvert(request.getParameter("act")))) {
		request.setAttribute("LAYOUT", "popup_layout.jsp");
	} else if (requestUri.lastIndexOf("/survey.do") > -1) {
		String act = EgovStringUtil.nullConvert(request.getParameter("act"));
		if ("qlist".equals(act) || "qwrite".equals(act) || "qdelete".equals(act)) {
			request.setAttribute("LAYOUT", "popup_layout.jsp");
		}
	}	
%>
<%@include file="/WEB-INF/jsp/egovframework/coing/include/view.jsp"%>