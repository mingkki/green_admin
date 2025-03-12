<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<div class="row">
	<div class="col-lg-2">
		<div class="block">
			<div class="block-content">
				<link rel="stylesheet" href="${CONTEXT_PATH}/assets/js/plugins/dtree/dtree.css">
				<script type="text/javascript" src="${CONTEXT_PATH}/assets/js/plugins/dtree/dtree.js"></script>
				<script type="text/javascript">
				var d = new dTree('d');
				d.add(<c:out value="${rootMenuId}"/>,-1,"홈", "${REQUEST_URI}");
				<c:forEach var="result" items="${allAdminMenuList}" varStatus="status">
				<c:if test="${result.ameParntsId > 0}">
				d.add(<c:out value='${result.ameId}' />, <c:out value='${result.ameParntsId}' />, "<c:out value='${result.ameName}' />", "?ameId=<c:out value='${result.ameId}' />", "", "", "");
				</c:if>
				</c:forEach>
				document.write(d);
				</script>
			</div>
			<div class="block-footer">
				<a href="?act=move" class="btn btn-default"><i class="fa fa-arrows"></i> 메뉴이동</a>
			</div>
		</div>
	</div>
	<div class="col-lg-10">
		<div class="block">
			<div class="block-head">
				<c:choose>
					<c:when test="${searchAdminMenuVO.ameParntsId > 0}"><h1>서브메뉴 만들기</h1></c:when>
					<c:when test="${searchAdminMenuVO.ameId > 0}"><h1>메뉴 등록정보</h1></c:when>
					<c:otherwise><h1>최상위메뉴 만들기</h1></c:otherwise>
				</c:choose>
			</div>
			<div class="block-content">
				<form:form commandName="writeAdminMenu" action="?act=write" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}'/>" />
					<input type="hidden" name="ameId" value="<c:out value='${searchAdminMenuVO.ameId}'/>"/>
					<input type="hidden" name="ameParntsId" value="<c:out value='${searchAdminMenuVO.ameParntsId}'/>"/>
					<fieldset>
						<legend class="sr-only">메뉴관리</legend>
						<c:if test="${searchAdminMenuVO.ameId > 0 || searchAdminMenuVO.ameParntsId > 0}">
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">상위메뉴</label></span>
							<div class="col-sm-10">
								<p class="form-control-static">
									홈
									<c:forEach var="result" items="${parentAdminMenuList}" varStatus="status">
									&gt; <c:out value="${result.ameName}" />
									</c:forEach>
								</p>
							</div>
						</div>
						</c:if>
						<div class="form-group">
							<span class="col-sm-2 text-right"><span class="required">*</span><label for="ameName" class="control-label">메뉴명</label></span>
							<div class="col-sm-10">
								<c:choose>
									<c:when test="${command eq 'insert'}">
										<form:input path="ameName" class="form-control required" />
										<div class="help-block">
											복수의 메뉴를 한번에 등록하시려면 메뉴명을 콤마(,)로 구분해 주세요. 보기)회사소개,커뮤니티,고객센터
										</div>
									</c:when>
									<c:otherwise>
										<div class="input-group">
											<form:input path="ameName" class="form-control" />
											<c:if test="${writeAdminMenu.ameId > 0}">
											<span class="input-group-btn"><a href="?ameParntsId=<c:out value='${writeAdminMenu.ameId}' />" class="btn btn-default btn-sm"><i class="fa fa-plus"></i> 하위메뉴등록</a></span>
											</c:if>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><span class="required">*</span><label for="amePname" class="control-label">프로그램명</label></span>
							<div class="col-sm-10">
								<form:input path="amePname" class="form-control required" />
							</div>
						</div>						
						<div class="form-group">
							<span class="col-sm-2 text-right"><span class="required">*</span><label class="control-label">메뉴타입</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="ameType" value="LINK_IN" />내부링크</label>
								<label class="radio-inline"><form:radiobutton path="ameType" value="LINK_OUT" />외부링크</label>
								<div id="linkUrlLayer">
									<div class="input-group mg-t-xs mg-b-xs">
										<span class="input-group-addon">URL</span>
										<form:input path="ameLinkUrl" class="form-control" />
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">링크타겟</label></span>
							<div class="col-sm-10">
								<label class="radio-inline">
									<form:radiobutton path="ameLinkTarget" value="_SELF" />현재창
								</label>
								<label class="radio-inline">
									<form:radiobutton path="ameLinkTarget" value="_BLANK" />새창
								</label>
								<label class="radio-inline">
									<form:radiobutton path="ameLinkTarget" value="_POPUP" />팝업
								</label>
								<div class="input-group mg-t-sm" id="popupParamLayer" style="display:none;">
									<span class="input-group-addon">팝업옵션</span>
									<form:input path="amePopupParam" class="form-control" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="ameShowYn" class="control-label">출력여부</label></span>
							<div class="col-sm-10">
								<form:select path="ameShowYn" class="form-control">
								<form:option value="Y">출력함</form:option>
								<form:option value="N">출력안함</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="ameUseYn" class="control-label">사용여부</label></span>
							<div class="col-sm-10">
								<form:select path="ameUseYn" class="form-control">
								<form:option value="Y">사용함</form:option>
								<form:option value="N">사용안함</form:option>
								</form:select>
							</div>
						</div>
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<c:if test="${command eq 'update'}">
								<button type="button" class="btn btn-default pd-l-lg pd-r-lg" onclick="post_delete('', 'act=delete&ameId=<c:out value='${writeAdminMenu.ameId}' />'); return false;"><spring:message code="common.delete" /></button>
								</c:if>
							</div>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(function() {

	$("#writeAdminMenu").validate({});

	function ameLinkTargetSelect(value) {
		$("#popupParamLayer").hide();
		switch(value) {
			case "_POPUP":
				$("#popupParamLayer").show();
				$("#amePopupParam").val("<c:out value='${writeAdminMenu.amePopupParam}' />");
				break;
			default:
				$("#amePopupParam").val("");
				break;
		}
	}

	$("input[name='ameLinkTarget']").change(function() {
		ameLinkTargetSelect($(this).val());
	});

	ameLinkTargetSelect("<c:out value='${writeAdminMenu.ameLinkTarget}' />");
});
</script>