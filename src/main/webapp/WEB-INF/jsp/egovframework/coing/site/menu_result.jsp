<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
	<c:choose>
		<c:when test="${message == 'common.success.insert'}">
			alert("<spring:message code='${message}' />");
			document.location.href = "?sinId=<c:out value='${searchSiteMenuVO.sinId}' />&smeParntsId=<c:out value='${searchSiteMenuVO.smeParntsId}' />";
		</c:when>
		<c:when test="${message == 'common.success.update'}">
			alert("<spring:message code='${message}' />");
			document.location.href = "?sinId=<c:out value='${searchSiteMenuVO.sinId}' />&smeId=<c:out value='${searchSiteMenuVO.smeId}' />";
		</c:when>
		<c:when test="${message == 'common.success.move'}">
			alert("<spring:message code='common.success.update' />");
			document.location.href = "?sinId=<c:out value='${searchSiteMenuVO.sinId}' escapeXml='false' />";
		</c:when>
		<c:when test="${message == 'common.success.delete'}">
			alert("<spring:message code='${message}' />");
			document.location.href = "?sinId=<c:out value='${searchSiteMenuVO.sinId}' escapeXml='false' />";
		</c:when>
		<c:otherwise>
			alert("<spring:message code='${message}' />");
			history.back(-1);
		</c:otherwise>
	</c:choose>
</script>