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
				<form:form commandName="writeMemberGeneral" action="?act=write" class="form-horizontal" role="form">
					<input type="hidden" name="command" id="command" value="<c:out value='${command}' />" />
					<input type="hidden" name="returnQueryString" value="<c:out value='${searchMemberGeneralVO.queryString}' escapeXml='false' />"/>
					<input type="hidden" name="regnumcheck" id="regnumcheck" value="N"/>
					<input type="hidden" name="memGender" id="memGender" value="M"/>
					<c:if test="${command eq 'update'}">
					<input type="hidden" name="memId" value="${searchMemberGeneralVO.memId}"/>
					</c:if>
					<fieldset>
						<legend class="sr-only">등록정보</legend>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memId" class="control-label">* 아이디</label></span>
							<div class="col-sm-10">
								<c:choose>
									<c:when test="${command eq 'insert'}">
										<form:input path="memId" class="form-control required" maxlength="20" /><button type="button" onclick="checkMemberId();">중복확인</button>
										<div class="help-block">영문자와 숫자로 4자 이상 20자 이하로 입력하세요.</div>
									</c:when>
									<c:otherwise>
										<form:input path="memId" class="form-control" disabled="true" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<c:if test="${command eq 'insert'}">
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memPasswd" class="control-label">* 비밀번호</label></span>
							<div class="col-sm-10">
								<form:password path="memPasswd" class="form-control required" />
								<div class="help-block">영문자와 숫자로 4자 이상 12자 이하로 입력하세요.</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memPasswdConfirm" class="control-label">* 비밀번호 확인</label></span>
							<div class="col-sm-10">
								<input type="password" name="memPasswdConfirm" id="memPasswdConfirm" class="form-control required" value="" />
							</div>
						</div>
						</c:if>
						<c:if test="${command eq 'update'}">
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memPasswd" class="control-label">비밀번호변경</label></span>
							<div class="col-sm-10">
								<form:password path="memPasswd" class="form-control" />
								<div class="help-block">영문자와 숫자로 4자 이상 20자 이하로 입력하세요.</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memPasswdConfirm" class="control-label">비밀번호변경 확인</label></span>
							<div class="col-sm-10">
								<input type="password" name="memPasswdConfirm" id="memPasswdConfirm" class="form-control" value="" />
							</div>
						</div>
						</c:if>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memName" class="control-label">* 이름</label></span>
							<div class="col-sm-10">
								<form:input path="memName" class="form-control required" maxlength="60" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">본인확인여부</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="memIdentifyYn" value="Y"/>예</label>
								<label class="radio-inline"><form:radiobutton path="memIdentifyYn" value="N"/>아니오</label>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">본인확인방법</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="memIdentifyGb" value="Y"/>아이핀</label>
								<label class="radio-inline"><form:radiobutton path="memIdentifyGb" value="N"/>휴대폰</label>
							</div>
						</div>
						<%--<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">성별</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="memGender" value="M"/>남성</label>
								<label class="radio-inline"><form:radiobutton path="memGender" value="F"/>여성</label>
							</div>
						</div>--%>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memBirthDt" class="control-label">생년월일</label></span>
							<div class="col-sm-2">							
								<form:input path="memBirthDt" class="form-control datepicker" />
							</div>
							<div class="col-sm-4">
								<label class="radio-inline"><form:radiobutton path="memBirthGb" value="S" />양력</label>
								<label class="radio-inline"><form:radiobutton path="memBirthGb" value="L" />음력</label>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memEmail" class="control-label">이메일</label></span>
							<div class="col-sm-2">							
								<form:input path="memEmail1" class="form-control" />
							</div>
							<div class="col-sm-2">
								<form:input path="memEmail2" class="form-control" />
							</div>
							<div class="col-sm-2">
								<form:select path="memEmail3" class="form-control" onchange="selectEmail(this);">
								<form:option value="">+ 선택</form:option>
								<c:forEach var="result" items="${emailList}" varStatus="status">
								<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
								</c:forEach>
								<form:option value="1">직접입력</form:option>
								</form:select>
							</div>								
							<div class="col-sm-2">
								<label class="checkbox-inline"><form:checkbox path="memEmailYn" value="Y" />이메일 수신</label>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memHomepage" class="control-label">홈페이지</label></span>
							<div class="col-sm-10">
								<form:input path="memHomepage" class="form-control" />
								<div class="help-block">
									입력예) http://www.naver.com
								</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memTel" class="control-label">전화번호</label></span>
							<div class="col-sm-2">
								<form:select path="memTel1" class="form-control">
								<form:option value="">+ 선택</form:option>
								<c:forEach var="result" items="${telList}" varStatus="status">
								<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
							<div class="col-sm-2">
								<form:input path="memTel2" class="form-control" maxlength="4"/>
							</div>
							<div class="col-sm-2">
								<form:input path="memTel3" class="form-control" maxlength="4"/>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memFax" class="control-label">FAX번호</label></span>
							<div class="col-sm-2">
								<form:select path="memFax1" class="form-control">
								<form:option value="">+ 선택</form:option>
								<c:forEach var="result" items="${faxList}" varStatus="status">
								<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
							<div class="col-sm-2">
								<form:input path="memFax2" class="form-control" maxlength="4"/>
							</div>
							<div class="col-sm-2">
								<form:input path="memFax3" class="form-control" maxlength="4"/>
							</div>							
						</div>						
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memHp" class="control-label">휴대폰번호</label></span>
							<div class="col-sm-2">
								<form:select path="memHp1" class="form-control">
								<form:option value="">+ 선택</form:option>
								<c:forEach var="result" items="${hpList}" varStatus="status">
								<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
							<div class="col-sm-2">
								<form:input path="memHp2" class="form-control" maxlength="4"/>
							</div>
							<div class="col-sm-2">
								<form:input path="memHp3" class="form-control" maxlength="4"/>
							</div>
							<div class="col-sm-2">
								<label class="checkbox-inline"><form:checkbox path="memHpYn" value="Y" />SMS 수신</label>
							</div>
						</div>



						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memComAddress1" class="control-label">주소</label></span>
							<div class="col-sm-10">
								<form:input path="memComAddress1" id="memComAddress1" class="form-control" readonly="true"/>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memComAddress2" class="control-label">거주동</label></span>
							<div class="col-sm-4">
								<form:select path="memComAddress2" id="memComAddress2" class="form-control">
									<form:option value="">+ 거주동 선택</form:option>
									<c:forEach var="result" items="${emdList}" varStatus="status">
										<form:option value="${result.cddName}"><c:out value="${result.cddName}"/></form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memBank" class="control-label">은행명</label></span>
							<div class="col-sm-4">
								<form:select path="memBank" id="memBank" class="form-control">
									<form:option value="">+ 은행명 선택</form:option>
									<c:forEach var="result" items="${bankList}" varStatus="status">
										<form:option value="${result.cddName}"><c:out value="${result.cddName}"/></form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memBankAccountNumber" class="control-label">계좌번호</label></span>
							<div class="col-sm-10">
								<form:input path="memBankAccountNumber" id="memBankAccountNumber" class="form-control"/>
							</div>
						</div>



						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memMemo" class="control-label">관리자메모</label></span>
							<div class="col-sm-10">							
								<form:textarea path="memMemo" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memGroups">회원구분</label></span>
							<div class="col-sm-3">
							<c:choose>
								<c:when test="${command eq 'insert'}">
									<label><form:radiobutton path="memGroups" value="GNR" checked="true"/>일반회원</label>
									<label><form:radiobutton path="memGroups" value="MNG"/>강릉시청 직원</label>
								</c:when>
								<c:otherwise>
									<c:forEach var="result" items="${gubunList}" varStatus="status">
										<c:if test="${result.cddId eq writeMemberGeneral.memGroups}"><c:out value="${result.cddName}"/></c:if>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							</div>
						</div>


						<div class="form-group layerG" style="display:none;">
							<span class="col-sm-2 text-right"><label for="memComRegNum">제공인력 고유번호</label></span>
							<div class="col-sm-3">
								<form:input path="memComRegNum" class="form-control"/><button type="button" onclick="checkComRegNum();">중복확인</button>
							</div>
						</div>
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="memStatus" class="control-label">* 사용자상태</label></span>
							<div class="col-sm-10">
								<form:select path="memStatus" class="form-control required">
								<c:forEach var="result" items="${statusList}" varStatus="status">
								<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
						</div>
						<div id="layerMemStatusD">
							<div class="form-group">
								<span class="col-sm-2 text-right"><label class="control-label">탈퇴정보</label></span>
								<div class="col-sm-10">
									<div class="input-group mg-t-xs">
										<span class="input-group-addon">탈퇴일시</span>
										<form:input path="memLeaveDttm" class="form-control" />
										<span class="input-group-btn"><button type="button" onclick="openDaumPostcode();" class="btn btn-default btn-sm">현재시간 셋팅</button></span>
									</div>
									<div class="input-group mg-t-xs">
										<span class="input-group-addon">탈퇴자ID</span>
										<form:input path="memLeaveId" class="form-control" />									
									</div>
									<div class="input-group mg-t-xs">
										<span class="input-group-addon">탈퇴자IP</span>
										<form:input path="memLeaveIp" class="form-control" />									
									</div>
									<div class="input-group mg-t-xs">
										<span class="input-group-addon">탈퇴사유</span>
										<form:input path="memLeaveMemo" class="form-control" />									
									</div>
								</div>
							</div>
						</div>
						<div id="layerMemStatusL">
							<div class="form-group">
								<span class="col-sm-2 text-right"><label class="control-label">잠김정보</label></span>
								<div class="col-sm-10">
									<div class="input-group mg-t-xs">
										<span class="input-group-addon">잠금일시</span>
										<form:input path="memLockDttm" class="form-control" />									
									</div>
									<div class="input-group mg-t-xs">
										<span class="input-group-addon">잠금자ID</span>
										<form:input path="memLockId" class="form-control" />									
									</div>
									<div class="input-group mg-t-xs">
										<span class="input-group-addon">잠금자IP</span>
										<form:input path="memLockIp" class="form-control" />									
									</div>
									<div class="input-group mg-t-xs">
										<span class="input-group-addon">잠금사유</span>
										<form:input path="memLockMemo" class="form-control" />									
									</div>
								</div>
							</div>
						</div>
						<c:if test="${command eq 'update' }">
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">등록정보</label></span>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:out value="${writeMemberGeneral.memRegDttm}"/>
									/ <c:out value="${writeMemberGeneral.memRegIp}"/>
									/ 등록ID : <c:out value="${writeMemberGeneral.memRegId}"/>
								</p>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">비밀번호 변경정보</label></span>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:out value="${writeMemberGeneral.memPwchangeDttm}"/>
									/ <c:out value="${writeMemberGeneral.memPwchangeIp}"/>
								</p>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">최근 정보변경</label></span>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:out value="${writeMemberGeneral.memUpdtDttm}"/>
									/ <c:out value="${writeMemberGeneral.memUpdtIp}"/>
									/ 수정ID : <c:out value="${writeMemberGeneral.memUpdtId}"/>
								</p>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">최근 로그인</label></span>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:out value="${writeMemberGeneral.memLastloginDttm}"/>
									/ <c:out value="${writeMemberGeneral.memLastloginIp}"/>
									/ <c:out value="${writeMemberGeneral.memLoginCnt}"/>회
								</p>
							</div>
						</div>
						</c:if>
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<a href="?<c:out value='${searchMemberGeneralVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list" /></a>
							</div>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</div>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="${CONTEXT_PATH}/validator.do"></script>
