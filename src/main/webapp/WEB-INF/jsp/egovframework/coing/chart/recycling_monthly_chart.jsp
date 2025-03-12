<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="now" class="java.util.Date"/>
<c:set var="currentYear"><fmt:formatDate value="${now}" pattern="yyyy"/></c:set>
<c:set var="currentMonth"><fmt:formatDate value="${now}" pattern="MM"/></c:set>

<div class="block recycling_monthly_chart_block">
  <div class="block-head">
    <h4>재활용품 배출 현황</h4>
  </div>
  <div class="block-content">
    <div class="chart-filters">
      <select name="month" class="form-control">
        <c:forEach var="month" items="${['01','02','03','04','05','06','07','08','09','10','11','12']}">
          <option value="${month}"
            ${month == currentMonth ? 'selected' : ''}
          >${month}월
          </option>
        </c:forEach>
      </select>
      
      <select id="emd" class="form-control">
        <c:forEach items="${emdList}" var="emd" varStatus="status">
          <option value="${emd.cddId}"
            ${status.index == 0 ? 'selected' : ''}
          >${emd.cddName}</option>
        </c:forEach>
      </select>
    </div>
  </div>
  <div class="block-content">
    <canvas id="recyclingMonthlyChart" style="width: 100%; height: 400px;"></canvas>
  </div>
</div>
<script>
  document.addEventListener('DOMContentLoaded', function() {
    const weeklyCtx = document.getElementById('recyclingMonthlyChart').getContext('2d');
    let chartInstance = null;

    // 차트 기본 설정
    const chartConfig = {
      type: 'line',
      data: {
        labels: [],
        datasets: []
      },
      options: {
        ...commonOptions,
        interaction: {
          intersect: false,
          mode: 'index'
        },
        scales: {
          y: {
            beginAtZero: true,
            grid: {
              drawBorder: false,
              color: 'rgba(0, 0, 0, 0.1)'
            },
            ticks: {
              stepSize: 50
            }
          },
          x: {
            grid: {
              display: false
            }
          }
        },
        animation: {
          duration: 300
        }
      }
    };

    const getRandomColor = () => {
      const color = '#' + Math.floor(Math.random() * 16777215).toString(16);
      return {
        border: color,
        background: 'rgba(128, 128, 128, 0.1)'
      };
    };

    const updateChart = (data) => {
      // 자원별로 데이터 재구성
      const resources = [...new Set(data.map(item => item.RESOURCE_NAME))];
      const weeks = [...new Set(data.map(item => item.WEEK_NUM))];

      const datasets = resources.map(resource => {
        const resourceData = weeks.map(week => {
          const entry = data.find(item =>
            item.RESOURCE_NAME === resource &&
            item.WEEK_NUM === week
          );
          return entry ? entry.QUANTITY : 0;
        });

        const colors = getRandomColor();

        return {
          label: resource,
          data: resourceData,
          borderColor: colors.border,
          backgroundColor: colors.background,
          tension: 0.4,
          pointRadius: 6,
          pointBackgroundColor: colors.border
        };
      });

      if(!chartInstance) {
        chartConfig.data.labels = weeks.map(week => `\${week}주차`);
        chartConfig.data.datasets = datasets;
        chartInstance = new Chart(weeklyCtx, chartConfig);
      } else {
        chartInstance.data.labels = weeks.map(week => `\${week}주차`);
        chartInstance.data.datasets = datasets;
        chartInstance.update('active');
      }
    };

    const fetchData = () => {
      const month = document.querySelector('.recycling_monthly_chart_block select[name="month"]').value;
      const exchange = document.querySelector('.recycling_monthly_chart_block select#emd').value;
      const query = new URLSearchParams({month, exchange});

      fetch(`${pageContext.request.contextPath}/recyclingMonthly/chart.do?\${query}`)
        .then(response => response.json())
        .then(data => {
          updateChart(data);
        });
    };

    // 초기 데이터 로드 및 차트 생성
    fetchData();

    // select 변경 이벤트 리스너 등록
    document.querySelector('.recycling_monthly_chart_block select[name="month"]').addEventListener('change', fetchData);
    document.querySelector('.recycling_monthly_chart_block select#emd').addEventListener('change', fetchData);
  });
</script>