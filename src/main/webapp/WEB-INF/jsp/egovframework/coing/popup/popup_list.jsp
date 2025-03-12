<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<div class="row">
	<div class="col-lg-2">
		<div class="block">
			<div class="block-content">
				<link rel="stylesheet" href="${CONTEXT_PATH}/assets/js/plugins/dtree/dtree.css">
				<script type="text/javascript" src="${CONTEXT_PATH}/assets/js/plugins/dtree/dtree.js"></script>
				<script type="text/javascript">
				var d = new dTree('d');
				d.add(1,-1,"분류", "${REQUEST_URI}");
				<c:forEach var="result" items="${popCategoryList}" varStatus="status">
				d.add(<c:out value='${status.index+2}' />, 1, "<c:out value='${result.cddName}' />", "?popCategory=<c:out value='${result.cddId}' />", "", "", "");
				</c:forEach>
				document.write(d);
				</script>
			</div>
		</div>
	</div>
	<div class="col-lg-10">
		<div class="block">
			<div class="block-head">
				<h4><c:out value="${ADMIN_MENU_VO.amePname}"/> 목록</h4>
			</div>
			<div class="block-head pd-xs">
				<form action="${REQUEST_URI}" method="post" class="form-inline">
					<fieldset>
						<legend class="sr-only">검색 폼</legend>
						<div class="form-group">
							<select name="progress" id="progress" class="form-control">
							<option value="">+ 상태</option>
							<option value="ING" <c:if test="${searchPopupVO.progress eq 'ING'}">selected="selected"</c:if>>진행중</option>
							<option value="COMPLETE" <c:if test="${searchPopupVO.progress eq 'COMPLETE'}">selected="selected"</c:if>>기간종료</option>
							<option value="PRE" <c:if test="${searchPopupVO.progress eq 'PRE'}">selected="selected"</c:if>>준비중</option>
							</select> 
						</div>
						<div class="form-group">
							<select name="popUseYn" id="popUseYn" class="form-control">
							<option value="">+ 사용유무</option>
							<option value="Y" <c:if test="${searchPopupVO.popUseYn eq 'Y'}">selected="selected"</c:if>>사용</option>
							<option value="N" <c:if test="${searchPopupVO.popUseYn eq 'N'}">selected="selected"</c:if>>미사용</option>
							</select> 
						</div>
						<div class="form-group">
							<select name="searchCondition" id="searchCondition" class="form-control">
							<option value="popTitle" <c:if test="${searchPopupVO.searchCondition eq 'popTitle'}">selected="selected"</c:if>>팝업명</option>
							</select> 
						</div>
						<div class="input-group">						
							<input type="text" name="searchKeyword" id="searchKeyword" class="form-control" value="<c:out value='${searchPopupVO.searchKeyword}'/>"/>
							<span class="input-group-btn">
								<button type="submit" class="btn btn-default"><spring:message code="common.search" /></button>
								<a href="${REQUEST_URI}" class="btn btn-default"><spring:message code="common.reset" /></a>
							</span>
						</div>
						<div class="form-group">
							<a href="${CONTEXT_PATH}/code/detail.do?searchCodId=POP<c:out value='${fn:toUpperCase(searchPopupVO.popType)}'/>" class="btn btn-default">분류설정</a> 
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
						<th scope="col" class="text-center">분류</th>
						<th scope="col" class="text-center">이미지</th>
						<th scope="col" class="text-center">제목</th>
						<th scope="col" class="text-center">게시일시</th>
						<th scope="col" class="text-center">정렬순서</th>
						<th scope="col" class="text-center">링크 카운터</th>
						<th scope="col" class="text-center">공유</th>
						<th scope="col" class="text-center">상태</th>
						<th scope="col" class="text-center">관리</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">
					<tr <c:if test="${result.popUseYn eq 'N'}">style="text-decoration:line-through"</c:if>>
						<td class="text-center"><c:out value="${paginationInfo.totalRecordCount - (searchPopupVO.pageIndex-1) * searchPopupVO.pageSize - status.index}"/></td>
						<td class="text-center"><c:out value="${result.popCategoryNm}"/></td>
						<td class="text-center">
							<c:if test="${not empty result.popFile}">
							<img src="${CONTEXT_PATH}/imgview.do?file=<c:out value='${result.popFile}'/>" alt="" width="100"/>
							</c:if>							
						</td>
						<td class="text-center">
							<c:out value="${result.popTitle}"/>
							<c:if test="${not empty result.popLinkUrl}">
							<a href="${result.popLinkUrl}" target="_blank">링크</a>
							</c:if>
						</td>
						<td class="text-center">
							<c:choose>
								<c:when test="${result.popPeriodYn eq 'N'}">제한없음</c:when>
								<c:otherwise><c:out value="${result.popStartDttm}"/> ~<br/><c:out value="${result.popEndDttm}"/></c:otherwise>
							</c:choose>
						</td>
						<td class="text-center"><c:out value="${result.popOrderNo}"/></td>
						<td class="text-center"><c:out value="${result.popHits}"/></td>
						<td class="text-center"><c:out value="${fn:replace(result.popSharesNm,',','<br/>')}" escapeXml="false"/></td>
						<td class="text-center">
							<c:choose>
								<c:when test="${result.progress eq 'ING'}">진행중</c:when>
								<c:when test="${result.progress eq 'COMPLETE'}">기간종료</c:when>
								<c:otherwise>준비중</c:otherwise>
							</c:choose>
						</td>
						<td class="text-center">
							<a href="?act=write&amp;popId=<c:out value='${result.popId}'/>&amp;<c:out value='${searchPopupVO.queryString}'/>" class="btn btn-default btn-xs">수정</a>
							<a href="javascript:post_delete('', 'act=delete&popId=<c:out value="${result.popId}"/>&<c:out value="${searchPopupVO.queryString}"/>');" class="btn btn-default btn-xs">삭제</a>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(resultList) == 0}">
					<tr>
						<td colspan="10" class="text-center"><spring:message code="common.nodata" /></td>
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
						<a href="?act=write&amp;<c:out value='${searchPopupVO.queryString}'/>" class="btn btn-primary">등록</a>
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