<script type="text/javascript">
$(function()
{
	$("#writeMemberGeneral").validate();
	
	$("#memStatus").on('change', function() {
		changeMemStatus($(this).val());
	});
	
	function changeMemStatus(val) {
		switch (val) {
		case 'P':
			break;
		case 'A':
			break;
		case 'D':
			break;
		case 'L':
			break;
		}
	}
	
	changeMemStatus('<c:out value="${writeMemberGeneral.memStatus}"/>');
	
	$('input[name="memGroups"]').change(function() {			
		$("#memComRegNum").rules("remove");	
		document.getElementById('regnumcheck').value = 'N';
		document.getElementById('memComRegNum').value = '';
		var value = $(this).val();
		if( value == "EMP" ){
			$(".layerG").show();
			$("#memComRegNum").rules("add", {required: true, digits:true, minlength:8, maxlength:8});
		}else {
			$(".layerG").hide();
		}
	});
	
	$('input[name="memComRegNum"]').change(function() {
		document.getElementById('regnumcheck').value = 'N';
	});
});
</script>

<script type="text/javascript">
$(document).ready(function() {
	checkMemGroups();	
	$("#writeMemberGeneral").validate({		
		rules: {
			memId:{required:true, minlength:4, maxlength:12}
			, memName:{required:true}
			, memPasswd:{required:true, minlength:6, maxlength:12}
			, memPasswdRe:{equalTo: "#memPasswd"}
			, memEmail1:{required:true}
			, memEmail2:{required:true}
			, memHp1:{required:true}
			, memHp2:{required:true, digits:true, minlength:3, maxlength:4}
			, memHp3:{required:true, digits:true, minlength:4, maxlength:4}
		},
		messages: {
			passwordCnfirm:{equalTo: "비밀번호와 비밀번호 확인이 일치하지 않습니다."}		
		},	
		ignore: [],		
		submitHandler: function(form) {
			if(!form.memPasswd.value.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~,-])|([!,@,#,$,%,^,&,*,?,_,~,-].*[a-zA-Z0-9])/)) {
				alert("비밀번호는 영문(대소문자구분),숫자,특수문자(~!@#$%^&*()-_? 만 허용)를 혼용하여 6~12자를 입력해주세요.");
				form.memPasswd.focus();
				return false;
			}
			var command = $('input[name="command"]').val();
			var regnum = $('input[name="regnumcheck"]').val();
			var value;
			if(command == 'insert'){
				value = $('input[name="memgroups"]:checked').val();
			}else {
				value = '${writeMemberGeneral.memGroups}';
			}			 
			if( value == 'EMP' && regnum == 'N' ) {
				alert("제공인력 고유번호 중복확인을 진행해 주세요.");
				return false;
			}
			form.submit();
		}
	});		
});

