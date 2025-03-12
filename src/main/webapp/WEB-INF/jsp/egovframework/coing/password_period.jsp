<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js" lang="ko">
<head>
	<title>비밀번호 변경주기에 따른 비밀번호 변경</title>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />

	<link href="<c:out value='${CONTEXT_PATH}'/>/assets/css/styles.css" rel="stylesheet" type="text/css" id="theme"/>
	<!--[if lt IE 10]><link rel="stylesheet" type="text/css" href="<c:out value='${CONTEXT_PATH}'/>/assets/css/ie.css"/><![endif]-->

	<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/jquery/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/bootstrap/bootstrap.min.js"></script>
	<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>

	<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/jquery-validation/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/jquery-validation/localization/messages_ko.js"></script>

	<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/actions.js"></script>
</head>
<body>
<div class="page-container">
	<div class="page-content page-content-default">
		<div class="block-login">
			<div class="block-login-content">				
					<h1>비밀번호 변경</h1>
					<form id="passwordPeriodForm" action="${REQUEST_URI}" method="post">
                       <div class="form-group">                        
                            <label>현재 비밀번호 :</label>
                            <input type="password" name="password" id="password" class="form-control" value="" />
                        </div>
						<div class="form-group">                    
                            <label>새 비밀번호 :</label>
                            <input type="password" name="newPassword" id="newPassword" class="form-control" value="" />
                            <div class="help-block">
                            	비밀번호는 영문(대소문자구분),숫자,특수문자(~!@#$%^&*()-_? 만 허용)를 혼용하여 6~12자를 입력해주세요.
                            </div>
                        </div>
						<div class="form-group">                        
                            <label>새 비밀번호 확인 :</label>
                            <input type="password" name="newPasswordConfirm" id="newPasswordConfirm" class="form-control" value="" />
                        </div>
						<button class="btn btn-primary btn-block" type="submit">변경하기</button>
					</form>
					<div class="sp"></div>
                    <div class="pull-left">
                    	${SYSTEM_CONFIG_VO.cfgAdminPwchangeMonth}개월 마다 비밀번호를 변경하셔야 합니다.
                    </div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function() {
	$("#passwordPeriodForm").validate({
		rules: {
			password:{required:true}
			, newPassword:{required:true, minlength:6, maxlength:12}
			, newPasswordConfirm:{required:true, equalTo: "#newPassword"}
		},
		messages: {
			newPasswordConfirm:{equalTo: "새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다."}
		},
		submitHandler: function(form) {
			if (!form.newPassword.value.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~,-])|([!,@,#,$,%,^,&,*,?,_,~,-].*[a-zA-Z0-9])/)) {
				alert("비밀번호는 영문(대소문자구분),숫자,특수문자(~!@#$%^&*()-_? 만 허용)를 혼용하여 6~12자를 입력해주세요.");
				return false;
			}
			form.submit();
		}
	});
});
</script>
</body>
</html>