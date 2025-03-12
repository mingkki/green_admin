<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">
	<div class="col-lg-12">
		<div class="block">
			<div class="block-head">
				<h1><c:out value="${ADMIN_MENU_VO.amePname}"/> <c:choose><c:when test="${command == 'insert'}"><spring:message code="common.insert" /></c:when><c:otherwise><spring:message code="common.update" /></c:otherwise></c:choose></h1>
			</div>
			<div class="block-content">
				<form:form commandName="writePopup" action="?act=write" enctype="multipart/form-data" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}' />" />
					<input type="hidden" name="returnQueryString" value="<c:out value='${searchPopupVO.queryString}' escapeXml='false' />"/>
					<input type="hidden" name="popId" value="${searchPopupVO.popId}"/>
					<fieldset>
						<legend class="sr-only">등록정보</legend>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="popCategory" class="control-label">* 분류</label></span>
							<div class="col-sm-10">
								<form:select path="popCategory" class="form-control">
								<form:option value="">+ 선택</form:option>
								<c:forEach var="result" items="${popCategoryList}" varStatus="status">
								<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
						</div>						
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="popTitle" class="control-label">* 제목</label></span>
							<div class="col-sm-10">
								<form:input path="popTitle" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">* 날짜제한</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="popPeriodYn" value="Y"/>날짜제한</label>
								<label class="radio-inline"><form:radiobutton path="popPeriodYn" value="N"/>제한없음</label>
							</div>
						</div>
						<div id="_periodYn">
							<div class="form-group">
								<span class="col-sm-2 text-right"><label class="control-label">게시시작일</label></span>
								<div class="col-sm-2">
									<form:input path="popStartDttm" class="form-control datepicker" />
								</div>
								<div class="col-sm-1">
									<form:select path="popStartHH" class="form-control">
									<c:forEach var="i" begin="0" end="23">
									    <form:option value="${i}">${i}시</form:option>
									</c:forEach>
									</form:select>
								</div>
								<div class="col-sm-1">
									<form:select path="popStartMM" class="form-control">
									<c:forEach var="i" begin="0" end="59">
									    <form:option value="${i}">${i}분</form:option>
									</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<span class="col-sm-2 text-right"><label class="control-label">게시종료일</label></span>
								<div class="col-sm-2">
									<form:input path="popEndDttm" class="form-control datepicker" />
								</div>
								<div class="col-sm-1">
									<form:select path="popEndHH" class="form-control">
									<c:forEach var="i" begin="0" end="23">
									    <form:option value="${i}">${i}시</form:option>
									</c:forEach>
									</form:select>
								</div>
								<div class="col-sm-1">
									<form:select path="popEndMM" class="form-control">
									<c:forEach var="i" begin="0" end="59">
									    <form:option value="${i}">${i}분</form:option>
									</c:forEach>
									</form:select>
								</div>
							</div>	
						</div>	
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">* 링크타겟</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="popLinkTarget" value="_SELF"/>자신</label>
								<label class="radio-inline"><form:radiobutton path="popLinkTarget" value="_BLANK"/>새창</label>
								<label class="radio-inline"><form:radiobutton path="popLinkTarget" value="_PARENT"/>부모창</label>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="popLinkUrl" class="control-label">링크주소</label></span>
							<div class="col-sm-10">
								<form:input path="popLinkUrl" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="popOrderNo" class="control-label">정렬순서</label></span>
							<div class="col-sm-10">
								<form:input path="popOrderNo" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">공유분류</label></span>
							<div class="col-sm-10">
								<c:forEach var="result" items="${popCategoryList}" varStatus="status">
								<label class="checkbox-inline"><form:checkbox path="popShareArr" value="${result.cddId}"/><c:out value="${result.cddName}"/></label>
								</c:forEach>
							</div>
						</div>	
						<div class="form-group">
							<span class="col-sm-2  text-right"><label for="popUseYn" class="control-label">사용유무</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="popUseYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="popUseYn" value="N"/>미사용</label>								
							</div>
						</div>
						<c:if test="${writePopup.popType eq 'P' || writePopup.popType eq 'p'}">
							<div class="form-group">
								<span class="col-sm-2 text-right"><label class="control-label">팝업창 위치</label></span>
								<div class="col-sm-2">
									<form:input path="popTop" class="form-control"/>
								</div>
								<div class="col-sm-2">
									<label class="radio-inline"><form:radiobutton path="popTopUnit" value="px"/>픽셀(px)</label>
									<label class="radio-inline"><form:radiobutton path="popTopUnit" value="%"/>퍼센트(%)</label>	
								</div>
								<div class="col-sm-2">
									<form:input path="popLeft" class="form-control"/>
								</div>
								<div class="col-sm-2">
									<label class="radio-inline"><form:radiobutton path="popLeftUnit" value="px"/>픽셀(px)</label>
									<label class="radio-inline"><form:radiobutton path="popLeftUnit" value="%"/>퍼센트(%)</label>	
								</div>							
							</div>
							<div class="form-group">
								<span class="col-sm-2 text-right"><label class="control-label">팝업창 크기</label></span>
								<div class="col-sm-2">
									<form:input path="popWidth" class="form-control"/>
								</div>
								<div class="col-sm-2">
									<label class="radio-inline"><form:radiobutton path="popWidthUnit" value="px"/>픽셀(px)</label>
									<label class="radio-inline"><form:radiobutton path="popWidthUnit" value="%"/>퍼센트(%)</label>	
								</div>
								<div class="col-sm-2">
									<form:input path="popHeight" class="form-control"/>
								</div>
								<div class="col-sm-2">
									<label class="radio-inline"><form:radiobutton path="popHeightUnit" value="px"/>픽셀(px)</label>
									<label class="radio-inline"><form:radiobutton path="popHeightUnit" value="%"/>퍼센트(%)</label>
								</div>
							</div>
							<div class="form-group">
								<span class="col-sm-2 text-right"><label class="control-label">* 스크롤</label></span>
								<div class="col-sm-10">
									<label class="radio-inline"><form:radiobutton path="popScrollAt" value="yes"/>예</label>
									<label class="radio-inline"><form:radiobutton path="popScrollAt" value="no"/>아니오</label>
									<label class="radio-inline"><form:radiobutton path="popScrollAt" value="auto"/>자동</label>
								</div>
							</div>
							<div class="form-group">
								<span class="col-sm-2 text-right"><label for="popCookieDay" class="control-label">* 쿠키일수</label></span>
								<div class="col-sm-10">
									<form:input path="popCookieDay" class="form-control" />
								</div>
							</div>
						</c:if>
						<c:if test="${writePopup.popType ne 'L' && writePopup.popType ne 'l'}">						
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="atchFile" class="control-label">이미지</label></span>
							<div class="col-sm-10">
								<input type="file" name="atchFile" id="atchFile" class="form-control" value=""/>
								<c:if test="${not empty writePopup.popFile}">
									<input type="checkbox" name="deleteFile" value="Y" />삭제
									<img src="${CONTEXT_PATH}/imgview.do?file=<c:out value='${writePopup.popFile}'/>" width="70" alt=""/>
								</c:if>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="popFileAlt" class="control-label">대체텍스트</label></span>
							<div class="col-sm-10">
								<form:textarea path="popFileAlt" class="form-control" />
								<div class="help-block">
								이미지에 대한 대체텍스트 입니다.
								</div>
							</div>
						</div>
						</c:if>
						<c:if test="${writePopup.popType eq 'P' || writePopup.popType eq 'p' || writePopup.popType eq 'Z' || writePopup.popType eq 'z'}">
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="popContent" class="control-label">내용</label></span>
							<div class="col-sm-10">
								<form:textarea path="popContent" class="form-control" />
								<div class="help-block">
									HTML 및 script를 사용 하실 수 있습니다.<br/>
									이미지맵 사용 예제<br/>
									&lt;map id="{map}" name="이름"&gt;<br/>
									&lt;area href="경로" shape="모양" coords="위치값"&gt;<br/>
									&lt;area href="경로" shape="모양" coords="위치값"&gt;<br/>
									&lt;/map&gt;<br/>
								</div>								
							</div>
						</div>
						</c:if>
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<a href="?<c:out value='${searchPopupVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list" /></a>
							</div>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(function()
{
	$("#writePopup").validate();
	
	$("input[name='popPeriodYn']").on("change", function() {
		changePriodYn($(this).val());
	});
	
	function changePriodYn(val) {
		if (val == "Y") {
			$("#_periodYn").find("input").attr("disabled", false);
			$("#_periodYn").find("select").attr("disabled", false);
		} else {
			$("#_periodYn").find("input").attr("disabled", true);
			$("#_periodYn").find("select").attr("disabled", true);
		}
	}
	
	changePriodYn('<c:out value="${writePopup.popPeriodYn}"/>');
});
</script>