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
				<form:form commandName="writeCodeDetail" action="?act=write" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}' />" />
					<input type="hidden" name="returnQueryString" value="<c:out value='${searchCodeDetailVO.queryString}' escapeXml='false' />"/>					
					<c:if test="${command eq 'update'}">
					<input type="hidden" name="codId" value="${searchCodeDetailVO.codId}"/>
					<input type="hidden" name="cddId" value="${searchCodeDetailVO.cddId}"/>
					</c:if>
					<fieldset>
						<legend class="sr-only">등록정보</legend>
						<div class="form-group">
							<span class="col-sm-2 text-right"><span class="required">*</span><label for="codId" class="control-label">코드ID</label></span>
							<div class="col-sm-10">
								<c:choose>
									<c:when test="${command eq 'insert'}">
										<form:select path="codId" class="form-control required">
										<c:forEach var="result" items="${codeListAll}" varStatus="status">
										<form:option value="${result.codId}"><c:out value="${result.codName}"/> [<c:out value="${result.codId}"/>]</form:option>
										</c:forEach>
										</form:select>
									</c:when>
									<c:otherwise>
										<form:input path="codId" class="form-control" disabled="true" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><span class="required">*</span><label for="cddId" class="control-label">상세코드ID</label></span>
							<div class="col-sm-10">
								<c:choose>
									<c:when test="${command eq 'insert'}">
										<form:input path="cddId" class="form-control required" maxlength="20" />
									</c:when>
									<c:otherwise>
										<form:input path="cddId" class="form-control" disabled="true" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>						
						<div class="form-group">
							<span class="col-sm-2 text-right"><span class="required">*</span><label for="cddName" class="control-label">상세코드명</label></span>
							<div class="col-sm-10">
								<form:input path="cddName" class="form-control required" maxlength="60" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="cddContent" class="control-label">설명</label></span>
							<div class="col-sm-10">
								<form:textarea path="cddContent" class="form-control" rows="6"/>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="cddData01" class="control-label">확장필드1</label></span>
							<div class="col-sm-10">
								<form:input path="cddData01" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="cddData02" class="control-label">확장필드2</label></span>
							<div class="col-sm-10">
								<form:input path="cddData02" class="form-control"/>
							</div>
						</div>		
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="cddData03" class="control-label">확장필드3</label></span>
							<div class="col-sm-10">
								<form:input path="cddData03" class="form-control"/>
							</div>
						</div>		
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="cddData04" class="control-label">확장필드4</label></span>
							<div class="col-sm-10">
								<form:input path="cddData04" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="cddData05" class="control-label">확장필드5</label></span>
							<div class="col-sm-10">
								<form:input path="cddData05" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="cddOrderNo" class="control-label">순서</label></span>
							<div class="col-sm-10">
								<form:input path="cddOrderNo" class="form-control digits" maxlength="3" />
							</div>
						</div>						
						<div class="form-group">
							<span class="col-sm-2  text-right"><label for="cddUseYn" class="control-label">사용유무</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="cddUseYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="cddUseYn" value="N"/>미사용</label>								
							</div>
						</div>
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<a href="?<c:out value='${searchCodeDetailVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list" /></a>
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
	$("#writeCodeDetail").validate({
		rules: {
			cddId:{remote:{url:"${REQUEST_URI}?act=check", data:{codId:function(){return $('#codId').val();}}}}
		},
		messages: {
			cddId:{remote : "<spring:message code='code.detail.usedata'/>"}
		}
	});
})
</script>