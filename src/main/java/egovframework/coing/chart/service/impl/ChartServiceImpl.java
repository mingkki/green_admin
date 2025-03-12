package egovframework.coing.chart.service.impl;

import egovframework.coing.chart.service.ChartService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChartServiceImpl extends EgovAbstractServiceImpl implements ChartService {
    
    private final ChartMapper chartMapper;
    
    public List<Map<String, Object>> exchangeRecyclingChart(int year, int month) throws Exception {
        return chartMapper.exchangeRecyclingChart(year, month);
    }
    
    public List<Map<String, Object>> pointChart(int year) throws Exception {
        return chartMapper.pointChart(year);
    }
    
    public List<Map<String, Object>> recyclingMonthlyChart(int month, String exchange) throws Exception {
        return chartMapper.recyclingMonthlyChart(month, exchange);
    }
    
    public List<Map<String, Object>> recyclingResourcesChart(int year, int month) throws Exception {
        return chartMapper.recyclingResourcesChart(year, month);
    }
    
}