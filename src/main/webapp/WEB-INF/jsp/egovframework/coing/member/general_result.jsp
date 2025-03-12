<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
	<c:choose>
		<c:when test="${message == 'common.success.insert'}">
			alert("<spring:message code='${message}' />");
			document.location.href = "?<c:out value='${param.returnQueryString}' escapeXml='false' />";
		</c:when>
		<c:when test="${message == 'common.success.update'}">
			alert("<spring:message code='${message}' />");
			document.location.href = "?act=write&memId=<c:out value='${searchMemberGeneralVO.memId}' />&<c:out value='${param.returnQueryString}' escapeXml='false' />";
		</c:when>
		<c:when test="${message == 'common.success.delete'}">
			alert("<spring:message code='${message}' />");
			document.location.href = "?<c:out value='${searchMemberGeneralVO.queryString}' escapeXml='false' />";
		</c:when>
		<c:when test="${message == 'common.success.process'}">
			alert("<spring:message code='${message}' />");
			document.location.href = "?<c:out value='${param.returnQueryString}' escapeXml='false' />";
		</c:when>
		<c:otherwise>
			alert("<spring:message code='${message}' />");
			history.back(-1);
		</c:otherwise>
	</c:choose>
</script>