function checkMemberId() {
	var memId = $('input[name="memId"]').val();
	if (!memId) {
		alert("사용하실 아이디를 입력해 주세요.");
		$('input[name="memId"]').focus();
		return false;
	}

	$.get("${REQUEST_URI}?act=checkMemberId", { memId: memId },
	function(result, status) {
		if (status == "success") {
			if (!result) {
				alert("<spring:message code='member.usedata' />");
			} else {
				alert("입력하신 아이디는 사용이 가능합니다.");
			}
		}
	});
}

function selectEmail(ele) { 
	var $ele = $(ele); 
	var $email2 = $('input[name=memEmail2]');
	if ($ele.val() == "1") { 
		$email2.attr('readonly', false); 
		$email2.val(''); 
	} else { 
		$email2.attr('readonly', true); 
		$email2.val($ele.val()); 
	} 
}
//이부분 검색하는거 기업회원일때만 할 수 있게 해야하고, 컬럼 추가해야함. vo에다가

function openDaumPostcode() {
	new daum.Postcode({
		oncomplete: function(data) {
			document.getElementById('memComZipcd').value = data.zonecode;
			document.getElementById('memComAddress1').value = data.address;
			document.getElementById('memComAddress2').focus();
		}
	}).open();
}

function checkMemGroups(){
	var command = document.getElementById('command').value;
	var comRegNum = document.getElementById('memComRegNum').value;
	var memGroups = '${writeMemberGeneral.memGroups}';
	if (memGroups == 'EMP'){
		document.getElementById('regnumcheck').value = 'Y';
		$(".layerG").show();
		$("#memComRegNum").rules("add", {required: true, digits:true, minlength:8, maxlength:8});
	}
}

