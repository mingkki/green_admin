<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.io.*, java.util.*, org.springframework.web.context.*, org.springframework.web.context.support.*,egovframework.coing.board.service.impl.*" %>
<%@ page import="egovframework.coing.cmm.util.DashBoardHelper" %>
<%@ page import="egovframework.coing.board.vo.BoardWriteVO" %>
<%@ page import="egovframework.coing.cmm.vo.UserLoginLogVO" %>
<script>
  // 공통 차트 옵션
  const commonOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      tooltip: {
        backgroundColor: 'rgba(255, 255, 255, 0.95)',
        titleColor: '#1e293b',
        bodyColor: '#475569',
        borderColor: '#e2e8f0',
        borderWidth: 1,
        padding: {
          top: 10,
          right: 15,
          bottom: 10,
          left: 15
        },
        titleFont: {
          size: 13,
          weight: '600'
        },
        bodyFont: {
          size: 12
        },
        displayColors: true,
        boxPadding: 5
      },
      legend: {
        position: 'top',
        align: 'start',
        labels: {
          padding: 20,
          usePointStyle: true,
          pointStyle: 'circle',
          font: {
            size: 12
          }
        }
      }
    }
  };
</script>

<style>
    .stat_box {
        float: left;
        width: 33.333333333333%;
        min-height: 246px;
        border-right: 1px solid #e2e2e2;
        padding: 0 30px;
        box-sizing: border-box;
    }

    .stat_box h5 {
        font-size: 16px;
        padding-left: 7px;
        background: url("/cms/assets/img/dash/admin_dot.gif") left center no-repeat;
    }

    .stat_box span {
        display: block;
        font-size: 16px;
        font-weight: bold;
        padding-top: 7px;
    }

    .stat_box .guest_box {
        margin-top: 15px;
        position: relative;
        padding-left: 90px;
        min-height: 90px;
        background: url("/cms/assets/img/dash/admin_icon01.png") left top no-repeat;
    }

    .stat_box .guest_box .counter {
        position: absolute;
        right: 0;
        top: 30px;
        font-size: 22px;
        color: #5b7b0f;
    }

    .stat_box .pview_box {
        margin-top: 15px;
        position: relative;
        padding-left: 90px;
        min-height: 90px;
        background: url("/cms/assets/img/dash/admin_icon02.png") left top no-repeat;
    }

    .stat_box .pview_box .counter {
        position: absolute;
        right: 0;
        top: 30px;
        font-size: 22px;
        color: #e46b13;
    }

    /* 포인트 통계 스타일 */
    .point-stats {
        display: flex;
        justify-content: space-between;
        padding: 0 10px;
        background: #244E95;
        border-radius: 8px;
        color: white;
    }

    .point-box {
        flex: 1;
        text-align: center;
        padding: 10px;
        border-right: 1px solid rgba(255, 255, 255, 0.2);
    }

    .point-box:last-child {
        border-right: none;
    }

    .point-box h5 {
        font-size: 16px;
        margin-bottom: 5px;
        opacity: 0.9;
    }

    .point-value {
        font-size: 32px;
        font-weight: bold;
    }

    .point-value .unit {
        font-size: 20px;
        margin-left: 5px;
    }

    /* 차트 필터 스타일 */
    .chart-filters {
        display: flex;
        gap: 10px;
        margin-bottom: 20px;
    }

    .chart-filters select {
        padding: 6px 12px;
        border: 1px solid #ddd;
        border-radius: 4px;
        background: white;
        font-size: 14px;
    }

    /* 기존 스타일과 통일성을 위한 추가 스타일 */
    .block {
        background: #fff;
        margin-bottom: 20px;
        border-radius: 3px;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    }

</style>


<%
  DashBoardHelper dbh = new DashBoardHelper(request);
  List<BoardWriteVO> boardlist = dbh.getBoardWriteList("BordWriteVO");
  List<UserLoginLogVO> loginloglist = dbh.getLoginLogList("UserLoginLogVO");


%>

<c:set var="boardlist" value="<%=boardlist%>"/>
<c:set var="loginloglist" value="<%=loginloglist%>"/>

<div class="page-toolbar">
  <div class="page-toolbar-block">
    <div class="page-toolbar-title">Dashboard</div>
  </div>
</div>

