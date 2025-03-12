package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.service.CalendarService;
import egovframework.coing.cmm.vo.CalendarVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("calendarService")
public class CalendarServiceImpl extends EgovAbstractServiceImpl implements CalendarService {
    
    private final CalendarMapper calendarMapper;
    
    @Override
    public List<CalendarVO> selectCalendarSolarList(CalendarVO vo) throws Exception {
        
        return calendarMapper.selectCalendarSolarList(vo);
    }
    
    @Override
    public List<CalendarVO> selectCalendarLunarList(CalendarVO vo) throws Exception {
        
        return calendarMapper.selectCalendarLunarList(vo);
    }
    
    @Override
    public CalendarVO selectCalendarSolar(CalendarVO vo) throws Exception {
        
        return calendarMapper.selectCalendarSolar(vo);
    }
    
    @Override
    public CalendarVO selectCalendarLunar(CalendarVO vo) throws Exception {
        
        return calendarMapper.selectCalendarLunar(vo);
    }
    
}