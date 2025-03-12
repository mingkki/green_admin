package egovframework.coing.cmm.service;

import egovframework.coing.cmm.vo.SystemConfigVO;

public interface SystemConfigService {
    
    /**
     * 환경설정 정보 한 건을 상세조회한다.
     */
    SystemConfigVO selectSystemConfig() throws Exception;
    
}