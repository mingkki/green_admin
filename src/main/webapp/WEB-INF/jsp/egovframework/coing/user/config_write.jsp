<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ckeditor" uri="http://ckeditor.com" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">
	<div class="col-lg-12">
		<div class="block">
			<%@ include file="../include/tabmenu.jsp" %>
			<div class="block-head">
				<h1>
					<c:out value="${ADMIN_MENU_VO.amePname}"/> 
					<c:choose>
						<c:when test="${command == 'insert'}"><spring:message code="common.insert" /></c:when>
						<c:otherwise><spring:message code="common.update" /></c:otherwise>
					</c:choose>
				</h1>
			</div>			
			<div class="block-content">
				<form:form commandName="writeUserConfig" action="${REQUEST_URI}" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}' />" />
					<fieldset>
						<legend class="sr-only">등록정보</legend>							
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="ucfJoinYn" class="control-label">회원가입 작동상태</label></span>
							<div class="col-sm-10">
								<form:select path="ucfJoinYn" class="form-control">
								<form:option value="Y">작동</form:option>
								<form:option value="N">중단</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="ucfJoinLevel" class="control-label">회원가입시 등급</label></span>
							<div class="col-sm-10">
								<form:select path="ucfJoinLevel" class="form-control">
								<c:forEach var="result" items="${levelList}" varStatus="status">
								<form:option value="${result.lvlId}">[<c:out value="${result.lvlId}"/>] <c:out value="${result.lvlName}"/></form:option>
								</c:forEach>								
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="ucfJoinConfirmYn" class="control-label">회원가입 승인여부</label></span>
							<div class="col-sm-10">
								<form:select path="ucfJoinConfirmYn" class="form-control">
								<form:option value="N">즉시승인</form:option>
								<form:option value="Y">관리자 확인 후 승인</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="ucfJoinGubunYn" class="control-label">회원가입 구분여부</label></span>
							<div class="col-sm-10">
								<form:select path="ucfJoinGubunYn" class="form-control">
								<form:option value="Y">14세이상, 14세미만, 외국인 구분</form:option>
								<form:option value="N">구분안함</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="ucfJoinAgreeYn" class="control-label">회원가입 동의얻기 여부</label></span>
							<div class="col-sm-10">
								<form:select path="ucfJoinAgreeYn" class="form-control">
								<form:option value="Y">이용약관, 개인정보 수집안내 동의 얻기</form:option>
								<form:option value="N">생략</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="ucfJoinRealnameYn" class="control-label">회원가입시 본인확인 여부</label></span>
							<div class="col-sm-10">
								<form:select path="ucfJoinRealnameYn" class="form-control">
								<form:option value="Y">본인확인함</form:option>
								<form:option value="N">생략</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="ucfFindRealnameYn" class="control-label">비밀번호찾기시 본인확인 여부</label></span>
							<div class="col-sm-10">
								<form:select path="ucfFindRealnameYn" class="form-control">
								<form:option value="Y">본인확인함</form:option>
								<form:option value="N">생략</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="ucfJoinPrivacy1" class="control-label">개인정보 수집 및 이용에 대한 안내</label></span>
							<div class="col-sm-10">
								<form:textarea path="ucfJoinPrivacy1" class="form-control" rows="7" />
								<ckeditor:replace replace="ucfJoinPrivacy1" basePath="${pageContext.request.contextPath}/assets/ckeditor/" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="ucfJoinPrivacy2" class="control-label">개인정보(선택정보)수집 및 이용에 대한 안내</label></span>
							<div class="col-sm-10">
								<form:textarea path="ucfJoinPrivacy2" class="form-control" rows="7" />
								<ckeditor:replace replace="ucfJoinPrivacy2" basePath="${pageContext.request.contextPath}/assets/ckeditor/" />
							</div>
						</div>
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
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
	$("#writeUserConfig").validate();
});
</script>
