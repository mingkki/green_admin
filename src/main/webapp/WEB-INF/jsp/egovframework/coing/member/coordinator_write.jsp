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
				<form:form commandName="writeMemberCoordinator" action="?act=write" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}' />" />
					<input type="hidden" name="returnQueryString" value="<c:out value='${searchMemberCoordinatorVO.queryString}' escapeXml='false' />"/>
					<c:if test="${command eq 'update'}">
					<input type="hidden" name="mcoId" value="${searchMemberCoordinatorVO.mcoId}"/>
					</c:if>
					<fieldset>
						<legend class="sr-only">등록정보</legend>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mcoId" class="control-label">* 아이디</label></span>
							<div class="col-sm-10">
								<c:choose>
									<c:when test="${command eq 'insert'}">
										<form:input path="mcoId" class="form-control required" maxlength="20" />
										<div class="help-block">영문자와 숫자로 4자 이상 20자 이하로 입력하세요.</div>
									</c:when>
									<c:otherwise>
										<form:input path="mcoId" class="form-control" disabled="true" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<c:if test="${command eq 'insert'}">
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mcoPasswd" class="control-label">* 비밀번호</label></span>
							<div class="col-sm-10">
								<form:password path="mcoPasswd" class="form-control required" />
								<div class="help-block">영문자와 숫자로 4자 이상 12자 이하로 입력하세요.</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mcoPasswdConfirm" class="control-label">* 비밀번호 확인</label></span>
							<div class="col-sm-10">
								<input type="password" name="mcoPasswdConfirm" id="mcoPasswdConfirm" class="form-control required" value="" />
							</div>
						</div>
						</c:if>
						<c:if test="${command eq 'update'}">
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mcoPasswd" class="control-label">비밀번호변경</label></span>
							<div class="col-sm-10">
								<form:password path="mcoPasswd" class="form-control" />
								<div class="help-block">영문자와 숫자로 4자 이상 20자 이하로 입력하세요.</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mcoPasswdConfirm" class="control-label">비밀번호변경 확인</label></span>
							<div class="col-sm-10">
								<input type="password" name="mcoPasswdConfirm" id="mcoPasswdConfirm" class="form-control" value="" />
							</div>
						</div>
						</c:if>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mcoName" class="control-label">* 이름</label></span>
							<div class="col-sm-10">
								<form:input path="mcoName" class="form-control required" maxlength="60" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mcoArea" class="control-label">* 담당지역</label></span>
							<div class="col-sm-10">
								<form:select path="mcoArea" class="form-control">
								<form:option value="">+ 선택</form:option>
								<c:forEach var="result" items="${areaList}" varStatus="status">
								<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
						</div>							
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mcoEmail" class="control-label">이메일</label></span>
							<div class="col-sm-2">							
								<form:input path="mcoEmail1" class="form-control" />
							</div>
							<div class="col-sm-2">
								<form:input path="mcoEmail2" class="form-control" />
							</div>
							<div class="col-sm-2">
								<form:select path="mcoEmail3" class="form-control" onchange="selectEmail(this);">
								<form:option value="">+ 선택</form:option>
								<c:forEach var="result" items="${emailList}" varStatus="status">
								<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
								</c:forEach>
								<form:option value="1">직접입력</form:option>
								</form:select>
							</div>								
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mcoTel" class="control-label">전화번호</label></span>
							<div class="col-sm-2">
								<form:select path="mcoTel1" class="form-control">
								<form:option value="">+ 선택</form:option>
								<c:forEach var="result" items="${telList}" varStatus="status">
								<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
							<div class="col-sm-2">
								<form:input path="mcoTel2" class="form-control" maxlength="4"/>
							</div>
							<div class="col-sm-2">
								<form:input path="mcoTel3" class="form-control" maxlength="4"/>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mcoHp" class="control-label">휴대폰번호</label></span>
							<div class="col-sm-2">
								<form:select path="mcoHp1" class="form-control">
								<form:option value="">+ 선택</form:option>
								<c:forEach var="result" items="${hpList}" varStatus="status">
								<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
							<div class="col-sm-2">
								<form:input path="mcoHp2" class="form-control" maxlength="4"/>
							</div>
							<div class="col-sm-2">
								<form:input path="mcoHp3" class="form-control" maxlength="4"/>
							</div>
						</div>	
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mcoMemo" class="control-label">관리자메모</label></span>
							<div class="col-sm-10">							
								<form:textarea path="mcoMemo" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="mcoLevel" class="control-label">* 등급</label></span>
							<div class="col-sm-2">
								<form:select path="mcoLevel" class="form-control">
								<c:forEach var="result" items="${levelList}" varStatus="status">
								<form:option value="${result.lvlId}"><c:out value="${result.lvlName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
							<span class="col-sm-2 text-right"><label class="control-label">그룹</label></span>
							<div class="col-sm-6">
								<c:forEach var="result" items="${groupList}" varStatus="status">
								<label class="checkbox-inline">
									<form:checkbox path="mcoGroupArr" value="${result.grpId}"/><c:out value="${result.grpName}"/>
								</label>
								</c:forEach>
							</div>
						</div>
						<c:if test="${command eq 'update'}">
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">등록정보</label></span>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:out value="${writeMemberCoordinator.mcoRegDttm}"/>
									/ <c:out value="${writeMemberCoordinator.mcoRegIp}"/>
									/ 등록ID : <c:out value="${writeMemberCoordinator.mcoRegId}"/>
								</p>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">비밀번호 변경정보</label></span>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:out value="${writeMemberCoordinator.mcoPwchangeDttm}"/>
									/ <c:out value="${writeMemberCoordinator.mcoPwchangeIp}"/>
								</p>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">최근 정보변경</label></span>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:out value="${writeMemberCoordinator.mcoUpdtDttm}"/>
									/ <c:out value="${writeMemberCoordinator.mcoUpdtIp}"/>
									/ 수정ID : <c:out value="${writeMemberCoordinator.mcoUpdtId}"/>
								</p>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">최근 로그인</label></span>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:out value="${writeMemberCoordinator.mcoLastloginDttm}"/>
									/ <c:out value="${writeMemberCoordinator.mcoLastloginIp}"/>
									/ <c:out value="${writeMemberCoordinator.mcoLoginCnt}"/>회
								</p>
							</div>
						</div>
						</c:if>
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<a href="?<c:out value='${searchMemberCoordinatorVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list" /></a>
							</div>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(function()
{
	$("#writeMemberCoordinator").validate();
});
</script>

<script type="text/javascript">
function selectEmail(ele) { 
	var $ele = $(ele); 
	var $email2 = $('input[name=mcoEmail2]');
	if ($ele.val() == "1") { 
		$email2.attr('readonly', false); 
		$email2.val(''); 
	} else { 
		$email2.attr('readonly', true); 
		$email2.val($ele.val()); 
	} 
}
</script>