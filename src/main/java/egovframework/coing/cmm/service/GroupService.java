package egovframework.coing.cmm.service;

import egovframework.coing.cmm.vo.GroupVO;

import java.util.List;

public interface GroupService {
    
    List<GroupVO> selectGroupList(GroupVO vo) throws Exception;
    
    GroupVO selectGroup(GroupVO vo) throws Exception;
    
    void insertGroup(GroupVO vo) throws Exception;
    
    void updateGroup(GroupVO vo) throws Exception;
    
    void deleteGroup(GroupVO vo) throws Exception;
    
}