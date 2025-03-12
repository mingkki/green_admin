package egovframework.coing.cmm.service;

import egovframework.coing.cmm.vo.CalendarVO;

import java.util.List;

public interface CalendarService {
    
    List<CalendarVO> selectCalendarSolarList(CalendarVO vo) throws Exception;
    
    List<CalendarVO> selectCalendarLunarList(CalendarVO vo) throws Exception;
    
    CalendarVO selectCalendarSolar(CalendarVO vo) throws Exception;
    
    CalendarVO selectCalendarLunar(CalendarVO vo) throws Exception;
    
}