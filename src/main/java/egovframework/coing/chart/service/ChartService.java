package egovframework.coing.chart.service;

import java.util.List;
import java.util.Map;

public interface ChartService {
    
    List<Map<String, Object>> exchangeRecyclingChart(int year, int month) throws Exception;
    
    List<Map<String, Object>> pointChart(int year) throws Exception;
    
    List<Map<String, Object>> recyclingMonthlyChart(int month, String exchange) throws Exception;
    
    List<Map<String, Object>> recyclingResourcesChart(int year, int month) throws Exception;
    
}