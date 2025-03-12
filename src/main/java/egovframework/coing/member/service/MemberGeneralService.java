package egovframework.coing.member.service;

import egovframework.coing.member.service.impl.MemberGeneralMapper;
import egovframework.coing.member.vo.MemberGeneralVO;

import java.util.List;
import java.util.Map;

public interface MemberGeneralService {
    
    MemberGeneralMapper getMepper();
    
    Map<String, Object> selectMemberGeneralList(MemberGeneralVO vo) throws Exception;
    
    int selectEduMemberGeneralListCnt(MemberGeneralVO searchVO) throws Exception;
    
    List<MemberGeneralVO> selectEduMemberGeneralList(MemberGeneralVO vo) throws Exception;
    
    MemberGeneralVO selectMemberGeneral(MemberGeneralVO vo) throws Exception;
    
    void insertMemberGeneral(MemberGeneralVO vo) throws Exception;
    
    void updateMemberGeneral(MemberGeneralVO vo) throws Exception;
    
    void updateMemberGeneralCheckedLevel(MemberGeneralVO vo) throws Exception;
    
    void updateMemberGeneralCheckedStatus(MemberGeneralVO vo) throws Exception;
    
    void deleteMemberGeneral(MemberGeneralVO vo) throws Exception;
    
    int selectCompanyRegistNumberCnt(MemberGeneralVO vo) throws Exception;
}