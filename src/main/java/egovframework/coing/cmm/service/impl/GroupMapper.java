package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.vo.GroupVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("groupMapper")
public interface GroupMapper {
    
    List<GroupVO> selectGroupList(GroupVO vo) throws Exception;
    
    GroupVO selectGroup(GroupVO vo) throws Exception;
    
    void insertGroup(GroupVO vo) throws Exception;
    
    void updateGroup(GroupVO vo) throws Exception;
    
    void deleteGroup(GroupVO vo) throws Exception;
    
}