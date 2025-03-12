<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--@elvariable id="writeVO" type="egovframework.coing.resource.vo.ResourceVO"--%>
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
          <input type="hidden" name="regnumcheck" id="regnumcheck" value="N"/>
          <c:if test="${command eq 'update'}">
            <input type="hidden" name="id" value="${writeVO.id}"/>
          </c:if>
          <fieldset>
            <legend class="sr-only">등록정보</legend>
            <div class="form-group">
              <label for="name" class="col-sm-2 control-label">재활용품 이름</label>
              <div class="col-sm-6">
                <input type="text" class="form-control" id="name" name="name" value="<c:out value='${writeVO.name}' />" required="required"/>
              </div>
            </div>
            <div class="form-group">
              <label for="point" class="col-sm-2 control-label">개당 포인트</label>
              <div class="col-sm-6">
                <input type="number" class="form-control" id="point" name="point" value="<c:out value='${writeVO.point}' />" required="required"/>
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