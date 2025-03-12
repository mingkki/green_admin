<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="now" class="java.util.Date"/>
<c:set var="currentYear"><fmt:formatDate value="${now}" pattern="yyyy"/></c:set>
<c:set var="currentMonth"><fmt:formatDate value="${now}" pattern="MM"/></c:set>

<div class="block exchange_recycling_chart_block">
  <div class="block-head">
    <h4>교환소별 배출 현황</h4>
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
    <canvas id="exchangeRecyclingChart" style="width: 100%; height: 400px;"></canvas>
  </div>
</div>
<script>
  document.addEventListener('DOMContentLoaded', function() {
    const stackedCtx = document.getElementById('exchangeRecyclingChart').getContext('2d');
    let chartInstance = null;

    // 차트 기본 설정
    const chartConfig = {
      type: 'bar',
      data: {
        labels: [],
        datasets: []
      },
      options: {
        ...commonOptions,
        scales: {
          x: {
            stacked: true,
            grid: {
              display: false
            }
          },
          y: {
            stacked: true,
            beginAtZero: true,
            max: 1000,
            ticks: {
              stepSize: 200
            },
            grid: {
              color: 'rgba(0, 0, 0, 0.1)'
            }
          }
        },
        animation: {
          duration: 300 // 애니메이션 시간 설정
        }
      }
    };

    // 색상 배열
    const colors = [
      '#1e40af', '#60a5fa', '#7dd3fc', '#3b82f6',
      '#2563eb', '#1d4ed8', '#93c5fd', '#bfdbfe',
      '#dbeafe', '#0ea5e9', '#0284c7', '#0369a1'
    ];

    const updateChart = (data) => {
      const resourceTypes = Object.keys(JSON.parse(data[0].RESOURCE_QUANTITIES));
      const datasets = resourceTypes.map((resource, index) => ({
        label: resource,
        data: data.map(item => JSON.parse(item.RESOURCE_QUANTITIES)[resource]),
        backgroundColor: colors[index % colors.length]
      }));

      if(!chartInstance) {
        chartConfig.data.labels = data.map(item => item.EMD_NAME);
        chartConfig.data.datasets = datasets;
        chartInstance = new Chart(stackedCtx, chartConfig);
      } else {
        chartInstance.data.labels = data.map(item => item.EMD_NAME);
        chartInstance.data.datasets = datasets;
        chartInstance.update('active'); // 부드러운 전환을 위해 'active' 모드로 업데이트
      }
    };

    const fetchData = () => {
      const year = document.querySelector('.exchange_recycling_chart_block select[name="year"]').value;
      const month = document.querySelector('.exchange_recycling_chart_block select[name="month"]').value;
      const query = new URLSearchParams({year, month});

      fetch(`${pageContext.request.contextPath}/exchangeRecycling/chart.do?\${query}`)
        .then(response => response.json())
        .then(data => {
          updateChart(data);
        });
    };

    // 초기 데이터 로드 및 차트 생성
    fetchData();

    // select 변경 이벤트 리스너 등록
    document.querySelector('.exchange_recycling_chart_block select[name="year"]').addEventListener('change', fetchData);
    document.querySelector('.exchange_recycling_chart_block select[name="month"]').addEventListener('change', fetchData);
  });
</script>