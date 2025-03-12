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
				<li><a href="${REQUEST_URI}?sinId=<c:out value='${searchSiteCounterDayVO.sinId}'/>">접속통계</a></li>
				<li><a href="${REQUEST_URI}?act=hour&amp;sinId=<c:out value='${searchSiteCounterDayVO.sinId}'/>">시간대별</a></li>
				<li><a href="${REQUEST_URI}?act=day&amp;sinId=<c:out value='${searchSiteCounterDayVO.sinId}'/>">일별</a></li>
				<li class="active"><a href="#">요일별</a></li>
				<li><a href="${REQUEST_URI}?act=month&amp;sinId=<c:out value='${searchSiteCounterDayVO.sinId}'/>">월별</a></li>
				<li><a href="${REQUEST_URI}?act=year&amp;sinId=<c:out value='${searchSiteCounterDayVO.sinId}'/>">년도별</a></li>
				<li><a href="${REQUEST_URI}?act=page&amp;sinId=<c:out value='${searchSiteCounterDayVO.sinId}'/>">페이지별</a></li>
			</ul>	
			<div class="block-head">
				<h4>요일별</h4>
			</div>
			<div class="block-head pd-xs">
				<form action="${REQUEST_URI}?act=week" method="post" class="form-inline">
					<fieldset>
						<legend class="sr-only">검색 폼</legend>
						<div class="form-group">
							<select name="sinId" id="sinId" class="form-control" onchange="this.form.submit();">
							<option value="">+ 사이트선택</option>
							<c:forEach var="result" items="${siteList}" varStatus="status">
							<option value="${result.sinId}" <c:if test="${searchSiteCounterDayVO.sinId eq result.sinId}">selected="selected"</c:if>><c:out value="${result.sinName}" /></option>
							</c:forEach>
							</select>
						</div>							
						<div class="form-group">
							<input type="text" name="searchStartDt" id="searchStartDt" class="form-control datepicker" value="<c:out value='${searchSiteCounterDayVO.searchStartDt}'/>" /> ~
						</div>
						<div class="input-group">
							<input type="text" name="searchEndDt" id="searchEndDt" class="form-control datepicker" value="<c:out value='${searchSiteCounterDayVO.searchEndDt}'/>" />
							<span class="input-group-btn">
								<button type="submit" class="btn btn-default"><spring:message code="common.search" /></button>
							</span>							
						</div>
					</fieldset>					
				</form>
			</div>
			<div class="block-content">
				<table class="table">
					<thead>
						<tr>
							<th scope="col">요일</th>
							<th scope="col">페이지 접속</th>
							<th scope="col">방문자</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${resultList}" varStatus="status">
						<fmt_rt:formatNumber var="percentHits" value="${result.scdHits == 0 ? 0 : (result.scdHits / sumHits * 100)}" maxFractionDigits="1" />
						<fmt_rt:formatNumber var="percentUniqHits" value="${result.scdUniqHits == 0 ? 0 : (result.scdUniqHits / sumUniqHits * 100)}" maxFractionDigits="1" />						
						<tr>
							<td>
								<c:choose>
									<c:when test="${result.scdWw == 1}">일요일</c:when>
									<c:when test="${result.scdWw == 2}">월요일</c:when>
									<c:when test="${result.scdWw == 3}">화요일</c:when>
									<c:when test="${result.scdWw == 4}">수요일</c:when>
									<c:when test="${result.scdWw == 5}">목요일</c:when>
									<c:when test="${result.scdWw == 6}">금요일</c:when>
									<c:when test="${result.scdWw == 7}">토요일</c:when>
								</c:choose>
							</td>
							<td>
								<div class="progress progress-small no-radius no-margin mg-b-sm">
									<div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${percentHits}" aria-valuemin="0" aria-valuemax="100" style="width:${percentHits}%"></div>
								</div>							
								페이지뷰 : <fmt_rt:formatNumber value="${result.scdHits}" groupingUsed="true" /> / ${percentHits}%
							</td>
							<td>
								<div class="progress progress-small no-radius no-margin mg-b-sm">
									<div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${percentUniqHits}" aria-valuemin="0" aria-valuemax="100" style="width:${percentUniqHits}%"></div>
								</div>
								방문자 : <fmt_rt:formatNumber value="${result.scdUniqHits}" groupingUsed="true" /> / ${percentUniqHits}%
							</td>
						</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>	
							<td>전체</td>
							<td><fmt_rt:formatNumber value="${sumHits}" groupingUsed="true" /></td>
							<td><fmt_rt:formatNumber value="${sumUniqHits}" groupingUsed="true" /></td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</div>
</div>