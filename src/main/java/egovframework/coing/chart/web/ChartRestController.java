package egovframework.coing.chart.web;

import egovframework.coing.chart.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChartRestController {
    
    private final ChartService chartService;
    
    @GetMapping("exchangeRecycling/chart.do")
    public ResponseEntity<?> exchangeRecyclingChart(@RequestParam int year, @RequestParam int month) throws Exception {
        List<Map<String, Object>> resultList = chartService.exchangeRecyclingChart(year, month);
        
        return ResponseEntity.ok(resultList);
    }
    
    @GetMapping("point/chart.do")
    public ResponseEntity<?> pointChart(@RequestParam int year) throws Exception {
        List<Map<String, Object>> resultList = chartService.pointChart(year);
        
        return ResponseEntity.ok(resultList);
    }
    
    @GetMapping("recyclingMonthly/chart.do")
    public ResponseEntity<?> recyclingMonthlyChart(@RequestParam int month, @RequestParam String exchange) throws Exception {
        List<Map<String, Object>> resultList = chartService.recyclingMonthlyChart(month, exchange);
        
        return ResponseEntity.ok(resultList);
    }
    
    @GetMapping("recyclingResources/chart.do")
    public ResponseEntity<?> recyclingResourcesChart(@RequestParam int year, @RequestParam int month) throws Exception {
        List<Map<String, Object>> resultList = chartService.recyclingResourcesChart(year, month);
        
        return ResponseEntity.ok(resultList);
    }
    
}