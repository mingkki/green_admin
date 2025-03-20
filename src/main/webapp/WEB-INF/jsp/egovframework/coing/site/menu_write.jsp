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
				<form action="${REQUEST_URI}" method="get" class="form-search" role="form">
					<fieldset>
						<legend class="sr-only">검색 폼</legend>
						<div class="form-group">
							<select name="sinId" id="sinId" class="form-control" onchange="this.form.submit();">
							<option value="">+ 사이트선택</option>
							<c:forEach var="result" items="${siteList}" varStatus="status">
							<option value="${result.sinId}" <c:if test="${searchSiteMenuVO.sinId eq result.sinId}">selected="selected"</c:if>><c:out value="${result.sinName}" /></option>
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
				d.add(<c:out value="${rootMenuId}"/>,-1,"홈", "${REQUEST_URI}");
				<c:forEach var="result" items="${allSiteMenuList}" varStatus="status">
				<c:if test="${result.smeParntsId > 0}">
				d.add(<c:out value='${result.smeId}' />, <c:out value='${result.smeParntsId}' />, "<c:out value='${result.smeName}' />", "?sinId=<c:out value='${result.sinId}' />&smeId=<c:out value='${result.smeId}' />", "", "", "");
				</c:if>
				</c:forEach>
				document.write(d);
				</script>
			</div>
			<div class="block-footer">
				<a href="?act=move&amp;sinId=<c:out value='${writeSiteMenu.sinId}' />" class="btn btn-default"><i class="fa fa-arrows"></i> 메뉴이동</a>
				<button type="button" class="btn btn-default" onclick="post_url('', 'act=distribute&sinId=<c:out value='${writeSiteMenu.sinId}' />&smeId=<c:out value='${writeSiteMenu.smeId}' />', '배포하시겠습니까?'); return false;"><i class="fa fa-cloud-download"></i> 배포</button>
			</div>
		</div>
	</div>
	<div class="col-lg-10">
		<div class="block">
			<div class="block-head">
				<c:choose>
					<c:when test="${searchSiteMenuVO.smeParntsId > 0}"><h1>서브메뉴 만들기</h1></c:when>
					<c:when test="${searchSiteMenuVO.smeId > 0}"><h1>메뉴 등록정보</h1></c:when>
					<c:otherwise><h1>최상위메뉴 만들기</h1></c:otherwise>
				</c:choose>
			</div>
			<div class="block-content">
				<form:form commandName="writeSiteMenu" action="?act=write" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}'/>" />
					<input type="hidden" name="sinId" value="<c:out value='${searchSiteMenuVO.sinId}'/>"/>
					<input type="hidden" name="smeId" value="<c:out value='${searchSiteMenuVO.smeId}'/>"/>
					<input type="hidden" name="smeParntsId" value="<c:out value='${searchSiteMenuVO.smeParntsId}'/>"/>
					<fieldset>
						<legend class="sr-only">메뉴관리</legend>
						<c:if test="${searchSiteMenuVO.smeId > 0 || searchSiteMenuVO.smeParntsId > 0}">
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">상위메뉴</label></span>
							<div class="col-sm-10">
								<p class="form-control-static">
									홈
									<c:forEach var="result" items="${parentSiteMenuList}" varStatus="status">
									&gt; <c:out value="${result.smeName}" />
									</c:forEach>
								</p>
							</div>
						</div>
						</c:if>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="smeName" class="control-label">* 메뉴명</label></span>
							<div class="col-sm-10">
								<c:choose>
									<c:when test="${command eq 'insert'}">
										<form:input path="smeName" class="form-control required" />
										<div class="help-block">
											복수의 메뉴를 한번에 등록하시려면 메뉴명을 콤마(,)로 구분해 주세요. 보기)회사소개,커뮤니티,고객센터
										</div>
									</c:when>
									<c:otherwise>
										<div class="input-group">
											<form:input path="smeName" class="form-control" />
											<c:if test="${writeSiteMenu.smeId > 0}">
											<span class="input-group-btn"><a href="?sinId=<c:out value='${writeSiteMenu.sinId}' />&amp;smeParntsId=<c:out value='${writeSiteMenu.smeId}' />" class="btn btn-default btn-sm"><i class="fa fa-plus"></i> 하위메뉴등록</a></span>
											</c:if>
										</div>
									</c:otherwise>
								</c:choose>
								<c:if test="${writeSiteMenu.smeParntsId eq rootMenuId || (command eq 'insert' && searchSiteMenuVO.smeParntsId eq 0)}">
								<div>
									<label class="checkbox-inline"><form:checkbox path="smeMainmenuYn" value="Y"/> 상단메뉴 표출</label>
								</div>
								</c:if>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="smeContent">메뉴 안내</label></span>
							<div class="col-sm-10">
								<form:input path="smeContent" class="form-control" />
							</div>
						</div>						
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">* 메뉴 디자인</label></span>
							<div class="col-sm-10">
								<div class="input-group">
									<span class="input-group-addon">테마</span>
									<form:input path="smeTheme" class="form-control" />
								</div>
								<div class="input-group mg-t-xs">
									<span class="input-group-addon">레이아웃</span>
									<form:input path="smeLayout" class="form-control" />
								</div>
								<c:if test="${command eq 'update'}">
								<div class="help-block">
									<label class="checkbox-inline"><form:checkbox path="updateChildrenDesign" value="Y"/>하위메뉴 일괄 적용</label>
								</div>
								</c:if>
							</div>
						</div>	
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="smeDescription">메뉴 설명</label></span>
							<div class="col-sm-10">
								<form:input path="smeDescription" class="form-control" />
								<div class="help-block">
									페이지에 대한 요약설명. meta 태그의 description에 정의됨.
								</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="smeKeyword">메뉴 키워드</label></span>
							<div class="col-sm-10">
								<form:input path="smeKeyword" class="form-control" />
								<div class="help-block">
									페이지에 대한 키워드. meta 태그의 keyword에 정의됨. 여러개는 쉼표로 구분.
								</div>
							</div>
						</div>					
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="smeUseYn" class="control-label">사용 유무</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="smeUseYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="smeUseYn" value="N"/>미사용</label>
							</div>
						</div>
						<div class="form-group" id="showAtLayer">
							<span class="col-sm-2 text-right"><label for="smeShowYn" class="control-label">메뉴 표출</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="smeShowYn" value="Y"/>표출</label>
								<label class="checkbox-inline">(<form:checkbox path="smeLoginViewYn" value="Y"/> 로그인한 이용자에게만 표출)</label>
								<label class="radio-inline"><form:radiobutton path="smeShowYn" value="N"/>숨김</label>
							</div>
						</div>
						<div class="bd-t pd-t-md"></div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label"><span class="required" title="필수입력항목">*</span> 메뉴 종류</label></span>
							<div class="col-sm-10">
								<div class="mg-b-xs">
									<label class="radio-inline"><form:radiobutton path="smeType" value="HTML"/>콘텐츠</label>
									<label class="radio-inline"><form:radiobutton path="smeType" value="MENU_LINK"/>메뉴연결</label>
									<label class="radio-inline"><form:radiobutton path="smeType" value="BOARD"/>게시판</label>
									<label class="radio-inline"><form:radiobutton path="smeType" value="PROGRAM"/>프로그램</label>
									<label class="radio-inline"><form:radiobutton path="smeType" value="LINK_IN"/>직접입력(내부링크)</label>
									<label class="radio-inline"><form:radiobutton path="smeType" value="LINK_OUT"/>직접입력(외부링크)</label>
								</div>
							</div>
						</div>
						<div  id="menu_link_layer" style="display:none;">
							<div class="form-group">
								<span class="col-sm-2 text-right"><label class="control-label"><span class="required" title="필수입력항목">*</span> 연결 메뉴</label></span>
								<div class="col-sm-10">
									<form:select path="smeLinkSmeId" class="form-control required">
									<form:option value="0">+ 선택</form:option>									
									<c:forEach var="result" items="${treeSiteMenuList}" varStatus="status">
									<c:choose>
										<c:when test="${result.smeId eq searchSiteMenuVO.smeId}"><form:option value="${result.smeId}" disabled="true"><c:out value="${result.smeName}" /></form:option></c:when>
										<c:otherwise><form:option value="${result.smeId}"><c:out value="${result.smeName}" /></form:option></c:otherwise>
									</c:choose>									
									</c:forEach>
									</form:select>
								</div>
							</div>
						</div>
						<div id="board_link_layer" style="display:none;">
							<div class="form-group">
								<span class="col-sm-2 text-right"><label class="control-label"><span class="required" title="필수입력항목">*</span> 연결 게시판</label></span>
								<div class="col-sm-10">
									<div class="input-group">
										<form:select path="smeLinkBrdId" class="form-control">
										<form:option value="">+ 게시판을 선택해 주세요.</form:option>
										<c:forEach var="result" items="${boardList}" varStatus="status">
										<form:option value="${result.brdId}">[<c:out value="${result.brdId}"/>] <c:out value="${result.brdName}" /></form:option>
										</c:forEach>
										</form:select>										
										<span class="input-group-btn"><a href="?sinId=<c:out value='${searchSiteMenuVO.sinId}'/>&amp;act=search&amp;type=board" onclick="window.open(this.href, 'board', 'width=600, height=800, scrollbars=auto'); return false;" class="btn btn-default btn-sm">게시판 검색</a></span>
									</div>																
								</div>							
							</div>
						</div>
						<div id="program_link_layer" style="display:none;">
							<div class="form-group">
								<span class="col-sm-2 text-right"><label class="control-label"><span class="required" title="필수입력항목">*</span> 연결 프로그램</label></span>
								<div class="col-sm-10">							
									<div class="input-group">
										<form:select path="smeLinkPrgId" class="form-control">
										<form:option value="0">+ 프로그램을 선택해 주세요.</form:option>
										<c:forEach var="result" items="${programList}" varStatus="status">
										<form:option value="${result.prgId}"><c:out value="${result.prgName}" /></form:option>
										</c:forEach>
										</form:select>										
										<span class="input-group-btn"><a href="?sinId=<c:out value='${searchSiteMenuVO.sinId}'/>&amp;act=search&amp;type=program" onclick="window.open(this.href, 'board', 'width=600, height=800, scrollbars=auto'); return false;" class="btn btn-default btn-sm">프로그램 검색</a></span>
									</div>		
								</div>	
							</div>
						</div>
						<div id="link_url_layer" style="display:none;">
							<div class="form-group">
								<span class="col-sm-2 text-right"><label class="control-label">링크URL</label></span>
								<div class="col-sm-10">
									<form:input path="smeLinkUrl" class="form-control" />
									<div class="help-block">
										내부링크 입력예 : /test1/test2?a=1&b=1<br/>
										외부링크 입력예 : http://allu.kr/?a=1&b=1
									</div>
								</div>
							</div>
						</div>
						<div class="bd-t pd-t-md"></div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label"><span class="required" title="필수입력항목">*</span> 메뉴 타겟</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="smeLinkTarget" value="_SELF"/>현재창</label>
								<label class="radio-inline"><form:radiobutton path="smeLinkTarget" value="_BLANK"/>새창</label>
								<label class="radio-inline"><form:radiobutton path="smeLinkTarget" value="_POPUP"/>팝업창</label>
							</div>
						</div>
						<div  id="link_target_layer" style="display:none;">
							<div class="form-group">
								<span class="col-sm-2 text-right"><label class="control-label">팝업창 속성</label></span>
								<div class="col-sm-10">
									<div class="input-group">
										<span class="input-group-addon">왼쪽 위치</span>
										<form:input path="smePopupLeft" class="form-control" />
									</div>
									<div class="input-group mg-t-xs">
										<span class="input-group-addon">상단 위치</span>
										<form:input path="smePopupTop" class="form-control" />
									</div>
									<div class="input-group mg-t-xs">
										<span class="input-group-addon">가로 사이즈</span>
										<form:input path="smePopupWidth" class="form-control" />
									</div>
									<div class="input-group mg-t-xs">
										<span class="input-group-addon">세로 사이즈</span>
										<form:input path="smePopupHeight" class="form-control" />
									</div>
								</div>
							</div>
							<div class="form-group">
								<span class="col-sm-2 text-right"><label class="control-label">팝업창 스크롤</label></span>
								<div class="col-sm-10">
									<label class="radio-inline"><form:radiobutton path="smePopupScrollAt" value="yes"/>사용</label>
									<label class="radio-inline"><form:radiobutton path="smePopupScrollAt" value="no"/>미사용</label>
									<label class="radio-inline"><form:radiobutton path="smePopupScrollAt" value="auto"/>자동</label>
								</div>
							</div>
							<div class="pd-t-xs"></div>
						</div>
					<div class="bd-t pd-t-md"></div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">메뉴표출 그룹</label></span>
							<div class="col-sm-10">
								<c:forEach var="result" items="${groupList}" varStatus="status">
								<label class="checkbox-inline">
									<form:checkbox path="smeLimitViewArr" value="${result.grpId}"/><c:out value="${result.grpName}"/>
								</label>
								</c:forEach>
								<div class="help-block">
									메뉴표출 그룹을 체크하지 않는 경우 전체 그룹이 접근이 가능합니다.
								</div>
							</div>
						</div>	
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<c:if test="${command eq 'update'}">
								<button type="button" class="btn btn-default pd-l-lg pd-r-lg" onclick="post_delete('', 'act=delete&sinId=<c:out value='${writeSiteMenu.sinId}' />&smeId=<c:out value='${writeSiteMenu.smeId}' />'); return false;"><spring:message code="common.delete" /></button>
								</c:if>
							</div>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="/cms/assets/js/plugins/chained/jquery.chained.min.js"></script>
