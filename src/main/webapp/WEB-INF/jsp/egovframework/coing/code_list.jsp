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
			<div class="block-head pd-xs">
				<form action="${REQUEST_URI}" method="post" class="form-inline">
					<fieldset>
						<legend class="sr-only">검색 폼</legend>
						<div class="form-group">
							<select name="codUseYn" id="codUseYn" class="form-control">
							<option value="">+ 사용유무</option>
							<option value="Y" <c:if test="${searchCodeVO.codUseYn eq 'Y'}">selected="selected"</c:if>>사용</option>
							<option value="N" <c:if test="${searchCodeVO.codUseYn eq 'N'}">selected="selected"</c:if>>미사용</option>
							</select>
						</div>
						<div class="form-group">
							<select name="searchCondition" id="searchCondition" class="form-control">
							<option value="codId" <c:if test="${searchCodeVO.searchCondition eq 'codId'}">selected="selected"</c:if>>코드ID</option>
							<option value="codName" <c:if test="${searchCodeVO.searchCondition eq 'codName'}">selected="selected"</c:if>>코드명</option>
							<option value="codContent" <c:if test="${searchCodeVO.searchCondition eq 'codContent'}">selected="selected"</c:if>>코드설명</option>
							</select> 
						</div>
						<div class="input-group">						
							<input type="text" name="searchKeyword" id="searchKeyword" class="form-control" value="<c:out value='${searchCodeVO.searchKeyword}'/>"/>
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
				<colgroup>
				</colgroup>
				<thead>
					<tr>
						<th scope="col" class="text-center">코드ID</th>
						<th scope="col" class="text-center">코드명</th>
						<th scope="col" class="text-center">코드설명</th>
						<th scope="col" class="text-center">사용유무</th>
						<th scope="col" class="text-center">관리</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">
					<tr<c:if test="${result.codUseYn ne 'Y'}"> style="text-decoration:line-through"</c:if>>
						<td class="text-center"><c:out value="${result.codId}"/></td>
						<td class="text-center"><c:out value="${result.codName}"/></td>
						<td class="text-center"><c:out value="${result.codContent}"/></td>
						<td class="text-center">
							<c:choose>
								<c:when test="${result.codUseYn eq 'Y'}">사용</c:when>
								<c:otherwise>미사용</c:otherwise>
							</c:choose>
						</td>
						<td class="text-center">
							<a href="?act=write&amp;codId=<c:out value='${result.codId}'/>&amp;<c:out value='${searchCodeVO.queryString}'/>" class="btn btn-default btn-xs">수정</a>
							<a href="javascript:post_delete('', 'act=delete&codId=<c:out value="${result.codId}"/>&<c:out value="${searchCodeVO.queryString}"/>');" class="btn btn-default btn-xs">삭제</a>
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
						<a href="?act=write&amp;<c:out value='${searchCodeVO.queryString}'/>" class="btn btn-primary">등록</a>
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