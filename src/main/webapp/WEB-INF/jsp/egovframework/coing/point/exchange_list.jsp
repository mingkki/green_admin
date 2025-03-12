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
            </colgroup>
            <thead>
            <tr>
              <th scope="col" class="text-center">상태</th>
              <th scope="col" class="text-center">신청자</th>
              <th scope="col" class="text-center">은행명</th>
              <th scope="col" class="text-center">계좌번호</th>
              <th scope="col" class="text-center">휴대폰 번호</th>
              <th scope="col" class="text-center">환전 신청 포인트</th>
              <th scope="col" class="text-center">신청 일시</th>
              <th scope="col" class="text-center">처리 일시</th>
              <th scope="col" class="text-center">관리</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="result" items="${resultList}" varStatus="status">
              <tr>
                <td class="text-center">
                  <c:choose>
                    <c:when test="${result.status eq 'WAIT'}">
                      <span class="badge badge-warning"><c:out value="${result.statusNm}"/></span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge badge-info"><c:out value="${result.statusNm}"/></span>
                    </c:otherwise>
                  </c:choose>
                </td> <!-- 상태 -->
                <td class="text-left"><c:out value="${result.memName}"/>&nbsp;(<c:out value="${result.memId}"/>)</td> <!-- 신청자 -->
                <td class="text-center"><c:out value="${result.bank}"/></td> <!-- 은행명 -->
                <td class="text-left"><c:out value="${result.bankAccountNumber}"/></td> <!-- 계좌번호 -->
                <td class="text-left"><c:out value="${result.hp}"/></td> <!-- 휴대폰 번호 -->
                <td class="text-right">
                  <fmt:formatNumber value="${result.exchangePoint}" pattern="#,###"/>&nbsp;Point
                </td> <!-- 환전 신청 포인트 -->
                <td class="text-center">
                  <fmt:formatDate value="${result.createDttm}" pattern="yyyy-MM-dd HH:mm"/>
                </td> <!-- 신청 일시 -->
                <td class="text-center">
                  <fmt:formatDate value="${result.confirmDttm}" pattern="yyyy-MM-dd HH:mm"/>
                </td> <!-- 처리 일시 -->
                <td class="text-center">
                  <a href="?act=write&amp;id=<c:out value='${result.id}'/>&amp;<c:out value='${searchVO.queryString}'/>" class="btn btn-default btn-xs">수정</a>
                </td> <!-- 관리 -->
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
        <div class="clearfix">
          <div class="pull-left">
          </div>
          <div class="pull-right">
            <%--            <a href="?act=write&amp;<c:out value='${searchVO.queryString}'/>" class="btn btn-primary">등록</a>--%>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>