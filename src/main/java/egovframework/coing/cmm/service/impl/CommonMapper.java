package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.cmm.vo.SendSmsVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("commonMapper")
public interface CommonMapper {
    
    void insertSendSms(SendSmsVO vo) throws Exception;
    
    CodeDetailVO selectCodeDetail(CodeDetailVO vo) throws Exception;
    
}