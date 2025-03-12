package egovframework.coing.cmm.service;

import egovframework.coing.cmm.vo.SendMailVO;

public interface SendMailService {
    
    /**
     * 메일을 발송한다
     * @param vo SndngMailVO
     * @return boolean
     * @exception Exception
     */
    boolean send(SendMailVO vo) throws Exception;
}