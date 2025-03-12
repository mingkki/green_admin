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
							<select name="mcoArea" id="mcoArea" class="form-control">
							<option value="">+ 지역</option>
							<c:forEach var="result" items="${areaList}" varStatus="status">
							<option value="<c:out value='${result.cddId}'/>" <c:if test="${result.cddId eq searchMemberCoordinatorVO.mcoArea}">selected="selected"</c:if>><c:out value="${result.cddName}"/></option>
							</c:forEach>
							</select> 
						</div>
						<div class="form-group">
							<select name="searchCondition" id="searchCondition" class="form-control">
							<option value="mcoId" <c:if test="${searchMemberCoordinatorVO.searchCondition eq 'mcoId'}">selected="selected"</c:if>>아이디</option>
							<option value="mcoName" <c:if test="${searchMemberCoordinatorVO.searchCondition eq 'mcoName'}">selected="selected"</c:if>>이름</option>
							<option value="mcoEmail" <c:if test="${searchMemberCoordinatorVO.searchCondition eq 'mcoEmail'}">selected="selected"</c:if>>이메일</option>
							<option value="mcoTel" <c:if test="${searchMemberCoordinatorVO.searchCondition eq 'mcoTel'}">selected="selected"</c:if>>전화번호</option>
							<option value="mcoHp" <c:if test="${searchMemberCoordinatorVO.searchCondition eq 'mcoHp'}">selected="selected"</c:if>>휴대폰번호</option>
							</select> 
						</div>
						<div class="input-group">						
							<input type="text" name="searchKeyword" id="searchKeyword" class="form-control" value="<c:out value='${searchMemberCoordinatorVO.searchKeyword}'/>"/>
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
				<input type="hidden" name="returnQueryString" value="<c:out value='${searchMemberCoordinatorVO.queryString}' escapeXml='false' />"/>
				<input type="hidden" name="act" value="" />			
				<div class="block-content np">
					<table class="table table-hover table-bordered">
					<colgroup>
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="text-center">아이디</th>
							<th scope="col" class="text-center">이름</th>
							<th scope="col" class="text-center">이메일</th>
							<th scope="col" class="text-center">지역</th>
							<th scope="col" class="text-center">로그인정보</th>
							<th scope="col" class="text-center">가입일</th>
							<th scope="col" class="text-center">관리</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="result" items="${resultList}" varStatus="status">
						<tr>
							<td class="text-center"><c:out value="${result.mcoId}"/></td>
							<td class="text-center"><c:out value="${result.mcoName}"/></td>
							<td class="text-center"><c:out value="${result.mcoEmail}"/></td>
							<td class="text-center"><c:out value="${result.mcoAreaNm}"/></td>
							<td class="text-center"><c:out value="${result.mcoLastloginDttm}"/> / <c:out value="${result.mcoLoginCnt}"/>회</td>
							<td class="text-center"><c:out value="${fn:substring(result.mcoRegDttm,0,10)}"/></td>
							<td class="text-center">
								<a href="?act=write&amp;mcoId=<c:out value='${result.mcoId}'/>&amp;<c:out value='${searchMemberCoordinatorVO.queryString}'/>" class="btn btn-default btn-xs">수정</a>
								<a href="javascript:post_delete('', 'act=delete&mcoId=<c:out value="${result.mcoId}"/>&<c:out value="${searchMemberCoordinatorVO.queryString}"/>');" class="btn btn-default btn-xs">삭제</a>
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
							<a href="?act=write&amp;<c:out value='${searchMemberCoordinatorVO.queryString}'/>" class="btn btn-primary">등록</a>
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