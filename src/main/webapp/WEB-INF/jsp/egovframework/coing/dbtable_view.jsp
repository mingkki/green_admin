<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>

				<table class="table">
					<thead>
						<tr>
							<th scope="col" class="text-center">Field</th>
							<th scope="col" class="text-center">Type</th>
							<th scope="col" class="text-center">Collation</th>
							<th scope="col" class="text-center">Null</th>
							<th scope="col" class="text-center">Key</th>
							<th scope="col" class="text-center">Default</th>
							<th scope="col" class="text-center">Extra</th>
							<th scope="col" class="text-center">Comment</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="result" items="${dbTableColumnList}" varStatus="status">
						<tr>
							<td class="text-center"><c:out value="${result.field}"/></td>
							<td class="text-center"><c:out value="${result.type}"/></td>
							<td class="text-center"><c:out value="${result.collation}"/></td>
							<td class="text-center"><c:out value="${result.nullYn}"/></td>
							<td class="text-center"><c:out value="${result.key}"/></td>
							<td class="text-center"><c:out value="${result.defaultValue}"/></td>
							<td class="text-center"><c:out value="${result.extra}"/></td>
							<td class="text-center"><c:out value="${result.comment}"/></td>
						</tr>
					</c:forEach>
					<c:if test="${fn:length(dbTableColumnList) == 0}">
						<tr>
							<td colspan="8" class="text-center"><spring:message code="common.nodata" /></td>
						</tr>
					</c:if>
					</tbody>
				</table>
				
<a href="?<c:out value='${searchDbTableInfoVO.queryString}' />">목록</a>