<div class="row">
  <div class="col-lg-12">
    <div class="block">
      <div class="block-head">
        <h4>실적 현황</h4>
      </div>
      <div class="block-content">
        <div class="point-stats">
          <div class="point-box">
            <h5>구매 항목</h5>
            <div class="point-value">
              <span class="counter"><fmt:formatNumber value="${todayPoint}" pattern="#,###"/></span>
              <span class="unit">P</span>
            </div>
          </div>
          <div class="point-box">
            <h5>월별 지출 내역</h5>
            <div class="point-value">
              <span class="counter"><fmt:formatNumber value="${totalPoint}" pattern="#,###"/></span>
              <span class="unit">P</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="row">
  <div class="col-lg-6">
    <div class="block" style="min-height:322px;">
      <div class="block-head">
        <h4>방문기록</h4>
      </div>
      <div class="block-content">
        <div class="stat_box">
          <h5>전체통계</h5>
          <div class="guest_box">
            <span>방문자수</span>
            <span class="counter"><fmt_rt:formatNumber value="${totalCounterVO.scdUniqHits}" groupingUsed="true"/></span>
          </div>
          <div class="pview_box">
            <span>페이지뷰</span>
            <span class="counter"><fmt_rt:formatNumber value="${totalCounterVO.scdHits}" groupingUsed="true"/></span>
          </div>
        </div>
        <div class="stat_box">
          <h5>오늘 통계(<c:out value="${today}"/>)</h5>
          <div class="guest_box">
            <span>방문자수</span>
            <span class="counter"><fmt_rt:formatNumber value="${todayCounterVO.scdUniqHits}" groupingUsed="true"/></span>
          </div>
          <div class="pview_box">
            <span>페이지뷰</span>
            <span class="counter"><fmt_rt:formatNumber value="${todayCounterVO.scdHits}" groupingUsed="true"/></span>
          </div>
        </div>
        <div class="stat_box">
          <h5>어제 통계(<c:out value="${yesterday}"/>)</h5>
          <div class="guest_box">
            <span>방문자수</span>
            <span class="counter"><fmt_rt:formatNumber value="${yesterdayCounterVO.scdUniqHits}" groupingUsed="true"/></span>
          </div>
          <div class="pview_box">
            <span>페이지뷰</span>
            <span class="counter"><fmt_rt:formatNumber value="${yesterdayCounterVO.scdHits}" groupingUsed="true"/></span>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="col-lg-6">
    <div class="block">
      <div class="block-head">
        <h4>방문차트</h4>
      </div>
      <div class="block-content">
        <canvas id="visitChart" style="width: 100%; height: 250px;"></canvas>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/waypoints.min.js"></script>
<script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/jquery.counterup.min.js"></script>
<script src="//cdn.jsdelivr.net/npm/chart.js"></script>
<script>
  $(document).ready(function() {
    // 카운터 애니메이션
    $('.counter').counterUp({
      delay: 10,
      time: 1000
    });
  });
</script>

<!-- 스크립트 -->
<script src="//cdn.jsdelivr.net/npm/chart.js"></script>
<script>
  // 방문 차트
  const visitCtx = document.getElementById('visitChart').getContext('2d');
  new Chart(visitCtx, {
    type: 'line',
    data: {
      labels: [
        <c:forEach var="result" items="${weekCounterList}" varStatus="status">
        '<c:out value="${result.scdDate}"/>',
        </c:forEach>
      ],
      datasets: [
        {
          label: '페이지뷰',
          data: [
            <c:forEach var="result" items="${weekCounterList}" varStatus="status">
            <c:out value="${result.scdHits}"/>,
            </c:forEach>
          ],
          fill: true,
          backgroundColor: 'rgba(228, 107, 19, 0.2)',
          borderColor: 'rgba(228, 107, 19, 1)',
          tension: 0.4
        },
        {
          label: '방문자수',
          data: [
            <c:forEach var="result" items="${weekCounterList}" varStatus="status">
            <c:out value="${result.scdUniqHits}"/>,
            </c:forEach>
          ],
          fill: true,
          backgroundColor: 'rgba(91, 123, 15, 0.2)',
          borderColor: 'rgba(91, 123, 15, 1)',
          tension: 0.4
        }
      ]
    },
    options: {
      ...commonOptions,
      scales: {
        y: {
          beginAtZero: true,
          grid: {
            color: 'rgba(226, 232, 240, 0.7)',
            drawBorder: false
          },
          ticks: {
            padding: 10,
            color: '#64748b'
          }
        },
        x: {
          grid: {
            display: false
          },
          ticks: {
            padding: 5,
            color: '#64748b'
          }
        }
      }
    }
  });
</script>