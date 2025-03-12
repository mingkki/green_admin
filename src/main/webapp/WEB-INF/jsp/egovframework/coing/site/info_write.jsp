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
				<form:form commandName="writeSiteInfo" action="?act=write" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}' />" />
					<input type="hidden" name="returnQueryString" value="<c:out value='${searchSiteInfoVO.queryString}' escapeXml='false' />"/>
					<c:if test="${command eq 'update'}">
					<input type="hidden" name="sinId" value="${searchSiteInfoVO.sinId}"/>
					</c:if>
					<fieldset>
						<legend class="sr-only">등록정보</legend>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="sinId" class="control-label">* 사이트 코드</label></span>
							<div class="col-sm-10">
								<c:choose>
									<c:when test="${command eq 'insert'}">
										<form:input path="sinId" class="form-control required" maxlength="20" />
										<div class="help-block">영문과 숫자만 입력하실 수 있습니다. 첫글자는 영문자</div>
									</c:when>
									<c:otherwise>
										<form:input path="sinId" class="form-control" disabled="true" />
									</c:otherwise>
								</c:choose>
																
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="sinName" class="control-label">* 사이트명</label></span>
							<div class="col-sm-10">
								<form:input path="sinName" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="sinTitle" class="control-label">* 사이트 타이틀</label></span>
							<div class="col-sm-10">
								<form:input path="sinTitle" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="sinLang" class="control-label">* 사이트 언어</label></span>
							<div class="col-sm-10">
								<form:select path="sinLang" class="form-control required">							
								<c:forEach var="result" items="${langList}" varStatus="status">
								<form:option value="${result.cddId}"><c:out value="${result.cddName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
						</div>						
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="sinName" class="control-label">* 사이트 디자인</label></span>
							<div class="col-sm-10">
								<div class="input-group">
									<span class="input-group-addon">테마</span>
									<form:input path="sinTheme" class="form-control" />
								</div>
								<div class="input-group mg-t-xs">
									<span class="input-group-addon">레이아웃</span>
									<form:input path="sinLayout" class="form-control" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="sinDomain" class="control-label">* 도메인</label></span>
							<div class="col-sm-10">
								<form:input path="sinDomain" class="form-control required" />
								<div class="help-block">
									http는 제외하고 입력해 주세요. 예) www.naver.com 또는 www.naver.com/main
								</div>
							</div>
						</div>						
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="sinDescription" class="control-label">사이트 설명</label></span>
							<div class="col-sm-10">
								<form:input path="sinDescription" class="form-control" />
								<div class="help-block">페이지에 대한 요약설명. meta 태그의 description에 정의됨.</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="sinKeyword" class="control-label">사이트 키워드</label></span>
							<div class="col-sm-10">
								<form:input path="sinKeyword" class="form-control" />
								<div class="help-block">페이지에 대한 키워드. meta 태그의 keyword에 정의됨. 여러개는 쉼표로 구분.</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="sinCopyright" class="control-label">Copyright</label></span>
							<div class="col-sm-10">
								<form:input path="sinCopyright" class="form-control" />
							</div>
						</div>						
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="sinTel" class="control-label">전화번호</label></span>
							<div class="col-sm-10">
								<form:input path="sinTel" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="sinFax" class="control-label">팩스번호</label></span>
							<div class="col-sm-10">
								<form:input path="sinFax" class="form-control" />
							</div>
						</div>		
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">주소</label></span>
							<div class="col-sm-10">
								<div class="input-group">
									<form:input path="sinZipcd" class="form-control" />
									<span class="input-group-btn"><button type="button" onclick="openDaumPostcode();" class="btn btn-default btn-sm">우편번호 검색</button></span>
								</div>
								<div class="input-group mg-t-xs">
									<span class="input-group-addon">주소</span>
									<form:input path="sinAddress1" class="form-control" />									
								</div>
								<div class="input-group mg-t-xs">
									<span class="input-group-addon">상세주소</span>
									<form:input path="sinAddress2" class="form-control" />									
								</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">위/경도</label></span>
							<div class="col-sm-10">
								<div class="input-group">
									<span class="input-group-addon">위도</span>
									<form:input path="sinMapLng" class="form-control" />									
								</div>
								<div class="input-group mg-t-xs">
									<span class="input-group-addon">경도</span>
									<form:input path="sinMapLat" class="form-control" />									
								</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">담당자</label></span>
							<div class="col-sm-10">
								<div class="input-group">
									<span class="input-group-addon">이름</span>
									<form:input path="sinChargeNm" title="담당자 이름" class="form-control" />									
								</div>
								<div class="input-group mg-t-xs">
									<span class="input-group-addon">연락처</span>
									<form:input path="sinChargeTel" title="담당자 연락처" class="form-control" />									
								</div>
								<div class="input-group mg-t-xs">
									<span class="input-group-addon">이메일</span>
									<form:input path="sinChargeEmail" title="담당자 이메일" class="form-control" />									
								</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="sinPermitView">접근가능 등급</label></span>
							<div class="col-sm-10">
								<form:select path="sinPermitView" class="form-control">
								<form:option value="0">전체</form:option>								
								<c:forEach var="result" items="${levelList}" varStatus="status">
								<form:option value="${result.lvlId}"><c:out value="${result.lvlName}"/></form:option>
								</c:forEach>
								</form:select>
								<div class="help-block">
									<label class="radio-inline"><form:radiobutton path="sinPermitViewGubun" value="1"/>등급이상</label>
									<label class="radio-inline"><form:radiobutton path="sinPermitViewGubun" value="2"/>동일등급</label>									
								</div>				
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">접근가능 그룹</label></span>
							<div class="col-sm-10">
								<c:forEach var="result" items="${groupList}" varStatus="status">
								<label class="checkbox-inline">
									<form:checkbox path="sinLimitViewArr" value="${result.grpId}"/><c:out value="${result.grpName}"/>
								</label>
								</c:forEach>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="sinHeadHtml" class="control-label">상단 HTML</label></span>
							<div class="col-sm-10">
								<form:textarea path="sinHeadHtml" rows="7" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="sinTailHtml" class="control-label">하단 HTML</label></span>
							<div class="col-sm-10">
								<form:textarea path="sinTailHtml" rows="7" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">IP 체크</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="sinIpGubun" value="P"/>내용 허용</label>
								<label class="radio-inline"><form:radiobutton path="sinIpGubun" value="L"/>내용 차단</label>
								<label class="radio-inline"><form:radiobutton path="sinIpGubun" value="N"/>사용 안함</label>							
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="sin_check_ip">IP 내역</label></span>
							<div class="col-sm-10">
								<form:textarea path="sinCheckIp" class="form-control" />
								<div class="help-block">
								IP 내역을 입력하며 여러건은 엔터로 구분 합니다. 예) 123.123.123.123
								</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="sinOrderNo" class="control-label">순서</label></span>
							<div class="col-sm-10">
								<form:input path="sinOrderNo" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2  text-right"><label for="sinUseYn" class="control-label">사용유무</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="sinUseYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="sinUseYn" value="N"/>미사용</label>								
							</div>
						</div>
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<a href="?<c:out value='${searchSiteInfoVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list" /></a>
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
	$("#writeSiteInfo").validate();
});
</script>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript">
function openDaumPostcode() {
	new daum.Postcode({
		oncomplete: function(data) {
			document.getElementById('sinZipcd').value = data.zonecode;
			document.getElementById('sinAddress1').value = data.address;
			document.getElementById('sinAddress2').focus();
			
			searchLatLng()
		}
	}).open();
}

function searchLatLng() {
	var locAdres = document.getElementById('sinAddress1').value;
	console.log(locAdres);
	$.ajax({
		url:'http://maps.googleapis.com/maps/api/geocode/json?sensor=false&language=ko&address='+encodeURIComponent(locAdres),
		dataType: "json",
		success:function(data) {
	        if (data.results.length > 0) {
	            var lat = data.results[0].geometry.location.lat;
	            var lng = data.results[0].geometry.location.lng;
	            document.getElementById('sinMapLat').value = lat;
	            document.getElementById('sinMapLng').value = lng;
	        } else {
	            alert('검색된 위치 정보가 없습니다.');
	            document.getElementById('sinMapLat').value = "0.0";
	            document.getElementById('sinMapLng').value = "0.0";
	        }
		}
	});		
}
</script>