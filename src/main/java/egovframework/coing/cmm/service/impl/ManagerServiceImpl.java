package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.service.ManagerService;
import egovframework.coing.cmm.service.UserService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.cmm.vo.ManagerVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service("managerService")
public class ManagerServiceImpl extends EgovAbstractServiceImpl implements ManagerService {
    
    private final ManagerMapper managerMapper;
    private final CommonService commonService;
    private final UserService userService;
    
    @Override
    public Map<String, Object> selectManagerList(ManagerVO vo) throws Exception {
        
        List<ManagerVO> result = new ArrayList<ManagerVO>();
        int cnt = managerMapper.selectManagerListCnt(vo);
        if(cnt > 0) {
            result = managerMapper.selectManagerList(vo);
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));
        
        return map;
    }
    
    @Override
    public ManagerVO selectManager(ManagerVO vo) throws Exception {
        
        return managerMapper.selectManager(vo);
    }
    
    @Override
    public void insertManager(ManagerVO vo) throws Exception {
        
        if(!"".equals(EgovStringUtil.nullConvert(vo.getMngPasswd()))) {
            vo.setMngPasswd(commonService.encryptPassword(vo.getMngPasswd(), vo.getMngId()));
        }
        
        managerMapper.insertManager(vo);
    }
    
    @Override
    public void updateManager(ManagerVO vo) throws Exception {
        
        if(!"".equals(EgovStringUtil.nullConvert(vo.getMngPasswd()))) {
            LoginVO updateVO = new LoginVO();
            updateVO.setId(vo.getMngId());
            updateVO.setPassword(vo.getMngPasswd());
            updateVO.setUserSe("MNG");
            updateVO.setIp(vo.getMngUpdtIp());
            userService.updatePassword(updateVO);
        }
        
        managerMapper.updateManager(vo);
    }
    
    @Override
    public void updateManagerMyinfo(ManagerVO vo) throws Exception {
        
        if(!"".equals(EgovStringUtil.nullConvert(vo.getMngPasswd()))) {
            LoginVO updateVO = new LoginVO();
            updateVO.setId(vo.getMngId());
            updateVO.setPassword(vo.getMngPasswd());
            updateVO.setUserSe("MNG");
            updateVO.setIp(vo.getMngUpdtIp());
            userService.updatePassword(updateVO);
        }
        
        managerMapper.updateManagerMyinfo(vo);
    }
    
    @Override
    public void deleteManager(ManagerVO vo) throws Exception {
        
        managerMapper.deleteManager(vo);
    }
    
}