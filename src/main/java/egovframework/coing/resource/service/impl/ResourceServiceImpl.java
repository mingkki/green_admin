package egovframework.coing.resource.service.impl;

import egovframework.coing.resource.service.ResourceService;
import egovframework.coing.resource.vo.ResourceVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("resourceService")
@RequiredArgsConstructor
public class ResourceServiceImpl extends EgovAbstractServiceImpl implements ResourceService {
    
    private final ResourceMapper resourceMapper;
    
    @Override
    public List<ResourceVO> select(ResourceVO vo) throws Exception {
        return resourceMapper.select(vo);
    }
    
    @Override
    public ResourceVO selectOne(ResourceVO vo) throws Exception {
        return resourceMapper.selectOne(vo);
    }
    
    @Override
    public int count(ResourceVO vo) throws Exception {
        return resourceMapper.count(vo);
    }
    
    @Override
    public int insert(ResourceVO vo) throws Exception {
        resourceMapper.insert(vo);
        
        return 1;
    }
    
    @Override
    public int update(ResourceVO vo) throws Exception {
        resourceMapper.update(vo);
        
        return 1;
    }
    
    @Override
    public int delete(ResourceVO vo) throws Exception {
        resourceMapper.delete(vo.getId());
        
        return 1;
    }
    
    @Override
    public int delete(Long id) throws Exception {
        resourceMapper.delete(id);
        
        return 1;
    }
    
}