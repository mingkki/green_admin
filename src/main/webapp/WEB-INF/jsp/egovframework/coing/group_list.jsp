<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="row">
	<div class="col-lg-12">
		<div class="block">
			<div class="block-head">
				<h4><c:out value="${ADMIN_MENU_VO.amePname}"/> 목록</h4>
			</div>
			<div class="block-content np">
				<table class="table table-hover table-bordered">
				<colgroup>
				</colgroup>
				<thead>
					<tr>
						<th scope="col" class="text-center">그룹</th>
						<th scope="col" class="text-center">그룹명</th>
						<th scope="col" class="text-center">설명</th>
						<th scope="col" class="text-center">등록일</th>
						<th scope="col" class="text-center">관리</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">
					<tr>
						<td class="text-center"><c:out value="${result.grpId}"/></td>
						<td class="text-center"><c:out value="${result.grpName}"/></td>
						<td class="text-center"><c:out value="${result.grpContent}"/></td>
						<td class="text-center"><c:out value="${fn:substring(result.grpRegDttm,0,10)}"/></td>
						<td class="text-center">
							<c:if test="${result.systemYn ne 'Y'}">
								<a href="?act=write&amp;grpId=<c:out value='${result.grpId}'/>" class="btn btn-default btn-xs">수정</a>
								<a href="javascript:post_delete('', 'act=delete&grpId=<c:out value="${result.grpId}"/>');" class="btn btn-default btn-xs">삭제</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(resultList) == 0}">
					<tr>
						<td colspan="5" class="text-center"><spring:message code="common.nodata" /></td>
					</tr>
				</c:if>
				</tbody>
				</table>
			</div>
			<div class="block-footer">				
				<div class="clearfix">
					<div class="pull-left">
					</div>
					<div class="pull-right">
						<a href="?act=write" class="btn btn-primary">등록</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>