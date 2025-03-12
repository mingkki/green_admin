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
				<li class="active"><a href="#">접속통계</a></li>
				<li><a href="${REQUEST_URI}?act=hour&amp;sinId=<c:out value='${sinId}'/>">시간대별</a></li>
				<li><a href="${REQUEST_URI}?act=day&amp;sinId=<c:out value='${sinId}'/>">일별</a></li>
				<li><a href="${REQUEST_URI}?act=week&amp;sinId=<c:out value='${sinId}'/>">요일별</a></li>
				<li><a href="${REQUEST_URI}?act=month&amp;sinId=<c:out value='${sinId}'/>">월별</a></li>
				<li><a href="${REQUEST_URI}?act=year&amp;sinId=<c:out value='${sinId}'/>">년도별</a></li>
				<li><a href="${REQUEST_URI}?act=page&amp;sinId=<c:out value='${sinId}'/>">페이지별</a></li>
			</ul>		
			<div class="block-head">
				<h4>접속통계</h4>
			</div>
			<div class="block-head pd-xs">
				<form action="${REQUEST_URI}" method="get" class="form-search" role="form">
					<fieldset>
						<legend class="sr-only">검색 폼</legend>			
						<div class="form-group">
							<select name="sinId" id="sinId" class="form-control" onchange="this.form.submit();">
							<option value="">+ 사이트선택</option>
							<c:forEach var="result" items="${siteList}" varStatus="status">
							<option value="${result.sinId}" <c:if test="${sinId eq result.sinId}">selected="selected"</c:if>><c:out value="${result.sinName}" /></option>
							</c:forEach>
							</select>
						</div>			
					</fieldset>
				</form>
			</div>
			<div class="block-content">
				<div>
					전체통계<br/>
					방문자수 : <fmt_rt:formatNumber value="${totalVO.scdUniqHits}" groupingUsed="true" /><br />
					페이지뷰 : <fmt_rt:formatNumber value="${totalVO.scdHits}" groupingUsed="true" />
				</div>
				--------------------------------------------------------------------------------------------
				<div>
					어제 통계(<c:out value="${yesterday}"/>)<br/>
					방문자수 : <fmt_rt:formatNumber value="${yesterdayVO.scdUniqHits}" groupingUsed="true" /><br />
					페이지뷰 : <fmt_rt:formatNumber value="${yesterdayVO.scdHits}" groupingUsed="true" />
				</div>			
				--------------------------------------------------------------------------------------------
				<div>
					오늘 통계(<c:out value="${today}"/>)<br/>
					방문자수 : <fmt_rt:formatNumber value="${todayVO.scdUniqHits}" groupingUsed="true" /><br />
					페이지뷰 : <fmt_rt:formatNumber value="${todayVO.scdHits}" groupingUsed="true" />
				</div>	
				--------------------------------------------------------------------------------------------
				<div>
					최근 1주일 통계(<c:out value="${weekAgo}"/> ~ <c:out value="${yesterday}"/>)<br/>
					방문자수 : <fmt_rt:formatNumber value="${weekVO.scdUniqHits}" groupingUsed="true" /><br />
					페이지뷰 : <fmt_rt:formatNumber value="${weekVO.scdHits}" groupingUsed="true" />
				</div>
				--------------------------------------------------------------------------------------------
				<div>
					최근 1개월 통계(<c:out value="${monthAgo}"/> ~ <c:out value="${yesterday}"/>)<br/>
					방문자수 : <fmt_rt:formatNumber value="${monthVO.scdUniqHits}" groupingUsed="true" /><br />
					페이지뷰 : <fmt_rt:formatNumber value="${monthVO.scdHits}" groupingUsed="true" />
				</div>
				--------------------------------------------------------------------------------------------
				<div>
					최근 1년 통계(<c:out value="${yearAgo}"/> ~ <c:out value="${yesterday}"/>)<br/>
					방문자수 : <fmt_rt:formatNumber value="${yearVO.scdUniqHits}" groupingUsed="true" /><br />
					페이지뷰 : <fmt_rt:formatNumber value="${yearVO.scdHits}" groupingUsed="true" />
				</div>
			</div>
		</div>
	</div>
</div>