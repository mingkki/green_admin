package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.service.SystemConfigService;
import egovframework.coing.cmm.vo.SystemConfigVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("systemConfigService")
public class SystemConfigServiceImpl extends EgovAbstractServiceImpl implements SystemConfigService {
    
    private final SystemConfigMapper systemConfigMapper;
    
    @Override
    public SystemConfigVO selectSystemConfig() throws Exception {
        
        return systemConfigMapper.selectSystemConfig();
    }
    
}