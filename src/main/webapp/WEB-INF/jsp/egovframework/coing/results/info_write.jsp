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
				<form:form commandName="writeResultsInfo" action="?act=write" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}' />" />
					<input type="hidden" name="returnQueryString" value="<c:out value='${searchResultsInfoVO.queryString}' escapeXml='false' />"/>
					<input type="hidden" name="rsinId" value="<c:out value='${searchResultsInfoVO.rsinId}'/>"/>
					<fieldset>
						<legend class="sr-only">설문 관리 폼</legend>	
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="rsinItemId" class="control-label">* 품목</label></span>
							<div class="col-sm-2">
								<form:select path="rsinItemId" class="form-control">
									<form:option value="">+ 선택</form:option>
									<c:forEach var="result" items="${itemList}" varStatus="status">
										<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="rsinCompanyId" class="control-label">* 업체명</label></span>
							<div class="col-sm-2">
								<form:select path="rsinCompanyId" class="form-control">
									<form:option value="">+ 선택</form:option>
									<c:forEach var="result" items="${companyList}" varStatus="status">
										<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="rsinName" class="control-label">* 항목</label></span>
							<div class="col-sm-10">
								<form:input path="rsinName" maxlength="60" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="rsinModel" class="control-label">* 모델/서비스</label></span>
							<div class="col-sm-10">
								<form:input path="rsinModel" maxlength="255" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="rsinCount" class="control-label">수량</label></span>
							<div class="col-sm-10">
								<form:input type="number" path="rsinCount" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="rsinMoney" class="control-label">단가</label></span>
							<div class="col-sm-10">
								<form:input type="number" path="rsinMoney" class="form-control"/>
							</div>
						</div>
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<a href="?<c:out value='${searchResultsInfoVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list" /></a>
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
	$("#writeResultsInfo").validate();
});
</script>