package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.vo.SystemConfigVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("systemConfigMapper")
public interface SystemConfigMapper {
    
    /**
     * 환경설정 정보 한 건을 상세조회한다.
     */
    SystemConfigVO selectSystemConfig() throws Exception;
    
}