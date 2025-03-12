package egovframework.coing.popup.service.impl;

import egovframework.coing.popup.vo.PopupVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("popupMapper")
public interface PopupMapper {
    
    Integer selectPopupListCnt(PopupVO vo) throws Exception;
    
    List<PopupVO> selectPopupList(PopupVO vo) throws Exception;
    
    PopupVO selectPopup(PopupVO vo) throws Exception;
    
    Integer selectPopupMaxOrderNo(PopupVO vo) throws Exception;
    
    void insertPopup(PopupVO vo) throws Exception;
    
    void updatePopup(PopupVO vo) throws Exception;
    
    void deletePopup(PopupVO vo) throws Exception;
    
}