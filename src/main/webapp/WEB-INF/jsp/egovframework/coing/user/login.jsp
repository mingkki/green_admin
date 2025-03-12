<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html class="no-js" lang="ko">
<head>
	<title>로그인</title>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />

	<link href="<c:out value='${CONTEXT_PATH}'/>/assets/css/styles.css" rel="stylesheet" type="text/css" id="theme"/>
	<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/jquery/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/bootstrap/bootstrap.min.js"></script>
	<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
	<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/jquery-validation/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/jquery-validation/localization/messages_ko.js"></script>
	<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/actions.js"></script>
</head>
<body>
<style>
body{background:#fff;}
.login{width:800px; margin:0 auto; margin-top:150px;}
.login_body{position:relative; height:242px;}
.login_body:before{position:absolute; content:""; display:block; width:300px; height:242px; background:url('../assets/img/gssc_login.png')no-repeat center; background-size: 300px 242px;border-radius:10px;}
.login_tail{margin:40px 0 0 0; padding:20px 0 0 0; border-top:1px solid #888; text-align:center; color:#888;}
#loginForm{padding:0 50px 0 350px;}
#loginForm h1{padding:20px 0 15px 0; font-size:24px; font-weight:600; letter-spacing:-1px;}
#loginForm .item{margin:5px 0 0 0;}
#loginForm .item input{padding:13px; border:1px solid #999; display:block; width:100%;}
#loginForm .btn_login{margin:10px 0 0 0; background:#333; border:none; width:100%; padding:18px 0; color:#fff; font-size:15px;}
</style>

<div class="login">

	<div class="login_body">
				<form:form name="loginForm" id="loginForm" commandName="loginVO" role="form">
					<h1>CMS 관리 시스템</h1>
					<div class="item">
						<form:input path="id" class="required" placeholder="ID" value="" />
					</div>
					<div class="item">
						<form:password path="password" class="required" placeholder="Password" value="" />
					</div>
					<button class="btn_login" type="submit">Login</button>
				</form:form>
	</div>
	<div class="login_tail">
		<div class="txt1">
			<p>CMS 관리 시스템 운영관리에 대한 <u>문의사항이 있으실 경우</u> 담당자 연락처로 연락주시기 바랍니다.</p>
		</div>
	</div>

</div>
<script type="text/javascript">
$(function()
{
	$("#loginForm").validate();
});
</script>
</body>
</html>