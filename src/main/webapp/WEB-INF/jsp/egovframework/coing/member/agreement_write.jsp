<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ckeditor" uri="http://ckeditor.com" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">
	<div class="col-lg-12">
		<div class="block">
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
				<form:form commandName="writeMemberAgreement" action="?act=write" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}' />" />
					<input type="hidden" name="returnQueryString" value="<c:out value='${searchMemberAgreementVO.queryString}' escapeXml='false' />"/>
					<input type="hidden" name="magId" value="${searchMemberAgreementVO.magId}"/>
					<fieldset>
						<legend class="sr-only">등록정보</legend>							
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="magChangeDt" class="control-label">* 등록(변경)일자</label></span>
							<div class="col-sm-10">
								<form:input path="magChangeDt" class="form-control datepicker required" />
							</div>
						</div>						
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="magContent" class="control-label">내용</label></span>
							<div class="col-sm-10">
								<form:textarea path="magContent" class="form-control" rows="7" />
								<ckeditor:replace replace="magContent" basePath="${pageContext.request.contextPath}/assets/ckeditor/" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="magMemo" class="control-label">변경사항</label></span>
							<div class="col-sm-10">
								<form:input path="magMemo" class="form-control" />
								<div class="help-block">
								변경사항을 간략히 작성해 주세요. 예) Ⅶ. 권익침해 구제 방법 추가
								</div>
							</div>
						</div>						
						<div class="form-group">
							<span class="col-sm-2  text-right"><label for="magUseYn" class="control-label">사용유무</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="magUseYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="magUseYn" value="N"/>미사용</label>								
							</div>
						</div>
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<a href="?<c:out value='${searchMemberAgreementVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list" /></a>
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
	$("#writeMemberAgreement").validate();
});
</script>
