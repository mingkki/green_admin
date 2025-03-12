package egovframework.coing.resource.service.impl;

import egovframework.coing.resource.vo.ResourceVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper
public interface ResourceMapper {
    
    List<ResourceVO> selectAll();
    
    List<ResourceVO> select(ResourceVO vo);
    
    ResourceVO selectOne(ResourceVO vo);
    
    int count(ResourceVO vo);
    
    void insert(ResourceVO vo);
    
    void update(ResourceVO vo);
    
    void delete(Long id);
    
}