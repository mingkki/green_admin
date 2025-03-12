<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<div class="row">
	<div class="col-lg-12">
		<div class="block">
			<div class="block-head">
				<div class="pull-left">
					<h4><c:out value="${boardInfoVO.brdName}"/> 처리내역 관리</h4>
				</div>
				<div class="pull-right">
					<div class="pd-r-xs pd-t-xs">				
						<a href="?<c:out value='${searchBoardInfoVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list" /></a>
					</div>
				</div>
			</div>
			<div class="block-content np">
				<table class="table table-hover table-bordered">
				<colgroup>
				</colgroup>
				<thead>
					<tr>
						<th scope="col" class="text-center">번호</th>
						<th scope="col" class="text-center">처리내역명</th>
						<th scope="col" class="text-center">순번</th>
						<th scope="col" class="text-center">사용유무</th>
						<th scope="col" class="text-center">관리</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="result" items="${boardCategoryList}" varStatus="status">
					<tr>
						<td class="text-center"><c:out value="${status.index+1}"/></td>
						<td class="text-center"><c:out value="${result.bcaTitle}"/></td>
						<td class="text-center"><c:out value="${result.bcaOrderNo}"/></td>
						<td class="text-center">
							<c:choose>
								<c:when test="${result.bcaUseYn eq 'Y'}">사용</c:when>
								<c:otherwise>미사용</c:otherwise>
							</c:choose>
						</td>
						<td class="text-center">
							<a href="?act=progress&amp;brdId=<c:out value='${result.brdId}'/>&amp;bcaId=<c:out value='${result.bcaId}'/>&amp;<c:out value='${searchBoardInfoVO.queryString}'/>" class="btn btn-default btn-xs">수정</a>
							<a href="javascript:post_delete('', 'act=pdelete&brdId=<c:out value="${result.brdId}"/>&bcaId=<c:out value="${result.bcaId}"/>&<c:out value="${searchBoardInfoVO.queryString}"/>');" class="btn btn-default btn-xs">삭제</a>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(boardCategoryList) == 0}">
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
						<a href="?act=progress&amp;brdId=<c:out value='${searchBoardInfoVO.brdId}'/>&amp;mode=insert&amp;<c:out value='${searchBoardInfoVO.queryString}'/>" class="btn btn-primary">등록</a>
					</div>
				</div>
			</div>			
		</div>
		<c:if test="${not empty mode || command eq 'update'}">
		<div class="block">		
			<form:form commandName="writeBoardCategory" action="?act=progress" class="form-horizontal" role="form">
				<input type="hidden" name="command" value="${command}"/>
				<input type="hidden" name="returnQueryString" value="<c:out value='${searchBoardInfoVO.queryString}' escapeXml='false' />" />
				<input type="hidden" name="brdId" value="${boardInfoVO.brdId}"/>
	
				<c:if test="${command == 'update'}">
				<input type="hidden" name="bcaId" value="<c:out value='${writeBoardCategory.bcaId}' />" />
				</c:if>
				<fieldset>
					<div class="block-head">
						<h4>처리내역 등록/수정</h4>
					</div>
					<div class="block-content">
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="bcaTitle" class="control-label">* 처리내역명</label></span>
							<div class="col-sm-10">
								<form:input path="bcaTitle" maxlength="60" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">* 사용유무</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="bcaUseYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="bcaUseYn" value="N"/>미사용</label>	
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="bcaOrderNo" class="control-label">* 순서</label></span>
							<div class="col-sm-10">
								<form:input path="bcaOrderNo" maxlength="3" class="form-control required digits" />
							</div>
						</div>
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
							</div>
						</div>
					</div>
				</fieldset>
			</form:form>			
		</div>
		</c:if>
	</div>
</div>