<script type="text/javascript" src="/cms/assets/js/plugins/chained/jquery.chained.remote.min.js"></script>
<script type="text/javascript"> 
$(function()
{
	$('#layout');

	function menu_type_select(value)
	{
		$("#menu_link_layer").hide();
		$("#board_link_layer").hide();
		$("#program_link_layer").hide();
		$("#link_url_layer").hide();

		switch(value)
		{
			case "HTML":
				break;
			case "MENU_LINK":
				$("#menu_link_layer").show();
				break;
			case "BOARD":
				$("#board_link_layer").show();
				break;
			case "PROGRAM":
				$("#program_link_layer").show();
				break;
			case "LINK_IN":
			case "LINK_OUT":
				$("#link_url_layer").show();
				break;				
		}
	}

	$("input[name='smeType']").change(function()
	{
		menu_type_select($(this).val());
	});
	menu_type_select("<c:out value='${writeSiteMenu.smeType}'/>");


	function link_target_select(value)
	{
		if (value == '_POPUP')
		{
			$("#link_target_layer").show();
		}
		else
		{
			$("#link_target_layer").hide();
		}
	}

	$("input[name='smeLinkTarget']").change(function()
	{
		link_target_select($(this).val());
	});
	link_target_select("<c:out value='${writeSiteMenu.smeLinkTarget}'/>");

});


