<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--@elvariable id="writeVO" type="egovframework.coing.exchange.vo.ExchangeVO"--%>
<%--@elvariable id="ADMIN_MENU_VO" type="egovframework.coing.cmm.vo.AdminMenuVO"--%>

<div class="row">
  <div class="col-lg-12">
    <div class="block">
      <div class="block-head">
        <h1><c:out value="${ADMIN_MENU_VO.amePname}"/> <c:choose><c:when test="${command == 'insert'}"><spring:message code="common.insert"/></c:when><c:otherwise><spring:message code="common.update"/></c:otherwise></c:choose></h1>
      </div>
      <div class="block-content">
        <form:form commandName="writeVO" action="?act=write" class="form-horizontal" role="form" modelAttribute="writeVO">
          <input type="hidden" name="command" id="command" value="<c:out value='${command}' />"/>
          <input type="hidden" name="returnQueryString" value="<c:out value='${writeVO.queryString}' escapeXml='false' />"/>
          <c:if test="${command eq 'update'}">
            <input type="hidden" name="id" value="${writeVO.id}"/>
          </c:if>
          <fieldset>
            <legend class="sr-only">등록정보</legend>
            <div class="form-group">
              <label for="emd" class="col-sm-2 control-label">읍면동 정보</label>
              <div class="col-sm-6">
                <form:select path="emd" items="${emdList}" itemValue="cddId" itemLabel="cddName" cssClass="form-control" id="emd" required="required"/>
              </div>
            </div>
            <div class="form-group">
              <label for="place" class="col-sm-2 control-label">교환소 위치</label>
              <div class="col-sm-6">
                <form:input path="place" cssClass="form-control" id="place"/>
              </div>
            </div>
            <div class="form-group">
              <label for="address" class="col-sm-2 control-label">주소</label>
              <div class="col-sm-6">
                <form:input path="address" cssClass="form-control" id="address" required="required" readonly="true"/>
              </div>
              <div class="col-sm-1">
                <button type="button" class="btn btn-default postcode">우편번호 찾기</button>
              </div>
            </div>
            <div class="form-group">
              <label for="open" class="col-sm-2 control-label">운영시간</label>
              <div class="col-sm-6">
                <form:input path="open" cssClass="form-control" id="address"/>
              </div>
            </div>
            <div class="form-group">
              <label for="close" class="col-sm-2 control-label">휴무</label>
              <div class="col-sm-6">
                <form:input path="close" cssClass="form-control" id="address"/>
              </div>
            </div>
            <div class="form-group">
              <label for="close" class="col-sm-2 control-label">위도</label>
              <div class="col-sm-6">
                <form:input path="lat" cssClass="form-control" id="lat"/>
              </div>
              <div class="col-sm-2">
                <button type="button" class="btn btn-default" id="findLatLng">위도 경도 찾기</button>
              </div>
            </div>
            <div class="form-group">
              <label for="close" class="col-sm-2 control-label">경도</label>
              <div class="col-sm-6">
                <form:input path="lng" cssClass="form-control" id="lng"/>
              </div>
            </div>
            <div class="bd-t mg-t-md pd-t-md"></div>
            <div class="form-group">
              <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert"/></button>
                <a href="?<c:out value='${writeVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list"/></a>
              </div>
            </div>
          </fieldset>
        </form:form>
      </div>
    </div>
  </div>
</div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
  $('.postcode').on('click', function() {
    new daum.Postcode({
      oncomplete: function(data) {
        $('#address').val(data.address);
      }
    }).open();
  });

  $('#findLatLng').on('click', function() {
    $(this).prop('disabled', true);
    const address = $('#address').val();

    if(address === '') {
      alert('주소를 입력해주세요.');
      return;
    }

    fetch(`\${baseUrl}/coord.do?act=getCoord&address=\${address}`)
      .then(response => response.json())
      .then(data => {
        $('#lat').val(data.lat);
        $('#lng').val(data.lng);
      }).catch(error => {
      $('#lat').val(0.0);
      $('#lng').val(0.0);
      console.error('Error:', error);
    }).finally(() => {
      $(this).prop('disabled', false);
    });
  });
</script>