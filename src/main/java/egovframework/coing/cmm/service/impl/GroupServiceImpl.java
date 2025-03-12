package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.service.GroupService;
import egovframework.coing.cmm.vo.GroupVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("groupService")
public class GroupServiceImpl extends EgovAbstractServiceImpl implements GroupService {
    
    private final GroupMapper groupMapper;
    
    @Override
    public List<GroupVO> selectGroupList(GroupVO vo) throws Exception {
        
        return groupMapper.selectGroupList(vo);
    }
    
    @Override
    public GroupVO selectGroup(GroupVO vo) throws Exception {
        
        return groupMapper.selectGroup(vo);
    }
    
    @Override
    public void insertGroup(GroupVO vo) throws Exception {
        
        groupMapper.insertGroup(vo);
    }
    
    @Override
    public void updateGroup(GroupVO vo) throws Exception {
        
        groupMapper.updateGroup(vo);
    }
    
    @Override
    public void deleteGroup(GroupVO vo) throws Exception {
        
        groupMapper.deleteGroup(vo);
    }
    
}