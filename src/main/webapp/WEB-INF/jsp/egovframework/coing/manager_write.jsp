<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">
	<div class="col-lg-12">
		<div class="block">
			<div class="block-head">
				<h1><c:out value="${ADMIN_MENU_VO.amePname}"/> <c:choose><c:when test="${command == 'insert'}"><spring:message code="common.insert" /></c:when><c:otherwise><spring:message code="common.update" /></c:otherwise></c:choose></h1>
			</div>
			<div class="block-content">
				<form:form commandName="writeManager" action="?act=write" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}' />" />
					<input type="hidden" name="returnQueryString" value="<c:out value='${searchManagerVO.queryString}' escapeXml='false' />"/>
					<c:if test="${command eq 'update'}">
					<input type="hidden" name="mngId" value="${searchManagerVO.mngId}"/>
					</c:if>
					<fieldset>
						<legend class="sr-only">등록정보</legend>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mngId" class="control-label">* 아이디</label></span>
							<div class="col-sm-10">
								<c:choose>
									<c:when test="${command eq 'insert'}">
										<form:input path="mngId" class="form-control required" maxlength="20" />
									</c:when>
									<c:otherwise>
										<form:input path="mngId" class="form-control" disabled="true" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mngName" class="control-label">* 이름</label></span>
							<div class="col-sm-10">
								<form:input path="mngName" class="form-control required" maxlength="60" />
							</div>
						</div>
						<c:if test="${command eq 'insert'}">
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mngPasswd" class="control-label">* 비밀번호</label></span>
							<div class="col-sm-10">
								<form:password path="mngPasswd" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2  text-right"><label for="mngPasswdConfirm" class="control-label">* 비밀번호 확인</label></span>
							<div class="col-sm-10">
								<input type="password" name="mngPasswdConfirm" id="mngPasswdConfirm" class="form-control required" value="" />
							</div>
						</div>
						</c:if>
						<c:if test="${command eq 'update'}">
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mngPasswd" class="control-label">비밀번호변경</label></span>
							<div class="col-sm-10">
								<form:password path="mngPasswd" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2  text-right"><label for="mngPasswdConfirm" class="control-label">비밀번호변경 확인</label></span>
							<div class="col-sm-10">
								<input type="password" name="mngPasswdConfirm" id="mngPasswdConfirm" class="form-control" value="" />
							</div>
						</div>
						</c:if>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mngEmail" class="control-label">이메일</label></span>
							<div class="col-sm-2">							
								<form:input path="mngEmail1" class="form-control" />
							</div>
							<div class="col-sm-2">
								<form:input path="mngEmail2" class="form-control" />
							</div>
							<div class="col-sm-2">
								<form:select path="mngEmail3" class="form-control" onchange="selectEmail(this);">
								<form:option value="">+ 선택</form:option>
								<c:forEach var="result" items="${emailList}" varStatus="status">
								<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
								</c:forEach>
								<form:option value="1">직접입력</form:option>
								</form:select>
							</div>								
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mngTel" class="control-label">전화번호</label></span>
							<div class="col-sm-2">
								<form:select path="mngTel1" class="form-control">
								<form:option value="">+ 선택</form:option>
								<c:forEach var="result" items="${telList}" varStatus="status">
								<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
							<div class="col-sm-2">
								<form:input path="mngTel2" class="form-control" maxlength="4"/>
							</div>
							<div class="col-sm-2">
								<form:input path="mngTel3" class="form-control" maxlength="4"/>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mngHp" class="control-label">휴대폰번호</label></span>
							<div class="col-sm-2">
								<form:select path="mngHp1" class="form-control">
								<form:option value="">+ 선택</form:option>
								<c:forEach var="result" items="${hpList}" varStatus="status">
								<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
							<div class="col-sm-2">
								<form:input path="mngHp2" class="form-control" maxlength="4"/>
							</div>
							<div class="col-sm-2">
								<form:input path="mngHp3" class="form-control" maxlength="4"/>
							</div>
						</div>	
						<div class="form-group">
							<span class="col-sm-2  text-right"><label class="control-label" for="mngAccessIp">관리자페이지 접근제한 IP</label></span>
							<div class="col-sm-10">
								<form:textarea path="mngAccessIp" rows="5" class="form-control" />
								<div class="help-block">
									입력된 IP의 컴퓨터만 접근할 수 있습니다.(엔터로 구분)
								</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2  text-right"><label for="mngStatus" class="control-label">* 사용자상태</label></span>
							<div class="col-sm-10">
								<form:select path="mngStatus" class="form-control required">
								<form:option value="P">승인</form:option>
								<form:option value="L">잠김</form:option>
								<form:option value="H">휴면</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2  text-right"><label for="mngLevel" class="control-label">등급</label></span>
							<div class="col-sm-10">
								<form:select path="mngLevel" class="form-control">
								<form:option value="0">+ 선택</form:option>
								<form:option value="97">사이트 부관리자</form:option>
								<form:option value="98">사이트 관리자</form:option>
								<form:option value="99">최고 관리자</form:option>
								</form:select>
							</div>
						</div>
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<a href="?<c:out value='${searchManagerVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list" /></a>
							</div>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(function() {
	$("#writeManager").validate({
		rules: {
			mngPasswd:{minlength:6, maxlength:12}
			, mngPasswdConfirm:{equalTo: "#mngPasswd"}
		},
		messages: {
			mngPasswdConfirm:{equalTo: "비밀번호 변경과 비밀번호변경 확인이 일치하지 않습니다."}
		},
		submitHandler: function(form) {
			if (form.mngPasswd.value) {
				if (!form.mngPasswd.value.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~,-])|([!,@,#,$,%,^,&,*,?,_,~,-].*[a-zA-Z0-9])/)) {
					alert("비밀번호는 영문(대소문자구분),숫자,특수문자(~!@#$%^&*()-_? 만 허용)를 혼용하여 6~12자를 입력해주세요.");
					return false;
				}
			}
			form.submit();
		}
	});
});
</script>

<script type="text/javascript">
function selectEmail(ele) { 
	var $ele = $(ele); 
	var $email2 = $('input[name=mngEmail2]');
	if ($ele.val() == "1") { 
		$email2.attr('readonly', false); 
		$email2.val(''); 
	} else { 
		$email2.attr('readonly', true); 
		$email2.val($ele.val()); 
	} 
}
</script>