//컨텐츠 관리자지정
function appointStaffAtSelect(value) {
	console.log(value);
	$('#DeptLayer').hide();
	if (value == 'Y') {

		$('#DeptLayer').show();
	}
}
$("select[name='appointStaffAt']").change(function() {

	appointStaffAtSelect($(this).val());
});
appointStaffAtSelect("<c:out value='${writeSiteMenu.appointStaffAt}' />")



function completeSearchBoard(data) {
	$('#smeLinkBrdId').val(data.brdId);
}

function completeSearchProgram(data) {
	$('#smeLinkPrgId').val(data.prgId);
}


/* function changeOrdId( ordId )
{
	console.log('ordId', ordId );	
	if( isEmpty(ordId) )
	{
		 alert('111');
		return;
	}	
	$('input[name=orsId]').empty();
	$('input[name=orsId]').append('<option value="">+ 담당자를 선택해 주세요</option>')
	$.ajax({            
	   type : "GET",
       url : baseUrl + '/site/menus.do?act=getOrganizationStaffList',
       dataType : "json",   
       data: {
    	   ordId: ordId
       },
       success : function(result){
    	   	console.log( result );
    	   	if( !isEmpty(result) )
    		{
    	   		for( var i = 0; i < result.length; i++)
    	   		{
    	   			console.log(result[i]);	    	   			
    	   			$('input[name=orsId]').append('<option value="' + result[i]['id'] + '">' + result[i]['name'] + '</option>')
    	   		}
    		}
        }, 
        error : function(err){
          console.log( err );
          alert('오류발생[' + err + ']')
        }
    });

	
} */
</script>