<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<div class="row">
  <div class="col-lg-12">
    <div class="block">
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
              <th scope="col" class="text-center">읍면동 정보</th>
              <th scope="col" class="text-center">교환소 위치</th>
              <th scope="col" class="text-center">주소</th>
              <th scope="col" class="text-center">운영시간</th>
              <th scope="col" class="text-center">휴무</th>
              <th scope="col" class="text-center">관리</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="result" items="${resultList}" varStatus="status">
              <tr>
                <td class="text-center"><c:out value="${result.emdNm}"/></td> <!-- 읍면동 정보 -->
                <td class="text-center"><c:out value="${result.place}"/></td> <!-- 교환소 위치 -->
                <td class="text-center"><c:out value="${result.address}"/></td> <!-- 주소 -->
                <td class="text-center"><c:out value="${result.open}"/></td> <!-- 운영시간 -->
                <td class="text-center"><c:out value="${result.close}"/></td> <!-- 휴무 -->
                <td class="text-center">
                  <a href="?act=write&amp;id=<c:out value='${result.id}'/>&amp;<c:out value='${searchVO.queryString}'/>" class="btn btn-default btn-xs">수정</a>
                  <a href="javascript:post_delete('', 'act=delete&id=<c:out value="${result.id}"/>&<c:out value="${searchVO.queryString}"/>');" class="btn btn-default btn-xs">삭제</a>
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
            <a href="?act=write&amp<c:out value='${searchVO.queryString}'/>" class="btn btn-primary">등록</a>
          </div>
        </div>
        <%--        <div class="text-center">--%>
        <%--          <ul class="pagination pagination-sm">--%>
        <%--            <li class="active"><a href="#"><strong>1</strong></a></li>--%>
        <%--          </ul>--%>
        <%--        </div>--%>
      </div>
    </div>
  </div>
</div>