<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<div class="row">
	<div class="col-lg-3">
		<div class="block">
			<div class="block-content">
				<link rel="stylesheet" href="${CONTEXT_PATH}/assets/js/plugins/dtree/dtree.css">
				<script type="text/javascript" src="${CONTEXT_PATH}/assets/js/plugins/dtree/dtree.js"></script>
				<script type="text/javascript">
				var d = new dTree('d');
				d.add(1,-1,"코드", "${REQUEST_URI}");
				<c:forEach var="result" items="${codeListAll}" varStatus="status">
				d.add(<c:out value='${status.index+2}' />, 1, "<c:out value='${result.codName}' />", "?searchCodId=<c:out value='${result.codId}' />", "", "", "");
				</c:forEach>
				document.write(d);
				</script>
			</div>
		</div>
	</div>
	<div class="col-lg-9">
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
							<select name="cddUseYn" id="cddUseYn" class="form-control">
							<option value="">+ 사용유무</option>
							<option value="Y" <c:if test="${searchCodeDetailVO.cddUseYn eq 'Y'}">selected="selected"</c:if>>사용</option>
							<option value="N" <c:if test="${searchCodeDetailVO.cddUseYn eq 'N'}">selected="selected"</c:if>>미사용</option>
							</select>
						</div>
						<div class="form-group">
							<select name="searchCondition" id="searchCondition" class="form-control">
							<option value="cddId" <c:if test="${searchCodeDetailVO.searchCondition eq 'cddId'}">selected="selected"</c:if>>상세코드ID</option>
							<option value="cddName" <c:if test="${searchCodeDetailVO.searchCondition eq 'cddName'}">selected="selected"</c:if>>상세코드명</option>
							<option value="cddContent" <c:if test="${searchCodeDetailVO.searchCondition eq 'cddContent'}">selected="selected"</c:if>>상세코드 설명</option>
							<option value="codId" <c:if test="${searchCodeDetailVO.searchCondition eq 'codId'}">selected="selected"</c:if>>코드ID</option>
							<option value="codName" <c:if test="${searchCodeDetailVO.searchCondition eq 'codName'}">selected="selected"</c:if>>코드명</option>
							</select> 
						</div>
						<div class="input-group">						
							<input type="text" name="searchKeyword" id="searchKeyword" class="form-control" value="<c:out value='${searchCodeDetailVO.searchKeyword}'/>"/>
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
						<th scope="col" class="text-center">상세코드ID</th>
						<th scope="col" class="text-center">상세코드명</th>
						<th scope="col" class="text-center">설명</th>
						<th scope="col" class="text-center">순서</th>
						<th scope="col" class="text-center">사용유무</th>
						<th scope="col" class="text-center">관리</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">
					<tr<c:if test="${result.cddUseYn ne 'Y'}"> style="text-decoration:line-through"</c:if>>
						<td class="text-center"><c:out value="${result.codId}"/>(<c:out value="${result.codName}"/>)</td>
						<td class="text-center"><c:out value="${result.cddId}"/></td>
						<td class="text-center"><c:out value="${result.cddName}"/></td>
						<td class="text-center"><c:out value="${result.cddContent}"/></td>
						<td class="text-center"><c:out value="${result.cddOrderNo}"/></td>
						<td class="text-center">
							<c:choose>
								<c:when test="${result.cddUseYn eq 'Y'}">사용</c:when>
								<c:otherwise>미사용</c:otherwise>
							</c:choose>
						</td>
						<td class="text-center">
							<a href="?act=write&amp;codId=<c:out value='${result.codId}'/>&amp;cddId=<c:out value='${result.cddId}'/>&amp;<c:out value='${searchCodeDetailVO.queryString}'/>" class="btn btn-default btn-xs">수정</a>
							<a href="javascript:post_delete('', 'act=delete&codId=<c:out value="${result.codId}"/>&cddId=<c:out value="${result.cddId}"/>&<c:out value="${searchCodeDetailVO.queryString}"/>');" class="btn btn-default btn-xs">삭제</a>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(resultList) == 0}">
					<tr>
						<td colspan="7" class="text-center"><spring:message code="common.nodata" /></td>
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
						<a href="?act=write&amp;<c:out value='${searchCodeDetailVO.queryString}'/>" class="btn btn-primary">등록</a>
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