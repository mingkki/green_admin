package egovframework.coing.popup.service.impl;

import egovframework.coing.popup.service.PopupService;
import egovframework.coing.popup.vo.PopupVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("popupService")
@RequiredArgsConstructor
public class PopupServiceImpl extends EgovAbstractServiceImpl implements PopupService {
    
    private final PopupMapper popupMapper;
    private final EgovIdGnrService egovPopupIdGnrService;
    
    @Override
    public Map<String, Object> selectPopupList(PopupVO vo) throws Exception {
        
        List<PopupVO> result = new ArrayList<PopupVO>();
        int cnt = popupMapper.selectPopupListCnt(vo);
        if(cnt > 0) {
            result = popupMapper.selectPopupList(vo);
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));
        
        return map;
    }
    
    @Override
    public PopupVO selectPopup(PopupVO vo) throws Exception {
        
        return popupMapper.selectPopup(vo);
    }
    
    @Override
    public void insertPopup(PopupVO vo) throws Exception {
        
        vo.setPopId(egovPopupIdGnrService.getNextIntegerId());
        popupMapper.insertPopup(vo);
    }
    
    @Override
    public void updatePopup(PopupVO vo) throws Exception {
        
        popupMapper.updatePopup(vo);
    }
    
    @Override
    public void deletePopup(PopupVO vo) throws Exception {
        
        popupMapper.deletePopup(vo);
    }
    
    @Override
    public int selectPopupMaxOrderNo(PopupVO vo) throws Exception {
        
        return popupMapper.selectPopupMaxOrderNo(vo);
    }
    
}