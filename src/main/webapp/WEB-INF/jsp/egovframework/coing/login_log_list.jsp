<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<div class="row">
	<div class="col-lg-12">
		<div class="block">
			<%@ include file="./include/tabmenu.jsp" %>
			<div class="block-head">
				<h4><c:out value="${ADMIN_MENU_VO.amePname}"/> 목록</h4>
			</div>
			<div class="block-content np">
				<table class="table table-hover table-bordered">
				<colgroup>
				</colgroup>
				<thead>
					<tr>
						<th scope="col" class="text-center">번호</th>						
						<th scope="col" class="text-center">이름</th>
						<th scope="col" class="text-center">로그인일시</th>
						<th scope="col" class="text-center">성공여부</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">
					<tr>
						<td class="text-center"><c:out value="${paginationInfo.totalRecordCount - (searchUserLoginLogVO.pageIndex-1) * searchUserLoginLogVO.pageSize - status.index}"/></td>
						<td class="text-center"><c:out value="${result.ullUserId}"/><br />(<c:out value="${result.ullUserNm}"/>)</td>
						<td class="text-center"><c:out value="${result.ullDttm}"/><br />(<c:out value="${result.ullIp}"/>)</td>
						<td class="text-center">
							<c:choose>
								<c:when test="${result.ullSuccessYn eq 'Y'}">성공</c:when>
								<c:otherwise>실패<br />(<c:out value="${result.ullMessage}"/>)</c:otherwise>
							</c:choose>
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
				<div class="text-center">
					<ul class="pagination pagination-sm">
					<ui:pagination paginationInfo="${paginationInfo}" type="default" jsFunction="${paginationQueryString}"/>
					</ul>	
				</div>				
			</div>
		</div>
	</div>
</div>