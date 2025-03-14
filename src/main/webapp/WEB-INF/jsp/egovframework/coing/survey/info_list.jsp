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
			<ul class="nav nav-tabs">
				<li<c:if test="${empty searchSurveyInfoVO.progress}"> class="active"</c:if>><a href="?">전체</a></li>
				<li<c:if test="${searchSurveyInfoVO.progress eq 'ING'}"> class="active"</c:if>><a href="?progress=ING">진행중</a></li>
				<li<c:if test="${searchSurveyInfoVO.progress eq 'COMPLETE'}"> class="active"</c:if>><a href="?progress=COMPLETE">마감</a></li>
				<li<c:if test="${searchSurveyInfoVO.progress eq 'PRE'}"> class="active"</c:if>><a href="?progress=PRE">진행전</a></li>
			</ul>			
			<div class="block-head pd-xs">
				<form action="${REQUEST_URI}" method="post" class="form-inline">
					<fieldset>
						<legend class="sr-only">검색 폼</legend>
						<div class="form-group">
							<select name="svinUseYn" id="svinUseYn" class="form-control">
							<option value="">+ 사용여부</option>
							<option value="Y" <c:if test="${searchSurveyInfoVO.svinUseYn == 'Y'}">selected="selected"</c:if>>사용함</option>
							<option value="N" <c:if test="${searchSurveyInfoVO.svinUseYn == 'N'}">selected="selected"</c:if>>사용안함</option>						
							</select> 
						</div>	
						<div class="form-group">
							<select name="searchCondition" id="searchCondition" class="form-control">
							<option value="svinTitle" <c:if test="${searchSurveyInfoVO.searchCondition eq 'svinTitle'}">selected="selected"</c:if>>타이틀</option>
							</select> 
						</div>
						<div class="input-group">						
							<input type="text" name="searchKeyword" id="searchKeyword" class="form-control" value="<c:out value='${searchSurveyInfoVO.searchKeyword}'/>"/>
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
							<th scope="col" class="text-center">설문제목</th>
							<th scope="col" class="text-center">시작일/종료일</th>
							<th scope="col" class="text-center">공개여부</th>
							<th scope="col" class="text-center">중복투표여부</th>
							<th scope="col" class="text-center">결과공개여부</th>
							<th scope="col" class="text-center">사용여부</th>							
							<th scope="col" class="text-center">기능</th>
							<th scope="col" class="text-center">관리</th>
						</tr>
					</thead>
					<tbody>	
					<c:forEach var="result" items="${resultList}" varStatus="status">
						<tr <c:if test="${result.svinUseYn eq 'N'}">style="text-decoration:line-through"</c:if>>
							<td class="text-center"><c:out value="${paginationInfo.totalRecordCount - (searchSurveyInfoVO.pageIndex-1) * searchSurveyInfoVO.pageSize - status.index}"/></td>							
							<td>
								<c:out value="${result.svinTitle}"/>
								<c:choose>
									<c:when test="${result.progress eq 'ING'}"><span class="label label-info">진행중</span></c:when>
									<c:when test="${result.progress eq 'COMPLETE'}"><span class="label label-default">마감</span></c:when>
									<c:when test="${result.progress eq 'PRE'}"><span class="label label-primary">진행전</span></c:when>
								</c:choose>								
							</td>
							<td class="text-center"><c:out value="${result.svinStartDt}"/> ~<br /><c:out value="${result.svinEndDt}"/></td>
							<td class="text-center">
								<c:choose>
									<c:when test="${result.svinOpenYn eq 'Y'}">공개</c:when>
									<c:otherwise>비공개</c:otherwise>
								</c:choose>
							</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${result.svinDplctnYn eq 'Y'}">허용</c:when>
									<c:otherwise>허용안함</c:otherwise>
								</c:choose>							
							</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${result.svinResopenYn eq 'Y'}">공개</c:when>
									<c:otherwise>비공개</c:otherwise>
								</c:choose>							
							</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${result.svinUseYn eq 'Y'}">사용함</c:when>
									<c:otherwise>사용안함</c:otherwise>
								</c:choose>
							</td>
							<td class="text-center">
								<button class="btn btn-default btn-xs" onclick="window.open('?act=qlist&svinId=<c:out value='${result.svinId}'/>', 'qlist', 'width=900, height=1000, scrollbars=auto'); return false;">항목설정</button><br />
								<a href="?act=excel&amp;svinId=<c:out value='${result.svinId}'/>" class="btn btn-default btn-xs">엑셀다운</a><br />
								<a href="?act=excel2&amp;svinId=<c:out value='${result.svinId}'/>" class="btn btn-default btn-xs">결과다운</a><br />
								<button class="btn btn-default btn-xs" onclick="post_url('', 'act=copy&svinId=<c:out value="${result.svinId}" />&<c:out value="${searchSurveyInfoVO.queryString}"/>', '정말로 복사하시겠습니까?');">설문복사</button>
							</td>
							<td class="text-center">
								<a href="?act=write&amp;svinId=<c:out value='${result.svinId}'/>&amp;<c:out value='${searchSurveyInfoVO.queryString}'/>" class="btn btn-default btn-xs"><spring:message code="common.update"/></a>
								<a href="javascript:post_delete('', 'act=delete&svinId=<c:out value="${result.svinId}"/>&<c:out value="${searchSurveyInfoVO.queryString}"/>');" class="btn btn-default btn-xs"><spring:message code="common.delete"/></a>
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
						<a href="?act=write&amp;<c:out value='${searchSurveyInfoVO.queryString}'/>" class="btn btn-primary">등록</a>
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