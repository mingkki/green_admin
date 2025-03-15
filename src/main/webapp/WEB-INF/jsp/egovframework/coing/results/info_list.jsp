<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<div class="row">
	<div class="col-lg-12">
		<div class="block">
			<div class="block-head">
				<h4><c:out value="${ADMIN_MENU_VO.amePname}"/> 목록</h4>
			</div>
			<div class="block-head pd-xs">
				<form action="${REQUEST_URI}" method="post" class="form-inline">
					<fieldset>
						<legend class="sr-only">검색 폼</legend>
						<div class="form-group">
							<select name="searchCondition" id="searchCondition" class="form-control">
							<option value="" <c:if test="${searchResultsInfoVO.searchCondition eq ''}">selected="selected"</c:if>>타이틀</option>
							</select> 
						</div>
						<div class="input-group">						
							<input type="text" name="searchKeyword" id="searchKeyword" class="form-control" value="<c:out value='${searchResultsInfoVO.searchKeyword}'/>"/>
							<span class="input-group-btn">
								<button type="submit" class="btn btn-default"><spring:message code="common.search" /></button>
								<a href="${REQUEST_URI}" class="btn btn-default"><spring:message code="common.reset" /></a>
							</span>
						</div>
					</fieldset>					
				</form>
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
					<thead>
						<tr>
							<th scope="col" class="text-center">번호</th>
							<th scope="col" class="text-center">품목</th>
							<th scope="col" class="text-center">업체명</th>
							<th scope="col" class="text-center">항목</th>
							<th scope="col" class="text-center">모델/서비스</th>
							<th scope="col" class="text-center">수량</th>
							<th scope="col" class="text-center">단가</th>
							<th scope="col" class="text-center">관리</th>
						</tr>
					</thead>
					<tbody>	
					<c:forEach var="result" items="${resultList}" varStatus="status">
						<tr>
							<td class="text-center"><c:out value="${paginationInfo.totalRecordCount - (searchResultsInfoVO.pageIndex-1) * searchResultsInfoVO.pageSize - status.index}"/></td>
							<td class="text-center">
								<c:forEach var="item" items="${itemList}" varStatus="status">
									<c:out value="${item.cddName}"/>
								</c:forEach>
							</td>
							<td class="text-center">
								<c:forEach var="company" items="${companyList}" varStatus="status">
									<c:out value="${company.cddName}"/>
								</c:forEach>
							</td>
							<td class="text-center"><c:out value='${result.rsinName}'/></td>
							<td class="text-center"><c:out value='${result.rsinModel}'/></td>
							<td class="text-center"><c:out value='${result.rsinCount}'/></td>
							<td class="text-center"><c:out value='${result.rsinMoney}'/></td>
							<td class="text-center">
								<a href="?act=write&amp;rsinId=<c:out value='${result.rsinId}'/>&amp;<c:out value='${searchResultsInfoVO.queryString}'/>" class="btn btn-default btn-xs"><spring:message code="common.update"/></a>
								<a href="javascript:post_delete('', 'act=delete&rsinId=<c:out value="${result.rsinId}"/>&<c:out value="${searchResultsInfoVO.queryString}"/>');" class="btn btn-default btn-xs"><spring:message code="common.delete"/></a>
							</td>		
						</tr>
					</c:forEach>
					<c:if test="${fn:length(resultList) == 0}">
						<tr>
							<td colspan="9" class="text-center"><spring:message code="info.nodata.msg"/></td>  
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
						<a href="?act=write&amp;<c:out value='${searchResultsInfoVO.queryString}'/>" class="btn btn-primary">등록</a>
					</div>
				</div>
				<div class="text-center">
					<ul class="pagination pagination-sm">
					<ui:pagination paginationInfo="${paginationInfo}" type="default" jsFunction="${paginationQueryString}"/>
					</ul>	
				</div>
			</div>
		</div>
	</div>
</div>