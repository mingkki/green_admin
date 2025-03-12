<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="egovframework.coing.cmm.util.EgovStringUtil" %>
<%
	String layout = EgovStringUtil.nullConvert((String)request.getAttribute("LAYOUT"));
	String layoutFile = "";
	if ("".equals(layout)) {
		layoutFile = "/WEB-INF/jsp/egovframework/coing/include/main_layout.jsp";
	} else {
		layoutFile = "/WEB-INF/jsp/egovframework/coing/include/" + layout;
	}
%>
<jsp:include page="<%=layoutFile%>" flush="true" />