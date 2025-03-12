package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.vo.AdminMenuVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper("adminMenuMapper")
public interface AdminMenuMapper {
    
    List<AdminMenuVO> selectAdminMenuList(AdminMenuVO vo) throws Exception;
    
    List<AdminMenuVO> selectParentAdminMenuList(AdminMenuVO vo) throws Exception;
    
    List<AdminMenuVO> selectChildrenAdminMenuList(AdminMenuVO vo) throws Exception;
    
    Integer selectAdminMenuRootMenuId(AdminMenuVO vo) throws Exception;
    
    AdminMenuVO selectAdminMenu(AdminMenuVO vo) throws Exception;
    
    AdminMenuVO selectAdminMenuByMenuTy(AdminMenuVO vo) throws Exception;
    
    void insertAdminMenu(AdminMenuVO vo) throws Exception;
    
    void updateChildrenAdminMenu(AdminMenuVO vo) throws Exception;
    
    void updateAdminMenu(AdminMenuVO vo) throws Exception;
    
    void updateAdminMenuLftForInsert(HashMap<String, Object> map) throws Exception;
    
    void updateAdminMenuRgtForInsert(HashMap<String, Object> map) throws Exception;
    
    void deleteAdminMenu(AdminMenuVO vo) throws Exception;
    
    void updateAdminMenuMove(AdminMenuVO adminMenuVO) throws Exception;
    
    void updateAdminMenuLftForDelete(HashMap<String, Object> map) throws Exception;
    
    void updateAdminMenuRgtForDelete(HashMap<String, Object> map) throws Exception;
    
}