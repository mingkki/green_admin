package egovframework.coing.cmm.util;

import egovframework.coing.cmm.service.AdminMenuService;
import egovframework.coing.cmm.vo.AdminMenuVO;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AdminMenuHelper {
    
    private HttpServletRequest request;
    private WebApplicationContext context;
    private AdminMenuService adminMenuService;
    
    private List<AdminMenuVO> adminMenuList = null;
    
    public AdminMenuHelper(HttpServletRequest request) throws Exception {
        
        this.request = request;
        this.context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        this.adminMenuService = (AdminMenuService) this.context.getBean("adminMenuService");
    }
    
    public List<AdminMenuVO> getParentMenuList(int menuId) throws Exception {
        
        AdminMenuVO vo = new AdminMenuVO();
        vo.setAmeId(menuId);
        
        return adminMenuService.selectParentAdminMenuList(vo);
    }
    
    public List<AdminMenuVO> getMenuList(int parntsMenuId) throws Exception {
        
        ArrayList<AdminMenuVO> menuList = new ArrayList<AdminMenuVO>();
        
        if(adminMenuList == null) {
            adminMenuList = adminMenuService.selectAdminMenuList(new AdminMenuVO());
        }
        
        for(int i = 0; i < adminMenuList.size(); i++) {
            
            AdminMenuVO menuVO = (AdminMenuVO) adminMenuList.get(i);
            if(menuVO.getAmeParntsId() != parntsMenuId) {
                continue;
            }
            
            if("N".equals(menuVO.getAmeShowYn()) || "N".equals(menuVO.getAmeUseYn())) {
                continue;
            }
            
            menuList.add(menuVO);
        }
        
        return menuList;
    }
    
}