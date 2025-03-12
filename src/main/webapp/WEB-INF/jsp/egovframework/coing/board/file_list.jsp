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
			<div class="block-head pd-xs">
				<form action="${REQUEST_URI}" method="post" class="form-inline">
					<fieldset>
						<legend class="sr-only">검색 폼</legend>
						<div class="form-group">
							<select name="searchCondition" id="searchCondition" class="form-control">
							<option value="brdName" <c:if test="${searchBoardFileVO.searchCondition eq 'brdName'}">selected="selected"</c:if>>게시판명</option>
							<option value="bwrSubject" <c:if test="${searchBoardFileVO.searchCondition eq 'bwrSubject'}">selected="selected"</c:if>>게시글 제목</option>
							<option value="bfiOname" <c:if test="${searchBoardFileVO.searchCondition eq 'bfiOname'}">selected="selected"</c:if>>파일명</option>
							</select> 
						</div>
						<div class="input-group">						
							<input type="text" name="searchKeyword" id="searchKeyword" class="form-control" value="<c:out value='${searchBoardFileVO.searchKeyword}'/>"/>
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
						<th scope="col" class="text-center">번호</th>
						<th scope="col" class="text-center">게시판명</th>
						<th scope="col" class="text-center">게시글 제목</th>
						<th scope="col" class="text-center">파일명</th>
						<th scope="col" class="text-center">사이즈</th>
						<th scope="col" class="text-center">다운로드수</th>
						<th scope="col" class="text-center">등록(수정) ID</th>
						<th scope="col" class="text-center">등록(수정) 일시</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">
					<tr>
						<td class="text-center"><c:out value="${paginationInfo.totalRecordCount - (searchBoardFileVO.pageIndex-1) * searchBoardFileVO.pageSize - status.index}"/></td>
						<td class="text-center"><c:out value="${result.brdName}"/></td>
						<td class="text-center"><c:out value="${result.bwrSubject}"/></td>
						<td>
							<c:if test="${result.bfiDelYn eq 'Y'}">[삭제] </c:if>
							<a href="?act=download&amp;bfiId=<c:out value='${result.bfiId}'/>"><c:out value="${result.bfiOname}"/></a>
						</td>
						<td class="text-center"><c:out value="${cms:bytes2String(result.bfiSize)}"/></td>
						<td class="text-center"><c:out value="${result.bfiDownCnt}"/></td>
						<td class="text-center">
							<c:out value="${result.bfiRegId}"/>
							<c:if test="${not empty result.bfiUpdtId}"><br/>(<c:out value="${result.bfiUpdtId}"/>)</c:if>
						</td>
						<td class="text-center">
							<c:out value="${fn:substring(result.bfiRegDttm,0,16)}"/>
							<c:if test="${not empty result.bfiUpdtDttm}"><br/>(<c:out value="${fn:substring(result.bfiUpdtDttm,0,16)}"/>)</c:if>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(resultList) == 0}">
					<tr>
						<td colspan="6" class="text-center"><spring:message code="common.nodata" /></td>
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