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
				<form:form commandName="writeCode" action="?act=write" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}' />" />
					<input type="hidden" name="returnQueryString" value="<c:out value='${searchCodeVO.queryString}' escapeXml='false' />"/>
					<c:if test="${command eq 'update'}">
					<input type="hidden" name="codId" value="${searchCodeVO.codId}"/>
					</c:if>
					<fieldset>
						<legend class="sr-only">등록정보</legend>
						<div class="form-group">
							<span class="col-sm-2 text-right"><span class="required">*</span><label for="codId" class="control-label">코드ID</label></span>
							<div class="col-sm-10">
								<c:choose>
									<c:when test="${command eq 'insert'}">
										<form:input path="codId" class="form-control required" maxlength="12" />
									</c:when>
									<c:otherwise>
										<form:input path="codId" class="form-control" disabled="true" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><span class="required">*</span><label for="codName" class="control-label">코드명</label></span>
							<div class="col-sm-10">
								<form:input path="codName" class="form-control required" maxlength="60" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="codContent" class="control-label">코드설명</label></span>
							<div class="col-sm-10">
								<form:input path="codContent" class="form-control" maxlength="200" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2  text-right"><label for="codUseYn" class="control-label">사용유무</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="codUseYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="codUseYn" value="N"/>미사용</label>								
							</div>
						</div>
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<a href="?<c:out value='${searchCodeVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list" /></a>
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
	$("#writeCode").validate({
		rules: {
			codId:{minlength:3, remote:"${REQUEST_URI}?act=check"}
		},
		messages: {
			codId:{remote : "<spring:message code='code.usedata'/>"}
		}
	});
})
</script>