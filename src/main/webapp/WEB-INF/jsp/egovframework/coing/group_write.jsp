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
				<form:form commandName="writeGroup" action="?act=write" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}' />" />
					<c:if test="${command eq 'update'}">
					<input type="hidden" name="grpId" value="${searchGroupVO.grpId}"/>
					</c:if>
					<fieldset>
						<legend class="sr-only">등록정보</legend>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="grpId" class="control-label">* 등급</label></span>
							<div class="col-sm-10">
								<c:choose>
									<c:when test="${command == 'insert'}">
										<form:input path="grpId" class="form-control required" maxlength="3" />
									</c:when>
									<c:otherwise>
										<form:input path="grpId" class="form-control" disabled="true" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="grpName" class="control-label">* 등급명</label></span>
							<div class="col-sm-10">
								<form:input path="grpName" class="form-control required" maxlength="60" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="grpContent" class="control-label">설명</label></span>
							<div class="col-sm-10">
								<form:input path="grpContent" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="grpContent" class="control-label">정렬순서</label></span>
							<div class="col-sm-10">
								<form:input path="grpOrderNo" class="form-control" />
							</div>
						</div>						
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<a href="${REQUEST_URI}" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list" /></a>
							</div>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</div>