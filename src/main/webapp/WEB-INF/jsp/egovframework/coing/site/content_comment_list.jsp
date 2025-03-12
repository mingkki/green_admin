<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>

 <div class="row">
	<div class="col-lg-2">
		<div class="block">
			<div class="block-content">
				<form action="${CONTEXT_PATH}" method="get" class="form-search" role="form">
					<fieldset>
						<legend class="sr-only">검색 폼</legend>
						<div class="form-group">
							<select name="sinId" id="sinId" class="form-control" onchange="this.form.submit();">
							<option value="">+ 사이트선택</option>
							<c:forEach var="result" items="${siteList}" varStatus="status">
							<option value="${result.sinId}" <c:if test="${searchSiteContentCommentVO.sinId == result.sinId}">selected="selected"</c:if>><c:out value="${result.sinName}" /></option>
							</c:forEach>
							</select>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
		<div class="block">
			<div class="block-content">
				<link rel="stylesheet" href="${CONTEXT_PATH}/assets/js/plugins/dtree/dtree.css">
				<script type="text/javascript" src="${CONTEXT_PATH}/assets/js/plugins/dtree/dtree.js"></script>
				<script type="text/javascript">
				var d = new dTree('d');
				d.add(1,-1,"홈", "?sinId=<c:out value='${searchSiteContentCommentVO.sinId}' />");
				/* d.add(<c:out value="${rootMenuId}"/>,-1,"홈", "${REQUEST_URI}"); */
				<c:forEach var="result" items="${allSiteMenuList}" varStatus="status">
				<c:if test="${result.smeParntsId > 0}">
				d.add(<c:out value='${result.smeId}' />, <c:out value='${result.smeParntsId}' />, "<c:out value='${result.smeName}' />", "?sinId=<c:out value='${result.sinId}' />&smeId=<c:out value='${result.smeId}' />", "", "", "");
				</c:if>
				</c:forEach>
				document.write(d);
				</script>
			</div>
		</div>
	</div>
	<div class="col-lg-10">
		<div class="block">
			<div class="block-head">
				<h2>홈 &gt; <c:if test="${not empty siteMenuVO}"><c:out value="${siteMenuVO.menuPath}"/></c:if></h2>
			</div>
			<div class="block-content">
				<form action="${REQUEST_URI}?sinId=<c:out value='${searchSiteContentCommentVO.sinId}'/>" method="post" class="form-inline" role="form">
					<input type="hidden" name="smeId" value="<c:out value='${searchSiteContentCommentVO.smeId}'/>" />
					<fieldset>
							<legend class="sr-only">검색 폼</legend>
							<div class="form-group">
								<input type="text" name="searchStartDt" id="searchStartDt" class="form-control datepicker" size="10" value="<c:out value='${searchSiteContentCommentVO.searchStartDt}'/>"/>
								~
								<input type="text" name="searchEndDt" id="searchEndDt" class="form-control datepicker" size="10" value="<c:out value='${searchSiteContentCommentVO.searchEndDt}'/>"/>
							</div>
							<div class="form-group">
								<select name="searchCondition" id="searchCondition" class="form-control">
								<option value="smeName" <c:if test="${searchSiteContentCommentVO.searchCondition == 'smeName'}">selected="selected"</c:if>>메뉴명</option>
								<option value="writerNm" <c:if test="${searchSiteContentCommentVO.searchCondition == 'writerNm'}">selected="selected"</c:if>>작성자명</option>
								<option value="content" <c:if test="${searchSiteContentCommentVO.searchCondition == 'content'}">selected="selected"</c:if>>내용</option>
								<option value="scoRegIp" <c:if test="${searchSiteContentCommentVO.searchCondition == 'scoRegIp'}">selected="selected"</c:if>>작성IP</option>
								</select>
							</div>
							<div class="input-group">
								<input type="text" name="searchKeyword" id="searchKeyword" class="form-control" value="<c:out value='${searchSiteContentCommentVO.searchKeyword}'/>"/>
								<span class="input-group-btn">
									<button type="submit" class="btn btn-default"><i class="fa fa-search"></i> <spring:message code="common.search"/></button>
									<a href="${REQUEST_URI}?sinId=<c:out value='${searchSiteContentCommentVO.sinId}'/>" class="btn btn-default"><i class="fa fa-refresh"></i> <spring:message code="common.reset"/></a>
								</span>
							</div>
						</fieldset>
				</form>
			</div>
			<div class="block-content np">
				<table class="table">
					<thead>
						<tr>
							<th scope="col" class="text-center">번호</th>
							<th scope="col" class="text-center">메뉴위치</th>
							<th scope="col" class="text-center">메뉴명</th>
							<th scope="col" class="text-center">작성자</th>
							<th scope="col" class="text-center">내용</th>
							<th scope="col" class="text-center">점수</th>
							<th scope="col" class="text-center">작성일</th>
							<th scope="col" class="text-center">작성IP</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="result" items="${resultList}" varStatus="status">
						<tr>
							<td class="text-center"><c:out value="${(paginationInfo.totalRecordCount+1) - (searchSiteContentCommentVO.pageIndex-1) * searchSiteContentCommentVO.pageUnit - status.count}"/></td>
							<td class="text-center"><c:out value="${result.menuPath}" /></td>
							<td class="text-center"><c:out value="${result.smeName}" /></td>
							<td class="text-center"><c:out value="${result.writerNm}" />(<c:out value="${result.writerId}" />)</td>
							<td class="text-center"><c:out value="${result.content}" /></td>
							<td class="text-center"><c:out value="${result.grade}" /></td>
							<td class="text-center"><c:out value="${result.scoRegDttm}" /></td>
							<td class="text-center"><c:out value="${result.scoRegIp}" /></td>
						</tr>
					</c:forEach>
					<c:if test="${fn:length(resultList) == 0}">
						<tr>
							<td colspan="8" class="text-center"><spring:message code="info.nodata.msg"/></td>
						</tr>
					</c:if>
					</tbody>
				</table>
			</div> 
			 <div class="block-footer">
				<div class="clearfix">
					<div class="pull-left">
						<ul class="pagination pagination-sm">
						<ui:pagination paginationInfo="${paginationInfo}" type="default" jsFunction="${paginationQueryString}"/>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>