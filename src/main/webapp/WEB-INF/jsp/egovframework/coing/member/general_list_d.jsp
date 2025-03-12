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
							<option value="memId" <c:if test="${searchMemberGeneralVO.searchCondition eq 'memId'}">selected="selected"</c:if>>아이디</option>
							<option value="memName" <c:if test="${searchMemberGeneralVO.searchCondition eq 'memName'}">selected="selected"</c:if>>이름</option>
							<option value="memLeaveDttm" <c:if test="${searchMemberGeneralVO.searchCondition eq 'memLeaveDttm'}">selected="selected"</c:if>>탈퇴일</option>
							<option value="memLeaveIp" <c:if test="${searchMemberGeneralVO.searchCondition eq 'memLeaveIp'}">selected="selected"</c:if>>탈퇴IP</option>
							</select> 
						</div>
						<div class="input-group">						
							<input type="text" name="searchKeyword" id="searchKeyword" class="form-control" value="<c:out value='${searchMemberGeneralVO.searchKeyword}'/>"/>
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
				<input type="hidden" name="returnQueryString" value="<c:out value='${searchMemberGeneralVO.queryString}' escapeXml='false' />"/>
				<input type="hidden" name="act" value="" />			
				<div class="block-content np">
					<table class="table table-hover table-bordered">
					<colgroup>
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="text-center"><input type="checkbox" onclick="set_checkbox(listForm, 'chks', this.checked)" /></th>
							<th scope="col" class="text-center">아이디</th>
							<th scope="col" class="text-center">이름</th>
							<th scope="col" class="text-center">탈퇴일</th>
							<th scope="col" class="text-center">탈퇴IP</th>
							<th scope="col" class="text-center">작업자ID</th>
							<th scope="col" class="text-center">관리</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="result" items="${resultList}" varStatus="status">
						<tr>
							<td class="text-center"><input type="checkbox" name="chks" value="<c:out value='${result.memId}'/>" /></td>
							<td class="text-center"><c:out value="${result.memId}"/></td>
							<td class="text-center"><c:out value="${result.memEmail}"/></td>
							<td class="text-center"><c:out value="${result.memLevelNm}"/></td>
							<td class="text-center"><c:out value="${result.memLastloginDttm}"/> / <c:out value="${result.memLoginCnt}"/>회</td>
							<td class="text-center"><c:out value="${fn:substring(result.memRegDttm,0,10)}"/></td>
							<td class="text-center">
								<a href="javascript:post_delete('', 'act=delete&memId=<c:out value="${result.memId}"/>&<c:out value="${searchMemberGeneralVO.queryString}"/>');" class="btn btn-default btn-xs">삭제</a>
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
							선택된 회원을
							<button type="button" class="btn btn-default" onclick="deleteMembers();">삭제</button>
						</div>
						<div class="pull-right">
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

<script type="text/javascript">
function deleteMembers() 
{
	if (!chk_checkbox(listForm, 'chks', true))
	{
		alert('삭제할 회원을 선택하여 주십시오.');
		return false;
	}

	if (!confirm('선택하신 회원을 삭제하시겠습니까?')) 
	{
		return false;
	}

	listForm.act.value = 'deleteMembers';
	listForm.submit();
}

function updateLevel(el) 
{
	if (!chk_checkbox(listForm, 'chks', true))
	{
		el.value = '';
		alert('등급을 변경하실 회원을 선택하여 주십시오.');
		return false;
	}

	if (!confirm('선택하신 회원을 \''+ el.options[el.options.selectedIndex].text +'\' 등급으로 변경하시겠습니까?')) 
	{
		el.value = '';
		return false;
	}

	listForm.act.value = 'updateLevel';
	listForm.submit();
}

function updateStatus(el) 
{
	if (!chk_checkbox(listForm, 'chks', true))
	{
		el.value = '';
		alert('상태를 변경하실 회원을 선택하여 주십시오.');
		return false;
	}

	if (!confirm('선택하신 회원을 \''+ el.options[el.options.selectedIndex].text +'\' 상태로 변경하시겠습니까?')) 
	{
		el.value = '';
		return false;
	}

	listForm.act.value = 'updateStatus';
	listForm.submit();
}
</script>
