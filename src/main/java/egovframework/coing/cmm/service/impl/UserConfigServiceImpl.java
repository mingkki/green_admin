package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.service.UserConfigService;
import egovframework.coing.cmm.vo.UserConfigVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("userConfigService")
public class UserConfigServiceImpl extends EgovAbstractServiceImpl implements UserConfigService {
    
    private final UserConfigMapper userConfigMapper;
    
    @Override
    public UserConfigVO selectUserConfig(UserConfigVO vo) throws Exception {
        
        return userConfigMapper.selectUserConfig(vo);
    }
    
    @Override
    public void insertUserConfig(UserConfigVO vo) throws Exception {
        
        userConfigMapper.insertUserConfig(vo);
    }
    
    @Override
    public void updateUserConfig(UserConfigVO vo) throws Exception {
        
        userConfigMapper.updateUserConfig(vo);
    }
    
}