<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="head.jsp"%>
<style type="text/css">
html, body{overflow:visible;}
.block-content{overflow-x:hidden !important;}
</style>
<body>

<script type="text/javascript" charset="utf-8" src="${CONTEXT_PATH}/assets/js/timeoutchk.js"></script>
<script type="text/javascript">
function openerRefreshTimer() {
	if(typeof(opener) != "undefined") {
		opener.refreshTimer();
	}
}

openerRefreshTimer();
</script>
<a href="#" style="font-size:12px;">
	<span id="timer"></span>
	<button type="button" class="btn btn-default btn-xs" onclick="refreshTimer(); openerRefreshTimer();">연장</button>
</a>			            
			            
<c:import url="${CONTENT_FILE}" />
</body>
</html>