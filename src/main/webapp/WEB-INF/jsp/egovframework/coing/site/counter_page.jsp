<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>

<div class="row">
	<div class="col-lg-12">
		<div class="block">
			<ul class="nav nav-tabs">
				<li><a href="${REQUEST_URI}?sinId=<c:out value='${searchSiteCounterMenuVO.sinId}'/>">통계현황</a></li>
				<li><a href="${REQUEST_URI}?act=hour&amp;sinId=<c:out value='${searchSiteCounterMenuVO.sinId}'/>">시간대별</a></li>
			    <li><a href="${REQUEST_URI}?act=day&amp;sinId=<c:out value='${searchSiteCounterMenuVO.sinId}'/>">일별</a></li>
			    <li><a href="${REQUEST_URI}?act=month&amp;sinId=<c:out value='${searchSiteCounterMenuVO.sinId}'/>">월별</a></li>
			    <li><a href="${REQUEST_URI}?act=year&amp;sinId=<c:out value='${searchSiteCounterMenuVO.sinId}'/>">년도별</a></li>
			    <li class="active"><a href="#">페이지별</a></li>
			</ul>
			<div class="block-content">
				<div class="pull-left">
					<form:form commandName="searchSiteCounterMenuVO" action="${REQUEST_URI}?act=page" method="post" class="form-inline form-search" role="form">
						<fieldset>
						<legend class="sr-only">검색 폼</legend>
						<div class="form-group">
							<select name="sinId" id="sinId" class="form-control" onchange="this.form.submit();">
							<option value="">+ 사이트선택</option>
							<c:forEach var="result" items="${siteList}" varStatus="status">
							<option value="${result.sinId}" <c:if test="${searchSiteCounterMenuVO.sinId eq result.sinId}">selected="selected"</c:if>><c:out value="${result.sinName}" /></option>
							</c:forEach>
							</select>
						</div>							
						<div class="form-group">
							<input type="text" name="searchStartDt" id="searchStartDt" class="form-control datepicker" value="<c:out value='${searchSiteCounterMenuVO.searchStartDt}'/>" /> ~
						</div>
						<div class="input-group">
							<input type="text" name="searchEndDt" id="searchEndDt" class="form-control datepicker" value="<c:out value='${searchSiteCounterMenuVO.searchEndDt}'/>" />
							<span class="input-group-btn">
								<button type="submit" class="btn btn-default"><spring:message code="common.search" /></button>
							</span>							
						</div>
					</fieldset>
					</form:form>
				</div>
				 <%-- <div class="pull-right">
					<a href="?act=page&amp;mode=excel&amp;sineId=<c:out value='${searchSiteCounterMenuVO.sinId}'/>&amp;searchStartDt=<c:out value='${searchSiteCounterMenuVO.searchStartDt}'/>&amp;searchStartDt=<c:out value='${searchSiteCounterMenuVO.searchEndDt}'/>" class="btn btn-primary">엑셀다운로드</a>
				</div> --%>
			</div>
			<div class="block-content np">
				<table class="table">
					<colgroup>
						<col style="width:350px;" />
						<col />
						<col />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">페이지</th>
							<th scope="col">페이지뷰</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${resultList}" varStatus="status">
						<fmt_rt:formatNumber var="percentHits" value="${result.hits == 0 ? 0 : (result.hits / totalHits * 100)}" maxFractionDigits="1" />
						<tr>
							<td>
								<c:choose>
									<c:when test="${result.smeId eq 0}"><a href="/${result.sinId}/main.do" target="_blank">메인</a></c:when>
									<c:otherwise><a href="/${result.sinId}/sub.do?m=${result.smeId}" target="_blank">${result.menuPath}</a></c:otherwise>
								</c:choose>
							</td>
							<td>
								<div class="progress progress-small no-radius no-margin mg-b-sm">
									<div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${percentHits}" aria-valuemin="0" aria-valuemax="100" style="width:${percentHits}%"></div>
								</div>
								페이지뷰 : <fmt_rt:formatNumber value="${result.hits}" groupingUsed="true" /> / ${percentHits}%
							</td>
						</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td>전체</td>
							<td><fmt_rt:formatNumber value="${totalHits}" groupingUsed="true" /></td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</div>
</div>