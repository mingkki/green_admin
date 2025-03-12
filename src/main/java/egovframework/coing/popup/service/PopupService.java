package egovframework.coing.popup.service;

import egovframework.coing.popup.vo.PopupVO;

import java.util.Map;

public interface PopupService {
    
    Map<String, Object> selectPopupList(PopupVO vo) throws Exception;
    
    PopupVO selectPopup(PopupVO vo) throws Exception;
    
    int selectPopupMaxOrderNo(PopupVO vo) throws Exception;
    
    void insertPopup(PopupVO vo) throws Exception;
    
    void updatePopup(PopupVO vo) throws Exception;
    
    void deletePopup(PopupVO vo) throws Exception;
    
}