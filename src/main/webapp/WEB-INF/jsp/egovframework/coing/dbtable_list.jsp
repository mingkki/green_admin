<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="/WEB-INF/tld/cms.tld" prefix="cms"%>
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
			<div class="block-content">
				<div class="pull-left pd-t-xs">
					전체 <strong><c:out value="${resultCnt}"/></strong>건
					, 현재 페이지 <c:out value="${paginationInfo.currentPageNo}"/> / <c:out value="${paginationInfo.totalPageCount}"/>
				</div>
				<div class="pull-right">
					<form action="${REQUEST_URI}" method="post" class="form-inline">
						<fieldset>
							<legend class="sr-only">검색 폼</legend>
							<div class="form-group">
								<select name="searchCondition" id="searchCondition" class="form-control">
								<option value="tableName" <c:if test="${searchDbTableInfoVO.searchCondition eq 'tableName'}">selected="selected"</c:if>>테이블명</option>
								</select> 
							</div>
							<div class="input-group">						
								<input type="text" name="searchKeyword" id="searchKeyword" class="form-control" value="<c:out value='${searchDbTableInfoVO.searchKeyword}'/>"/>
								<span class="input-group-btn">
									<button type="submit" class="btn btn-default"><spring:message code="common.search" /></button>
									<a href="${REQUEST_URI}" class="btn btn-default"><spring:message code="common.reset" /></a>
								</span>
							</div>
							<div class="form-group">
								<a href="?act=download&amp;<c:out value='${searchDbTableInfoVO.queryString}'/>" class="btn btn-info">테이블 리스트 다운로드</a>
								<a href="?act=download2&amp;<c:out value='${searchDbTableInfoVO.queryString}'/>" class="btn btn-info">테이블 컬럼리스트 다운로드</a>
							</div>
						</fieldset>					
					</form>				
				</div>
			</div>			
			<div class="block-content np">
				<table class="table table-hover table-bordered">
				<colgroup>
				</colgroup>
				<thead>
					<tr>
						<th scope="col" class="text-center">NAME</th>
						<th scope="col" class="text-center">TYPE</th>
						<th scope="col" class="text-center">ENGINE</th>
						<th scope="col" class="text-center">ROWS</th>
						<th scope="col" class="text-center">DATA SIZE</th>
						<th scope="col" class="text-center">INDEX SIZE</th>
						<th scope="col" class="text-center">TOTAL SIZE</th>
						<th scope="col" class="text-center">AUTO INCREMENT</th>
						<th scope="col" class="text-center">CREATE TIME</th>
						<th scope="col" class="text-center">UPDATE TIME</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">
					<tr>
						<td class="text-center">
							<a href="?act=view&amp;tableName=<c:out value='${result.tableName}'/>&amp;<c:out value='${searchDbTableInfoVO.queryString}'/>">
								<c:out value="${result.tableName}"/>
								[<c:out value="${result.tableComment}"/>]
							</a>
						</td>
						<td class="text-center"><c:out value="${result.tableType}"/></td>
						<td class="text-center"><c:out value="${result.engine}"/></td>
						<td class="text-center"><c:out value="${result.tableRows}"/></td>
						<td class="text-center"><c:out value="${cms:bytes2String(result.dataLength)}"/></td>
						<td class="text-center"><c:out value="${cms:bytes2String(result.indexLength)}"/></td>
						<td class="text-center"><c:out value="${cms:bytes2String(result.dataLength + result.indexLength)}"/></td>
						<td class="text-center"><c:out value="${result.autoIncrement}"/></td>
						<td class="text-center"><c:out value="${result.createTime}"/></td>
						<td class="text-center"><c:out value="${result.updateTime}"/></td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(resultList) == 0}">
					<tr>
						<td colspan="11" class="text-center"><spring:message code="common.nodata" /></td>
					</tr>
				</c:if>
				</tbody>
				</table>
				<div class="text-center">
					<ul class="pagination pagination-sm">
					<ui:pagination paginationInfo="${paginationInfo}" type="default" jsFunction="${paginationQueryString}"/>
					</ul>	
				</div>					
			</div>
		</div>
	</div>
</div>