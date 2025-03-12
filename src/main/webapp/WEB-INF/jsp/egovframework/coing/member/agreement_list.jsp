<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<div class="row">
	<div class="col-lg-12">
		<div class="block">
			<%@ include file="../include/tabmenu.jsp" %>
			<div class="block-head">
				<h4><c:out value="${ADMIN_MENU_VO.amePname}"/> 목록</h4>
			</div>
			<div class="block-head pd-xs">
				<form action="${REQUEST_URI}" method="post" class="form-inline">
					<fieldset>
						<legend class="sr-only">검색 폼</legend>
						<div class="form-group">
							<select name="searchCondition" id="searchCondition" class="form-control">
							<option value="magChangeDt" <c:if test="${searchMemberAgreementVO.searchCondition eq 'magChangeDt'}">selected="selected"</c:if>>변경날짜</option>
							</select> 
						</div>
						<div class="input-group">						
							<input type="text" name="searchKeyword" id="searchKeyword" class="form-control" value="<c:out value='${searchMemberAgreementVO.searchKeyword}'/>"/>
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
			<form name="listForm" action="${REQUEST_URI}" method="post" class="form-inline">
				<input type="hidden" name="returnQueryString" value="<c:out value='${searchMemberAgreementVO.queryString}' escapeXml='false' />"/>
				<input type="hidden" name="act" value="" />			
				<div class="block-content np">
					<table class="table table-hover table-bordered">
					<colgroup>
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="text-center">번호</th>
							<th scope="col" class="text-center">등록(변경)날짜</th>
							<th scope="col" class="text-center">변경이력</th>
							<th scope="col" class="text-center">사용유무</th>
							<th scope="col" class="text-center">관리</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="result" items="${resultList}" varStatus="status">
						<tr <c:if test="${result.magUseYn ne 'Y'}">style="text-decoration:line-through;"</c:if>>
							<td class="text-center"><c:out value="${paginationInfo.totalRecordCount - (searchMemberAgreementVO.pageIndex-1) * searchMemberAgreementVO.pageSize - status.index}"/></td>
							<td class="text-center"><c:out value="${result.magChangeDt}"/></td>
							<td><c:out value="${result.magMemo}"/></td>
							<td class="text-center">
								<c:choose>
									<c:when test="${result.magUseYn eq 'Y'}">사용</c:when>
									<c:otherwise>미사용</c:otherwise>
								</c:choose>
							</td>
							<td class="text-center">
								<a href="?act=write&amp;magId=<c:out value='${result.magId}'/>&amp;<c:out value='${searchMemberAgreementVO.queryString}'/>" class="btn btn-default btn-xs">수정</a>
								<a href="javascript:post_delete('', 'act=delete&magId=<c:out value="${result.magId}"/>&<c:out value="${searchMemberAgreementVO.queryString}"/>');" class="btn btn-default btn-xs">삭제</a>
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
							<a href="?act=write&amp;<c:out value='${searchMemberAgreementVO.queryString}'/>" class="btn btn-primary">등록</a>
						</div>
					</div>
					<div class="text-center">
						<ul class="pagination pagination-sm">
						<ui:pagination paginationInfo="${paginationInfo}" type="default" jsFunction="${paginationQueryString}"/>
						</ul>	
					</div>
				</div>
			</form>
		</div>
	</div>
</div>