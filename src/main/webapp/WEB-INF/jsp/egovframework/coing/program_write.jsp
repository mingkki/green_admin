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
				<form:form commandName="writeProgram" action="?act=write" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}' />" />
					<input type="hidden" name="returnQueryString" value="<c:out value='${searchProgramVO.queryString}' escapeXml='false' />"/>
					<input type="hidden" name="prgId" value="${searchProgramVO.prgId}"/>
					<fieldset>
						<legend class="sr-only">등록정보</legend>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="prgName" class="control-label">* 프로그램명</label></span>
							<div class="col-sm-10">
								<form:input path="prgName" class="form-control required" maxlength="120" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="prgUrl" class="control-label">url</label></span>
							<div class="col-sm-10">
								<form:input path="prgUrl" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="prgOrderNo" class="control-label">순서</label></span>
							<div class="col-sm-10">
								<form:input path="prgOrderNo" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2  text-right"><label for="prgUseYn" class="control-label">사용유무</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="prgUseYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="prgUseYn" value="N"/>미사용</label>								
							</div>
						</div>
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<a href="?<c:out value='${searchProgramVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list" /></a>
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
	$("#writeProgram").validate();
});
</script>