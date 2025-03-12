package egovframework.coing.member.service.impl;

import egovframework.coing.member.vo.MemberGeneralVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("memberGeneralMapper")
public interface MemberGeneralMapper {
    
    Integer selectMemberGeneralListCnt(MemberGeneralVO vo) throws Exception;
    
    List<MemberGeneralVO> selectMemberGeneralList(MemberGeneralVO vo) throws Exception;
    
    Integer selectEduMemberGeneralListCnt(MemberGeneralVO vo) throws Exception;
    
    List<MemberGeneralVO> selectEduMemberGeneralList(MemberGeneralVO vo) throws Exception;
    
    MemberGeneralVO selectMemberGeneral(MemberGeneralVO vo) throws Exception;
    
    void insertMemberGeneral(MemberGeneralVO vo) throws Exception;
    
    void updateMemberGeneral(MemberGeneralVO vo) throws Exception;
    
    void updateMemberGeneralCheckedLevel(MemberGeneralVO vo) throws Exception;
    
    void updateMemberGeneralCheckedStatus(MemberGeneralVO vo) throws Exception;
    
    void deleteMemberGeneral(MemberGeneralVO vo) throws Exception;
    
    Integer selectCompanyRegistNumberCnt(MemberGeneralVO vo) throws Exception;
    
}