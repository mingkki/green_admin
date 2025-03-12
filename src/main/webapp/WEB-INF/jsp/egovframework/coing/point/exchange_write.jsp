<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="writeVO" type="egovframework.coing.point.vo.PointExchangeVO"--%>
<div class="row">
  <div class="col-lg-12">
    <div class="block">
      <div class="block-head">
        <h1><c:out value="${ADMIN_MENU_VO.amePname}"/> <c:choose><c:when test="${command == 'insert'}"><spring:message code="common.insert"/></c:when><c:otherwise><spring:message code="common.update"/></c:otherwise></c:choose></h1>
      </div>
      <div class="block-content">
        <form:form commandName="writeVO" action="?act=write" class="form-horizontal" role="form" modelAttribute="writeVO">
          <c:set var="isConfirm" value="${writeVO.status eq 'CONFIRM'}"/>
          <input type="hidden" name="command" id="command" value="<c:out value='${command}' />"/>
          <input type="hidden" name="returnQueryString" value="<c:out value='${writeVO.queryString}' escapeXml='false' />"/>
          <c:if test="${command eq 'update'}">
            <input type="hidden" name="id" value="${writeVO.id}"/>
          </c:if>
          <fieldset>
            <legend class="sr-only">등록정보</legend>
            <div class="form-group">
              <label for="status" class="col-sm-2 control-label">상태</label>
              <div class="col-sm-10">
                <c:choose>
                  <c:when test="${isConfirm}">
                    <p class="form-control-static">
                        ${writeVO.statusNm}
                    </p>
                  </c:when>
                  <c:otherwise>
                    <label class="radio-inline"><form:radiobutton path="status" value="WAIT"/>대기중</label>
                    <label class="radio-inline"><form:radiobutton path="status" value="CONFIRM"/>환전 완료</label>
                  </c:otherwise>
                </c:choose>
              </div>
            </div>
            <div class="form-group">
              <label for="status" class="col-sm-2 control-label">신청자</label>
              <div class="col-sm-6">
                <p class="form-control-static">
                  홍길동
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="status" class="col-sm-2 control-label">신청일시</label>
              <div class="col-sm-6">
                <p class="form-control-static">
                  <fmt:formatDate value="${writeVO.createDttm}" pattern="yyyy-MM-dd HH:mm"/>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="exchangePoint" class="col-sm-2 control-label">환전 신청 포인트</label>
              <div class="col-sm-2">
                <c:choose>
                  <c:when test="${isConfirm}">
                    <p class="form-control-static">
                        ${writeVO.exchangePoint} Point
                    </p>
                  </c:when>
                  <c:otherwise>
                    <div class="input-group mg-t-xs">
                      <form:input path="exchangePoint" cssClass="form-control" required="required" type="number"/>
                      <span class="input-group-addon">Point</span>
                    </div>
                  </c:otherwise>
                </c:choose>
              </div>
            </div>
            <div class="form-group">
              <label for="exchangePoint" class="col-sm-2 control-label">계좌번호</label>
              <c:choose>
                <c:when test="${isConfirm}">
                  <div class="col-sm-3">
                    <p class="form-control-static">
                        ${writeVO.bank}&nbsp;${writeVO.bankAccountNumber}
                    </p>
                  </div>
                </c:when>
                <c:otherwise>
                  <div class="col-sm-1">
                    <form:input path="bank" cssClass="form-control" required="required"/>
                  </div>
                  <div class="col-sm-2">
                    <form:input path="bankAccountNumber" cssClass="form-control" required="required"/>
                  </div>
                </c:otherwise>
              </c:choose>
            </div>
            <div class="form-group">
              <label for="hp" class="col-sm-2 control-label">휴대폰 번호</label>
              <div class="col-sm-3">
                <c:choose>
                  <c:when test="${isConfirm}">
                    <p class="form-control-static">
                        ${writeVO.hp}
                    </p>
                  </c:when>
                  <c:otherwise>
                    <form:input path="hp" cssClass="form-control" required="required"/>
                  </c:otherwise>
                </c:choose>
              </div>
            </div>
            <div class="form-group">
              <label for="confirmDttm" class="col-sm-2 control-label">처리일시</label>
              <div class="col-sm-6">
                <p class="form-control-static">
                  <fmt:formatDate value="${writeVO.confirmDttm}" pattern="yyyy-MM-dd HH:mm"/>
                </p>
              </div>
            </div>
            <div class="bd-t mg-t-md pd-t-md"></div>
            <div class="form-group">
              <div class="col-sm-offset-2 col-sm-10">
                <c:if test="${!isConfirm}">
                  <button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert"/></button>
                </c:if>
                <a href="?<c:out value='${writeVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list"/></a>
              </div>
            </div>
          </fieldset>
        </form:form>
      </div>
    </div>
  </div>
</div>