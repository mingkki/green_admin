package egovframework.coing.cmm.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SendSmsVO {
    /** 메세지ID */
    private String messageId;
    
    /** 메세지 타입 */
    // (SMS 0/FAX 2/PHONE 3/MMS 5/AT 6/FT 7)
    private int type;
    
    /** 발신자 */
    private String from;
    
    /** 발신자 이름 */
    private String fromName;
    
    /** 수신자 */
    private String to;
    
    private String toName;
    
    private String subject;
    /** 발송결과코드 */
    //private String sndngResultCode;
    /** 메일내용 */
    private String content;
    /** 첨부파일ID */
    //private String atchFileId;
    /** 첨부파일경로 */
    //private String fileStreCours;
    /** 첨부파일이름 */
    //private String orignlFileNm;
    /** 발신일자 */
    private String sendDttm;
    /** 첨부파일ID 리스트 */
    //private String atchFileIdList;
    /** 발송요청XML내용 */
    //private String xmlContent;
    
}