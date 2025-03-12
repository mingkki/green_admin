package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.service.CodeService;
import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.util.EgovNumberUtil;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.util.SHA256Util;
import egovframework.coing.cmm.vo.CodeDetailVO;
import egovframework.coing.cmm.vo.GroupVO;
import egovframework.coing.cmm.vo.LevelVO;
import egovframework.coing.cmm.vo.SendSmsVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Service("commonService")
public class CommonServiceImpl extends EgovAbstractServiceImpl implements CommonService {
    
    private final CommonMapper commonMapper;
    private final CodeService codeService;
    private final LevelMapper levelMapper;
    private final GroupMapper groupMapper;
    
    @Override
    public List<LevelVO> selectLevelList() throws Exception {
        
        LevelVO vo = new LevelVO();
        return levelMapper.selectLevelList(vo);
    }
    
    @Override
    public List<GroupVO> selectGroupList() throws Exception {
        
        GroupVO vo = new GroupVO();
        return groupMapper.selectGroupList(vo);
    }
    
    @Override
    public List<GroupVO> selectGroupList(String systemYn) throws Exception {
        
        GroupVO vo = new GroupVO();
        vo.setSystemYn(systemYn);
        return groupMapper.selectGroupList(vo);
    }
    
    @Override
    public List<CodeDetailVO> selectCodeDetailList(CodeDetailVO vo) throws Exception {
        
        vo.setCddUseYn("Y");
        return codeService.selectCodeDetailListAll(vo);
    }
    
    @Override
    public String encryptPassword(String password) throws Exception {
        
        return SHA256Util.encrypt(password);
    }
    
    @Override
    public String encryptPassword(String password, String id) throws Exception {
        
        return SHA256Util.encrypt(password);
    }
    
    @Override
    public void sendSms(SendSmsVO vo) throws Exception {
        String content = EgovStringUtil.nullConvert(vo.getContent()).trim();
        if("".equals(content)) {
            return;
        }
        
        String from = EgovStringUtil.nullConvert(vo.getFrom()).trim();
        String to = EgovStringUtil.nullConvert(vo.getTo()).trim();
        if("".equals(from) || "".equals(to)) {
            return;
        }
        
        vo.setSubject("I-PLEX 광주");
        if(!EgovStringUtil.byteCheck(content, 90)) {
            vo.setType(5);
        }
        
        String messageId = EgovStringUtil.nullConvert(vo.getMessageId());
        if("".equals(messageId)) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            messageId = timestamp.getTime() + "" + EgovNumberUtil.getRandomNum(0, 1000);
            vo.setMessageId(messageId);
        }
        
        vo.setTo(vo.getTo().replace("-", ""));
        vo.setFrom(vo.getFrom().replace("-", ""));
        
        commonMapper.insertSendSms(vo);
    }
    
    @Override
    public CodeDetailVO selectCodeDetailSmsInfo(String cddId) throws Exception {
        
        CodeDetailVO codeDetailVO = new CodeDetailVO();
        codeDetailVO.setSearchCodId("SMSMSG");
        codeDetailVO.setSearchCddId(cddId);
        return selectCodeDetail(codeDetailVO);
    }
    
    @Override
    public CodeDetailVO selectCodeDetail(CodeDetailVO vo) throws Exception {
        
        return commonMapper.selectCodeDetail(vo);
    }
    
}