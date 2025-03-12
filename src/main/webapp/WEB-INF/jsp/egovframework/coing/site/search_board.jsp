<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="row">
	<div class="col-lg-12">
		<div class="block">
			<div class="block-head">
				<h1>게시판 목록</h1>
			</div>
			<div class="block-content">
				<div class="pull-left">
					전체 <strong><c:out value="${resultCnt}"/></strong>건
				</div>
				<div class="pull-right">
				</div>
			</div>
			<div class="block-content np">
				<table class="table table-hover table-bordered">
				<colgroup>
				</colgroup>
				<thead>
					<tr>
						<th scope="col" class="text-center">게시판ID</th>
						<th scope="col" class="text-center">게시판명</th>
						<th scope="col" class="text-center">선택</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">
					<tr>
						<td class="text-center"><c:out value="${result.brdId}"/></td>
						<td class="text-center"><c:out value="${result.brdName}"/></td>
						<td class="text-center">
							<button type="button" onclick="selectBoard('<c:out value="${result.brdId}"/>','<c:out value="${result.brdName}"/>'); return false;" class="btn btn-default btn-xs">선택</button>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(resultList) == 0}">
					<tr>
						<td colspan="4" class="text-center"><spring:message code="common.nodata" /></td>
					</tr>
				</c:if>
				</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
function selectBoard(brdId, brdName)
{
	var a = {
		"brdId" : brdId,
		"brdName" : brdName
	}
	
	window.opener.completeSearchBoard(a);
	
	self.close();
}
</script>