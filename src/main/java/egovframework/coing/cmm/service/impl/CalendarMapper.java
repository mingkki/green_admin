package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.vo.CalendarVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("calendarMapper")
public interface CalendarMapper {
    
    List<CalendarVO> selectCalendarSolarList(CalendarVO vo) throws Exception;
    
    List<CalendarVO> selectCalendarLunarList(CalendarVO vo) throws Exception;
    
    CalendarVO selectCalendarSolar(CalendarVO vo) throws Exception;
    
    CalendarVO selectCalendarLunar(CalendarVO vo) throws Exception;
    
}