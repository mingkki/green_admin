<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="row">
  <div class="col-lg-12">
    <div class="block">
      <%@ include file="../include/tabmenu.jsp" %>
      <div class="block-head">
        <h4><c:out value="${ADMIN_MENU_VO.amePname}"/> 목록</h4>
      </div>
      <div class="block-content">
        <div class="pull-left">
          전체 <strong><c:out value="${resultCnt}"/></strong>건
        </div>
        <div class="pull-right">
        </div>
      </div>
      <form name="listForm" action="${REQUEST_URI}" method="post" class="form-inline">
        <input type="hidden" name="returnQueryString" value="<c:out value='${searchVO.queryString}' escapeXml='false' />"/>
        <input type="hidden" name="act" value=""/>
        <div class="block-content np">
          <table class="table table-hover table-bordered">
            <colgroup>
              <col style="width: 5%;">
              <col style="width: 10%;">
              <col style="width: 10%;">
              <col style="width: 10%;">
              <col style="width: 10%;">
              <col style="width: 10%;">
              <col style="width: 10%;">
            </colgroup>
            <thead>
            <tr>
              <th scope="col" class="text-center">구분</th>
              <th scope="col" class="text-center">이름</th>
              <th scope="col" class="text-center">이메일</th>
              <th scope="col" class="text-center">교환소</th>
              <th scope="col" class="text-center">포인트</th>
              <th scope="col" class="text-center">일시</th>
              <th scope="col" class="text-center">누적 포인트</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="result" items="${resultList}" varStatus="status">
              <tr>
                <td class="text-center">
                  <c:choose>
                    <c:when test="${result.point > 0}">
                      <span class="badge badge-info">적립</span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge badge-danger">차감</span>
                    </c:otherwise>
                  </c:choose>
                </td> <!-- 구분 -->
                <td class="text-left"><c:out value="${result.memName}"/>&nbsp;(<c:out value="${result.memId}"/>)</td> <!-- 이름 -->
                <td class="text-left"><c:out value="${result.memEmail}"/></td> <!-- 이메일 -->
                <td class="text-left"><c:out value="${result.emdNm}"/></td> <!-- 교환소 -->
                <td class="text-right"><fmt:formatNumber value="${result.point}" pattern="#,###"/>&nbsp;Point</td> <!-- 포인트 -->
                <td class="text-center"><fmt:formatDate value="${result.dttm}" pattern="yyyy-MM-dd HH:mm"/></td> <!-- 일시 -->
                <td class="text-right"><fmt:formatNumber value="${result.nowPoint}" pattern="#,###"/>&nbsp;Point</td> <!-- 누적 포인트 -->
              </tr>
            </c:forEach>
            <c:if test="${fn:length(resultList) == 0}">
              <tr>
                <td colspan="100" class="text-center"><spring:message code="common.nodata"/></td>
              </tr>
            </c:if>
            </tbody>
          </table>
        </div>
      </form>
      <div class="block-footer">
      
      </div>
    </div>
  </div>
</div>