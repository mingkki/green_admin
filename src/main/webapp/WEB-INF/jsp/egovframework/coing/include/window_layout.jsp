<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="func.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <title><c:out value="${TITLE}"/></title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  
  <link href="<c:out value='${CONTEXT_PATH}'/>/assets/css/theme-d-hw-turquoise.css" rel="stylesheet" type="text/css" id="theme"/>
  <link href="<c:out value='${CONTEXT_PATH}'/>/assets/css/ereservation/content_nj.css" rel="stylesheet" type="text/css"/>
  <link href="<c:out value='${CONTEXT_PATH}'/>/assets/css/ereservation/sub_temp.css" rel="stylesheet" type="text/css"/>
  <link href="<c:out value='${CONTEXT_PATH}'/>/assets/css/ereservation/sub.css" rel="stylesheet" type="text/css"/>
  
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/jquery/jquery.min.js"></script>
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/jquery/jquery-ui.min.js"></script>
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/bootstrap/bootstrap.min.js"></script>
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins.js"></script>
  
  <script type="text/javascript" src="${CONTEXT_PATH}/validator.do"></script>
  
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/actions.js"></script>
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/sys.js"></script>
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/message-1.0.0.js"></script>
  <script type="text/javascript">
    var baseUrl = '${CONTEXT_PATH}';
  </script>
</head>
<body>
<div class="page-container">
  <div class="content_box"><c:import url="${CONTENT_FILE}"/></div>
</div>
</body>
</html>