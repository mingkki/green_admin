<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<div style="padding:15px;">
<div class="block">
	<div class="block-head">
		<div class="pull-left">
			<h1><c:out value="${surveyInfoVO.svinTitle}"/></h1>
		</div>
		<div class="pull-right pd-r-xs pd-t-xs">
			<a href="?act=qwrite&amp;svinId=<c:out value='${surveyInfoVO.svinId}'/>" class="btn btn-primary btn-sm"><spring:message code="common.insert"/></a>
		</div>
	</div>
</div>
<form:form commandName="writeSurveyQuestion" action="?act=qwrite" class="form-horizontal" role="form">

	<c:forEach var="result" items="${surveyQuestionList}" varStatus="status">
	<div class="block">
		<div class="block-head">
			<h2><c:out value="${result.svquTitle}"/></h2>
		</div>
		<div class="block-content">
			<c:if test="${result.svquType eq 'B' || result.svquType eq 'E'}">
				<div><c:out value='${result.svquMinchkNum}'/>개 이상 선택해 주세요.</div>
			</c:if>
			<div>
				<c:if test="${result.svquType eq 'C'}">
					<textarea name="remarks[<c:out value='${result.svquId}' />]" class="form-control"></textarea>
				</c:if>
				<c:if test="${result.svquType ne 'C'}">
					<ul style="margin:0; padding:0;">
						<c:forEach items="${result.exampleList}" var="example" varStatus="k">
						<li style="list-style:none;">
							<c:if test="${result.svquType eq 'A' || result.svquType eq 'D'}"><input type="radio" name="questions[<c:out value='${result.svquId}' />]" value="<c:out value='${example.svexId}' />" /></c:if>
							<c:if test="${result.svquType eq 'B' || result.svquType eq 'E'}"><input type="checkbox" name="questions[<c:out value='${result.svquId}' />]" value="<c:out value='${example.svexId}' />" /></c:if>
							<c:out value='${example.svexTitle}' />
											
							<c:if test="${result.svquType eq 'D' || result.svquType eq 'E'}">
								<c:if test="${(k.index + 1) == fn:length(result.exampleList)}">
								<textarea name="remarks[<c:out value='${result.svquId}' />]" class="form-control"></textarea>
								</c:if>
							</c:if>					
						</li>
						</c:forEach>
					</ul>			
				</c:if>
			</div>
		</div>
		<div class="block-footer">
			<a href="?act=qwrite&amp;svinId=<c:out value='${result.svinId}'/>&amp;svquId=<c:out value='${result.svquId}'/>" class="btn btn-default btn-xs">수정</a>
			<%-- <a href="#none" onclick="post_delete('', 'act=qdelete&amp;svinId=<c:out value='${result.svinId}'/>&amp;svquId=<c:out value='${result.svquId}'/>'); return false;" class="btn btn-default btn-xs">삭제</a> --%>
			<a href="javascript:post_delete('', 'act=qdelete&amp;svinId=<c:out value="${result.svinId}"/>&amp;svquId=<c:out value="${result.svquId}"/>');" class="btn btn-default btn-xs">삭제</a>
			<%-- <a href="javascript:post_delete('', 'act=qelete&svinId=<c:out value="${result.svinId}"/>&<c:out value="${searchSurveyQuestionVO.queryString}"/>');" class="btn btn-default btn-xs">삭제</a><!--  --> --%>
		</div>
	</div>
	</c:forEach>
</form:form>
</div>