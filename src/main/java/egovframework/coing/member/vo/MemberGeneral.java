package egovframework.coing.member.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberGeneral {
    
    private String memId;
    
    private String memPasswd;
    
    private String memName;
    
    private String memGender;
    
    private String memBirthDt;
    
    private String memBirthGb;
    
    private String memEmail;
    
    private String memEmailYn;
    
    private String memHomepage;
    
    private String memTel;
    
    private String memHp;
    
    private String memHpYn;
    
    private String memDept;
    
    private String memJobTitle;
    
    private String memTask;
    
    private String memIdentifyGb;
    
    private String memIdentifyYn;
    
    private String memStatus;
    
    private int memLevel;
    
    private String memGroups;
    
    private String memMemo;
    
    private String memLastloginDttm;
    
    private String memLastloginIp;
    
    private int memLoginCnt;
    
    private String memPwchangeDttm;
    
    private String memPwchangeIp;
    
    private String memRegDttm;
    
    private String memRegId;
    
    private String memRegIp;
    
    private String memUpdtDttm;
    
    private String memUpdtId;
    
    private String memUpdtIp;
    
    private String memLoginfailDttm;
    
    private String memLoginfailIp;
    
    private int memLoginfailCnt;
    
    private int memPwwrongCnt;
    
    private String memPwwrongDttm;
    
    private String memLockDttm;
    
    private String memLockId;
    
    private String memLockIp;
    
    private String memLockMemo;
    
    private String memLeaveDttm;
    
    private String memLeaveId;
    
    private String memLeaveIp;
    
    private String memLeaveMemo;
    
    private String memDelId;
    
    private String memDelIp;
    
    private String memSosok; //회원구분
    
    private String memComRegNum; //사업자등록번호
    
    private String memComName; //업체명
    
    private String memFax; //fax번호를 이녀석으로 사용할 것.
    
    private String memComZipcd; //우편번호
    
    private String memComAddress1; //주소
    private String memComAddress2; //상세주소
    private String memCeoName;

    private String memBank;
    private String memBankAccountNumber;

    private String memExchangeId;

    
}