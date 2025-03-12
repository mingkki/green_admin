package egovframework.coing.member.service.impl;

import egovframework.coing.cmm.service.CommonService;
import egovframework.coing.cmm.service.UserService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.vo.LoginVO;
import egovframework.coing.member.service.MemberGeneralService;
import egovframework.coing.member.vo.MemberGeneralVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service("memberGeneralService")
public class MemberGeneralServiceImpl extends EgovAbstractServiceImpl implements MemberGeneralService {
    
    private final MemberGeneralMapper memberGeneralMapper;
    private final CommonService commonService;
    private final UserService userService;
    
    @Override
    public MemberGeneralMapper getMepper() {
        return this.memberGeneralMapper;
    }
    
    
    @Override
    public Map<String, Object> selectMemberGeneralList(MemberGeneralVO vo) throws Exception {
        
        List<MemberGeneralVO> result = new ArrayList<MemberGeneralVO>();
        int cnt = memberGeneralMapper.selectMemberGeneralListCnt(vo);
        if(cnt > 0) {
            result = memberGeneralMapper.selectMemberGeneralList(vo);
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));
        
        return map;
    }
    
    @Override
    public int selectEduMemberGeneralListCnt(MemberGeneralVO searchVO) throws Exception {
        return memberGeneralMapper.selectEduMemberGeneralListCnt(searchVO);
    }
    
    @Override
    public List<MemberGeneralVO> selectEduMemberGeneralList(MemberGeneralVO searchVO) throws Exception {
        return memberGeneralMapper.selectEduMemberGeneralList(searchVO);
    }
    
    @Override
    public MemberGeneralVO selectMemberGeneral(MemberGeneralVO vo) throws Exception {
        
        return memberGeneralMapper.selectMemberGeneral(vo);
    }
    
    @Override
    public void insertMemberGeneral(MemberGeneralVO vo) throws Exception {
        
        if(!"".equals(EgovStringUtil.nullConvert(vo.getMemPasswd()))) {
            vo.setMemPasswd(commonService.encryptPassword(vo.getMemPasswd(), vo.getMemId()));
        }
        
        memberGeneralMapper.insertMemberGeneral(vo);
    }
    
    @Override
    public void updateMemberGeneral(MemberGeneralVO vo) throws Exception {
        
        if(!"".equals(EgovStringUtil.nullConvert(vo.getMemPasswd()))) {
            LoginVO updateVO = new LoginVO();
            updateVO.setId(vo.getMemId());
            updateVO.setPassword(vo.getMemPasswd());
            updateVO.setUserSe("GNR");
            updateVO.setIp(vo.getMemUpdtIp());
            userService.updatePassword(updateVO);
        }
        
        memberGeneralMapper.updateMemberGeneral(vo);
    }
    
    @Override
    public void updateMemberGeneralCheckedLevel(MemberGeneralVO vo) throws Exception {
        
        memberGeneralMapper.updateMemberGeneralCheckedLevel(vo);
    }
    
    @Override
    public void updateMemberGeneralCheckedStatus(MemberGeneralVO vo) throws Exception {
        
        memberGeneralMapper.updateMemberGeneralCheckedStatus(vo);
    }
    
    @Override
    public void deleteMemberGeneral(MemberGeneralVO vo) throws Exception {
        
        memberGeneralMapper.deleteMemberGeneral(vo);
    }
    
    @Override
    public int selectCompanyRegistNumberCnt(MemberGeneralVO vo) throws Exception {
        return memberGeneralMapper.selectCompanyRegistNumberCnt(vo);
    }
    
}