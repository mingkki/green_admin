package egovframework.coing.cmm.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SystemConfig {
    
    /**
     * 관리자 접근 IP
     */
    private String cfgAdminAccessIp;
    
    /**
     * 관리자 비밀번호변경 주기(개월)
     */
    private int cfgAdminPwchangeMonth;
    
    /**
     * 중복로그인 가능여부(Y,N)
     */
    private String cfgDuploginYn;
    
}