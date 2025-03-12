<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<div class="row">
  <div class="col-lg-12">
    <div class="block">
      <ul class="nav nav-tabs">
        <li><a href="${REQUEST_URI}?sinId=<c:out value='${searchSiteCounterDayVO.sinId}'/>">접속통계</a></li>
        <li class="active"><a href="#">시간대별</a></li>
        <li><a href="${REQUEST_URI}?act=day&amp;sinId=<c:out value='${searchSiteCounterDayVO.sinId}'/>">일별</a></li>
        <li><a href="${REQUEST_URI}?act=week&amp;sinId=<c:out value='${searchSiteCounterDayVO.sinId}'/>">요일별</a></li>
        <li><a href="${REQUEST_URI}?act=month&amp;sinId=<c:out value='${searchSiteCounterDayVO.sinId}'/>">월별</a></li>
        <li><a href="${REQUEST_URI}?act=year&amp;sinId=<c:out value='${searchSiteCounterDayVO.sinId}'/>">년도별</a></li>
        <li><a href="${REQUEST_URI}?act=page&amp;sinId=<c:out value='${searchSiteCounterDayVO.sinId}'/>">페이지별</a></li>
      </ul>
      <div class="block-head">
        <h4>시간대별</h4>
      </div>
      <div class="block-head pd-xs">
        <form action="${REQUEST_URI}?act=hour" method="post" class="form-inline">
          <fieldset>
            <legend class="sr-only">검색 폼</legend>
            <div class="form-group">
              <select name="sinId" id="sinId" class="form-control" onchange="this.form.submit();">
                <option value="">+ 사이트선택</option>
                <c:forEach var="result" items="${siteList}" varStatus="status">
                  <option value="${result.sinId}" <c:if test="${searchSiteCounterDayVO.sinId eq result.sinId}">selected="selected"</c:if>>
                    <c:out value="${result.sinName}"/></option>
                </c:forEach>
              </select>
            </div>
            <div class="form-group">
              <input type="text" name="searchStartDt" id="searchStartDt" class="form-control datepicker" value="<c:out value='${searchSiteCounterDayVO.searchStartDt}'/>"/> ~
            </div>
            <div class="input-group">
              <input type="text" name="searchEndDt" id="searchEndDt" class="form-control datepicker" value="<c:out value='${searchSiteCounterDayVO.searchEndDt}'/>"/>
              <span class="input-group-btn">
								<button type="submit" class="btn btn-default"><spring:message code="common.search"/></button>
							</span>
            </div>
          </fieldset>
        </form>
      </div>
      <div class="block-content">
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript">
          google.charts.load('current', {'packages': ['corechart']});
          google.charts.setOnLoadCallback(drawChart);

          function drawChart() {
            var data = google.visualization.arrayToDataTable([
              ['시간', '페이지뷰', '방문자수'],
              ['00', <c:out value="${result.scdHh00}"/>, <c:out value="${result.scdUhh00}"/>],
              ['01', <c:out value="${result.scdHh01}"/>, <c:out value="${result.scdUhh01}"/>],
              ['02', <c:out value="${result.scdHh02}"/>, <c:out value="${result.scdUhh02}"/>],
              ['03', <c:out value="${result.scdHh03}"/>, <c:out value="${result.scdUhh03}"/>],
              ['04', <c:out value="${result.scdHh04}"/>, <c:out value="${result.scdUhh04}"/>],
              ['05', <c:out value="${result.scdHh05}"/>, <c:out value="${result.scdUhh05}"/>],
              ['06', <c:out value="${result.scdHh06}"/>, <c:out value="${result.scdUhh06}"/>],
              ['07', <c:out value="${result.scdHh07}"/>, <c:out value="${result.scdUhh07}"/>],
              ['08', <c:out value="${result.scdHh08}"/>, <c:out value="${result.scdUhh08}"/>],
              ['09', <c:out value="${result.scdHh09}"/>, <c:out value="${result.scdUhh09}"/>],
              ['10', <c:out value="${result.scdHh10}"/>, <c:out value="${result.scdUhh10}"/>],
              ['11', <c:out value="${result.scdHh11}"/>, <c:out value="${result.scdUhh11}"/>],
              ['12', <c:out value="${result.scdHh12}"/>, <c:out value="${result.scdUhh12}"/>],
              ['13', <c:out value="${result.scdHh13}"/>, <c:out value="${result.scdUhh13}"/>],
              ['14', <c:out value="${result.scdHh14}"/>, <c:out value="${result.scdUhh14}"/>],
              ['15', <c:out value="${result.scdHh15}"/>, <c:out value="${result.scdUhh15}"/>],
              ['16', <c:out value="${result.scdHh16}"/>, <c:out value="${result.scdUhh16}"/>],
              ['17', <c:out value="${result.scdHh17}"/>, <c:out value="${result.scdUhh17}"/>],
              ['18', <c:out value="${result.scdHh18}"/>, <c:out value="${result.scdUhh18}"/>],
              ['19', <c:out value="${result.scdHh19}"/>, <c:out value="${result.scdUhh19}"/>],
              ['20', <c:out value="${result.scdHh20}"/>, <c:out value="${result.scdUhh20}"/>],
              ['21', <c:out value="${result.scdHh21}"/>, <c:out value="${result.scdUhh21}"/>],
              ['22', <c:out value="${result.scdHh22}"/>, <c:out value="${result.scdUhh22}"/>],
              ['23', <c:out value="${result.scdHh23}"/>, <c:out value="${result.scdUhh23}"/>],
            ]);

            var options = {
              title: '시간대별 접속통계',
              curveType: 'function',
              legend: {position: 'bottom'}
            };

            var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

            chart.draw(data, options);
          }
        </script>
        </head>
        <div id="curve_chart" style="width: 100%; height: 250px"></div>
      
      </div>
      <div class="block-content">
        <table class="table">
          <colgroup>
            <col style="width:150px;"/>
            <col/>
            <col/>
          </colgroup>
          <thead>
          <tr>
            <th scope="col">시간별</th>
            <th scope="col">페이지뷰</th>
            <th scope="col">방문자</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>00:00 ~ 01:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh00 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh00}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh00 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh00}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>01:00 ~ 02:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh01 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh01}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh01 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh01}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>02:00 ~ 03:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh02 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh02}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh02 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh02}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>03:00 ~ 04:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh03 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh03}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh03 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh03}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>04:00 ~ 05:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh04 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh04}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh04 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh04}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>05:00 ~ 06:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh05 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh05}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh05 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh05}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>06:00 ~ 07:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh06 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh06}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh06 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh06}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>07:00 ~ 08:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh07 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh07}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh07 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh07}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>08:00 ~ 09:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh08 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh08}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh08 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh08}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>09:00 ~ 10:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh09 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh09}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh09 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh09}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>10:00 ~ 11:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh10 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh10}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh10 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh10}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>11:00 ~ 12:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh11 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh11}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh11 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh11}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>12:00 ~ 13:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh12 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh12}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh12 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh12}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>13:00 ~ 14:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh13 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh13}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh13 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh13}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>14:00 ~ 15:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh14 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh14}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh14 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh14}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>15:00 ~ 16:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh15 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh15}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh15 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh15}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>16:00 ~ 17:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh16 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh16}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh16 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh16}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>17:00 ~ 18:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh17 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh17}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh17 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh17}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>18:00 ~ 19:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh18 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh18}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh18 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh18}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>19:00 ~ 20:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh19 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh19}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh19 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh19}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>20:00 ~ 21:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh20 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh20}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh20 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh20}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>21:00 ~ 22:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh21 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh21}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh21 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh21}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>22:00 ~ 23:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh22 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh22}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh22 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh22}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          <tr>
            <td>23:00 ~ 24:00</td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdHits == 0 ? 0 : (result.scdHh23 / result.scdHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              페이지뷰 : <fmt_rt:formatNumber value="${result.scdHh23}" groupingUsed="true"/> / ${countPer}%
            </td>
            <td>
              <fmt_rt:formatNumber var="countPer" value="${result.scdUniqHits == 0 ? 0 : (result.scdUhh23 / result.scdUniqHits * 100)}" maxFractionDigits="1"/>
              <div class="progress progress-small no-radius no-margin mg-b-sm">
                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${countPer}" aria-valuemin="0" aria-valuemax="100" style="width:${countPer}%"></div>
              </div>
              방문자 : <fmt_rt:formatNumber value="${result.scdUhh23}" groupingUsed="true"/> / ${countPer}%
            </td>
          </tr>
          </tbody>
          <tfoot>
          <tr>
            <td>전체</td>
            <td><fmt_rt:formatNumber value="${result.scdHits}" groupingUsed="true"/></td>
            <td><fmt_rt:formatNumber value="${result.scdUniqHits}" groupingUsed="true"/></td>
          </tr>
          </tfoot>
        </table>
      </div>
    </div>
  </div>
</div>