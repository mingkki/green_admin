package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.service.SendMailService;
import egovframework.coing.cmm.vo.SendMailVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Service("sendMailService")
public class SendMailServiceImpl extends EgovAbstractServiceImpl implements SendMailService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMailServiceImpl.class);
    private final JavaMailSender mailSender;
    
    @Override
    public boolean send(SendMailVO vo) throws Exception {
        
        String recptnPerson = (vo.getRecptnPerson() == null)
                ? ""
                : vo.getRecptnPerson();          // 수신자
        String subject = (vo.getSj() == null)
                ? ""
                : vo.getSj();                                   // 메일제목
        String emailCn = (vo.getEmailCn() == null)
                ? ""
                : vo.getEmailCn();                         // 메일내용
        
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            
            messageHelper.setTo(recptnPerson);
            messageHelper.setSubject(subject);
            messageHelper.setText(emailCn, true);
            mailSender.send(message);
        } catch(MailParseException ex) {
            LOGGER.error("Sending Mail Exception : {} [failure when parsing the message]", ex.getCause());
            return false;
        } catch(MailAuthenticationException ex) {
            LOGGER.error("Sending Mail Exception : {} [authentication failure]", ex.getCause());
            return false;
        } catch(MailSendException ex) {
            ex.fillInStackTrace();
            System.out.println(ex.getMessage());
            LOGGER.error("Sending Mail Exception : {} [failure when sending the message]", ex.getCause());
            return false;
        } catch(Exception ex) {
            LOGGER.debug(ex.getMessage());
            return false;
        }
        
        return true;
    }
    
}