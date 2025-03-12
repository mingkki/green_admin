<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="now" class="java.util.Date"/>
<c:set var="currentYear"><fmt:formatDate value="${now}" pattern="yyyy"/></c:set>
<c:set var="currentMonth"><fmt:formatDate value="${now}" pattern="MM"/></c:set>

<div class="block point_chart_block">
  <div class="block-head">
    <h4>포인트 적립 및 환전 현황</h4>
  </div>
  <div class="block-content">
    <div class="chart-filters">
      <select name="year" class="form-control">
        <c:forEach var="year" begin="${currentYear - 5}" end="${currentYear}">
          <option value="${year}" ${year == currentYear ? 'selected' : ''}>${year}년</option>
        </c:forEach>
      </select>
    </div>
  </div>
  <div class="block-content">
    <canvas id="pointChart" style="width: 100%; height: 400px;"></canvas>
  </div>
</div>

<script>
  document.addEventListener('DOMContentLoaded', function() {
    const pointCtx = document.getElementById('pointChart').getContext('2d');
    let chartInstance = null;

    // 초기 차트 설정
    const chartConfig = {
      type: 'bar',
      data: {
        labels: [],
        datasets: [
          {
            label: '적립 포인트',
            data: [],
            backgroundColor: 'rgba(255, 107, 139, 0.8)',
            borderColor: 'rgba(255, 107, 139, 1)',
            borderWidth: 1
          },
          {
            label: '환전 금액',
            data: [],
            backgroundColor: 'rgba(159, 122, 234, 0.8)',
            borderColor: 'rgba(159, 122, 234, 1)',
            borderWidth: 1
          }
        ]
      },
      options: {
        ...commonOptions,
        scales: {
          y: {
            beginAtZero: true,
            grid: {
              drawBorder: false
            }
          },
          x: {
            grid: {
              display: false
            }
          }
        },
        barPercentage: 0.7,
        categoryPercentage: 0.8,
        animation: {
          duration: 300 // 애니메이션 시간을 300ms로 설정
        }
      }
    };

    const updateChart = (data) => {
      const months = data.map(item => `\${item.MONTH}월`);
      const accumulatedPoints = data.map(item => item.ACCUMULATED_POINTS);
      const exchangedPoints = data.map(item => item.EXCHANGED_POINTS);

      chartInstance.data.labels = months;
      chartInstance.data.datasets[0].data = accumulatedPoints;
      chartInstance.data.datasets[1].data = exchangedPoints;
      chartInstance.update('active'); // 'active' 모드로 업데이트하여 부드러운 전환 구현
    };

    const fetchData = () => {
      const year = document.querySelector('.point_chart_block select[name="year"]').value;
      const query = new URLSearchParams({year});

      fetch('${pageContext.request.contextPath}/point/chart.do?' + query)
        .then(response => response.json())
        .then(data => {
          if(!chartInstance) {
            // 최초 차트 생성
            chartInstance = new Chart(pointCtx, chartConfig);
          }
          updateChart(data);
        });
    };

    // 초기 데이터 로드 및 차트 생성
    fetchData();

    // 연도 변경 이벤트
    document.querySelector('.point_chart_block select[name="year"]').addEventListener('change', fetchData);
  });
</script>