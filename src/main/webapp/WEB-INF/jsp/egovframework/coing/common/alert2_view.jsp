<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
alert("<c:out value='${msg}' escapeXml='false'/>");
<c:choose>
	<c:when test="${empty url}">
		history.go(-1);
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${windowYn == 'Y'}">
				self.close();
				opener.document.location.reload();				
			</c:when>
			<c:otherwise>
				location.replace('<c:out value="${url}" escapeXml="false"/>');
			</c:otherwise>
		</c:choose>		
	</c:otherwise>
</c:choose>
</script>