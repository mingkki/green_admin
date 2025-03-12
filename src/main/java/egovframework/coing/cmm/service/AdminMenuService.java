package egovframework.coing.cmm.service;

import egovframework.coing.cmm.vo.AdminMenuVO;

import java.util.List;

public interface AdminMenuService {
    
    List<AdminMenuVO> selectAdminMenuList(AdminMenuVO vo) throws Exception;
    
    List<AdminMenuVO> selectParentAdminMenuList(AdminMenuVO vo) throws Exception;
    
    List<AdminMenuVO> selectChildrenAdminMenuList(AdminMenuVO vo) throws Exception;
    
    int selectAdminMenuRootMenuId(AdminMenuVO vo) throws Exception;
    
    AdminMenuVO selectAdminMenu(AdminMenuVO vo) throws Exception;
    
    AdminMenuVO selectAdminMenuByMenuTy(AdminMenuVO vo) throws Exception;
    
    void insertAdminMenu(AdminMenuVO vo) throws Exception;
    
    void updateAdminMenu(AdminMenuVO vo) throws Exception;
    
    void deleteAdminMenu(AdminMenuVO vo) throws Exception;
    
    void updateAdminMenuMove(String jsonData) throws Exception;
    
}