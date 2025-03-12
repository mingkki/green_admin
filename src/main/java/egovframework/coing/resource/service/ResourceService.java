package egovframework.coing.resource.service;

import egovframework.coing.resource.vo.ResourceVO;

import java.util.List;

public interface ResourceService {
    
    List<ResourceVO> select(ResourceVO vo) throws Exception;
    
    ResourceVO selectOne(ResourceVO vo) throws Exception;
    
    int count(ResourceVO vo) throws Exception;
    
    int insert(ResourceVO vo) throws Exception;
    
    int update(ResourceVO vo) throws Exception;
    
    int delete(ResourceVO vo) throws Exception;
    
    int delete(Long id) throws Exception;
    
}