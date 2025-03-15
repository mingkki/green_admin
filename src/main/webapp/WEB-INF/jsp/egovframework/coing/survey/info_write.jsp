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
				<form:form commandName="writeSurveyInfo" action="?act=write" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}' />" />
					<input type="hidden" name="returnQueryString" value="<c:out value='${searchSurveyInfoVO.queryString}' escapeXml='false' />"/>					
					<input type="hidden" name="svinId" value="<c:out value='${searchSurveyInfoVO.svinId}'/>"/>
					<fieldset>
						<legend class="sr-only">설문 관리 폼</legend>	
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="svinTitle" class="control-label">* 설문제목</label></span>
							<div class="col-sm-10">
								<form:input path="svinTitle" maxlength="60" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="svinStartDt" class="control-label">* 설문기간</label></span>
							<div class="col-sm-10">
								<div class="input-group">
									<span class="input-group-addon">시작일</span>
									<form:input path="svinStartDt" maxlength="10" class="form-control datepicker required date" />
								</div>
								<div class="input-group mg-t-xs">
									<span class="input-group-addon">종료일</span>
									<form:input path="svinEndDt" maxlength="10" class="form-control datepicker required date" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="svinContent" class="control-label">* 설문내용</label></span>
							<div class="col-sm-10">
								<form:textarea path="svinContent" id="svinContent" rows="10" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">공개여부</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="svinOpenYn" value="Y" />공개</label>
								<label class="radio-inline"><form:radiobutton path="svinOpenYn" value="N" />비공개 (회원만 이용가능합니다.)</label>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">중복선택</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="svinDplctnYn" value="Y" />허용</label>
								<label class="radio-inline"><form:radiobutton path="svinDplctnYn" value="N" />허용안함</label>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">결과공개여부</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="svinResopenYn" value="Y" />공개</label>
								<label class="radio-inline"><form:radiobutton path="svinResopenYn" value="N" />비공개</label>
							</div>
						</div>						
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">사용여부</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="svinUseYn" value="Y" />사용함</label>
								<label class="radio-inline"><form:radiobutton path="svinUseYn" value="N" />사용안함</label>
							</div>
						</div>				
						<div class="bd-t mg-t-md pd-t-md"></div>	
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<a href="?<c:out value='${searchSurveyInfoVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list" /></a>
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
	$("#writeSurveyInfo").validate();
});
</script>