function checkComRegNum() {
	var memComRegNum = $('input[name="memComRegNum"]').val();	
	var command = document.getElementById('command').value;
	var memGroups;
	if( command == 'insert' ){
		memGroups = $('input[name="memGroups"]:checked').val();
	}else{
		memGroups = '${writeMemberGeneral.memGroups}';
	}
	if (!memComRegNum) {
		alert("제공인력 고유번호를 입력해 주세요.");			
		$('input[name="memComRegNum"]').focus();
		return false;
	}
	
	if(memGroups == 'EMP' && memComRegNum.length != 8){
		alert("제공인력 고유번호는 8자리 입니다.");
		return false;
	}
	
	$.ajax({ 
		url: "?act=checkComRegNum", // 클라이언트가 HTTP 요청을 보낼 서버의 URL 주소 
		data: {memComRegNum: memComRegNum, memGroups:memGroups}, // HTTP 요청과 함께 서버로 보낼 데이터 
		method: "POST", // HTTP 요청 메소드(GET, POST 등) 
		dataType: "json" // 서버에서 보내줄 데이터의 타입 
	}) // HTTP 요청이 성공하면 요청한 데이터가 done() 메소드로 전달됨. 
	.done(function(json) {
		if(json){
			$('input[name="regnumcheck"]').val('Y');
			alert('입력하신 제공인력 고유번호는 사용이 가능합니다.');
			
		}else {
			$('input[name="regnumcheck"]').val('N');
			alert("<spring:message code='member.join.nouseregnum' />");
		}		
	}) // HTTP 요청이 실패하면 오류와 상태에 관한 정보가 fail() 메소드로 전달됨. 
	.fail(function(xhr, status, errorThrown) { 
		alert('에러가 발생하였습니다. 관리자에게 문의해 주세요. ' + status);			
	});
}
</script>