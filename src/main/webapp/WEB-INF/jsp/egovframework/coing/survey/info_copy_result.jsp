<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
<c:choose>
	<c:when test="${message == 'common.success.process'}">
		alert("<spring:message code='${message}' />");
		document.location.href = "?<c:out value='${searchSurveyInfoVO.queryString}' escapeXml='false' />";
	</c:when>
	<c:otherwise>
		alert("<c:out value='${messageStr}' />");
		history.back(-1);
	</c:otherwise>
</c:choose>
</script>