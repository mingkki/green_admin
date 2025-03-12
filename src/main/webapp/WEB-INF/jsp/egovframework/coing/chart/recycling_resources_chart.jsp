<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="now" class="java.util.Date"/>
<c:set var="currentYear"><fmt:formatDate value="${now}" pattern="yyyy"/></c:set>
<c:set var="currentMonth"><fmt:formatDate value="${now}" pattern="MM"/></c:set>

<div class="block recycling_resources_chart_block">
  <div class="block-head">
    <h4>재활용품 품목별 현황</h4>
  </div>
  <div class="block-content">
    <div class="chart-filters">
      <select name="year" class="form-control">
        <c:forEach var="year" begin="${currentYear - 5}" end="${currentYear}">
          <option value="${year}" ${year == currentYear ? 'selected' : ''}>${year}년</option>
        </c:forEach>
      </select>
      <select name="month" class="form-control">
        <c:forEach var="month" items="${['01','02','03','04','05','06','07','08','09','10','11','12']}">
          <option value="${month}"
            ${month == currentMonth ? 'selected' : ''}
          >${month}월
          </option>
        </c:forEach>
      </select>
    </div>
  </div>
  <div class="block-content">
    <canvas id="recyclingResourcesChart" style="width: 100%; height: 400px;"></canvas>
  </div>
</div>
<script>
  document.addEventListener('DOMContentLoaded', function() {
    const donutCtx = document.getElementById('recyclingResourcesChart').getContext('2d');
    let chartInstance = null;

    // 색상 배열 정의
    const colors = [
      '#22c55e', '#3b82f6', '#f43f5e', '#eab308',
      '#8b5cf6', '#ec4899', '#06b6d4', '#84cc16',
      '#14b8a6', '#f97316', '#6366f1', '#d946ef'
    ];

    // 차트 기본 설정
    const chartConfig = {
      type: 'doughnut',
      data: {
        labels: [],
        datasets: [{
          data: [],
          backgroundColor: colors,
          borderWidth: 0
        }]
      },
      options: {
        ...commonOptions,
        cutout: '75%',
        plugins: {
          legend: {
            position: 'bottom'
          },
          tooltip: {
            callbacks: {
              label: function(context) {
                const item = context.chart.data.originalData[context.dataIndex];
                return `\${item.RESOURCE_NAME}: \${item.TOTAL_QUANTITY}개 (\${item.PERCENTAGE}%)`;
              }
            }
          }
        },
        animation: {
          duration: 300
        }
      }
    };

    const updateChart = (data) => {
      if(!chartInstance) {
        chartConfig.data.labels = data.map(item => item.RESOURCE_NAME);
        chartConfig.data.datasets[0].data = data.map(item => item.TOTAL_QUANTITY);
        chartConfig.data.backgroundColor = colors.slice(0, data.length);
        chartConfig.data.originalData = data; // 툴팁에서 사용할 원본 데이터 저장
        chartInstance = new Chart(donutCtx, chartConfig);
      } else {
        chartInstance.data.labels = data.map(item => item.RESOURCE_NAME);
        chartInstance.data.datasets[0].data = data.map(item => item.TOTAL_QUANTITY);
        chartInstance.data.datasets[0].backgroundColor = colors.slice(0, data.length);
        chartInstance.data.originalData = data;
        chartInstance.update('active');
      }
    };

    const fetchData = () => {
      const year = document.querySelector('.recycling_resources_chart_block select[name="year"]').value;
      const month = document.querySelector('.recycling_resources_chart_block select[name="month"]').value;
      const query = new URLSearchParams({year, month});

      fetch(`${pageContext.request.contextPath}/recyclingResources/chart.do?\${query}`)
        .then(response => response.json())
        .then(data => {
          updateChart(data);
        });
    };

    // 초기 데이터 로드 및 차트 생성
    fetchData();

    // select 변경 이벤트 리스너 등록
    document.querySelector('.recycling_resources_chart_block select[name="year"]').addEventListener('change', fetchData);
    document.querySelector('.recycling_resources_chart_block select[name="month"]').addEventListener('change', fetchData);
  });
</script>