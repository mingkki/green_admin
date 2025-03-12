package egovframework.coing.chart.service.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper("chartMapper")
public interface ChartMapper {
    
    /** 교환소별 배출 현황 */
    List<Map<String, Object>> exchangeRecyclingChart(@Param(value = "year") int year, @Param(value = "month") int month) throws Exception;
    
    /** 포인트 적립 및 환전 현황 */
    List<Map<String, Object>> pointChart(@Param(value = "year") int year) throws Exception;
    
    /** 재활용품 배출 현황 */
    List<Map<String, Object>> recyclingMonthlyChart(@Param(value = "month") int month, @Param("exchange") String exchange) throws Exception;
    
    /** 재활용품 품목별 현황 */
    List<Map<String, Object>> recyclingResourcesChart(@Param(value = "year") int year, @Param(value = "month") int month) throws Exception;
    
}