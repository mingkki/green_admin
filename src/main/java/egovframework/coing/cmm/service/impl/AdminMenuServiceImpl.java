package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.service.AdminMenuService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.vo.AdminMenuVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("adminMenuService")
@RequiredArgsConstructor
public class AdminMenuServiceImpl extends EgovAbstractServiceImpl implements AdminMenuService {
    
    private final AdminMenuMapper adminMenuMapper;
    
    @Override
    public List<AdminMenuVO> selectAdminMenuList(AdminMenuVO vo) throws Exception {
        
        return adminMenuMapper.selectAdminMenuList(vo);
    }
    
    @Override
    public List<AdminMenuVO> selectParentAdminMenuList(AdminMenuVO vo) throws Exception {
        
        return adminMenuMapper.selectParentAdminMenuList(vo);
    }
    
    @Override
    public List<AdminMenuVO> selectChildrenAdminMenuList(AdminMenuVO vo) throws Exception {
        
        return adminMenuMapper.selectChildrenAdminMenuList(vo);
    }
    
    @Override
    public int selectAdminMenuRootMenuId(AdminMenuVO vo) throws Exception {
        
        return adminMenuMapper.selectAdminMenuRootMenuId(vo);
    }
    
    @Override
    public AdminMenuVO selectAdminMenu(AdminMenuVO vo) throws Exception {
        
        return adminMenuMapper.selectAdminMenu(vo);
    }
    
    @Override
    public AdminMenuVO selectAdminMenuByMenuTy(AdminMenuVO vo) throws Exception {
        
        return adminMenuMapper.selectAdminMenuByMenuTy(vo);
    }
    
    @Override
    public void insertAdminMenu(AdminMenuVO vo) throws Exception {
        
        if(vo.getAmeParntsId() < 1) {
            AdminMenuVO adminMenuVO = new AdminMenuVO();
            vo.setAmeParntsId(adminMenuMapper.selectAdminMenuRootMenuId(adminMenuVO));
        }
        
        int parntsId = vo.getAmeParntsId();
        
        AdminMenuVO pvo = null;
        String[] nameArr = vo.getAmeName().split(",");
        HashMap<String, Object> hm = null;
        for(int i = 0; i < nameArr.length; i++) {
            pvo = new AdminMenuVO();
            pvo.setAmeId(parntsId);
            pvo = adminMenuMapper.selectAdminMenu(pvo);
            if(pvo == null) {
                pvo = new AdminMenuVO();
                pvo.setAmeRgt(0);
                pvo.setAmeLvl(0);
            }
            
            hm = new HashMap<String, Object>();
            hm.put("ameLft", pvo.getAmeRgt());
            adminMenuMapper.updateAdminMenuLftForInsert(hm);
            
            hm = new HashMap<String, Object>();
            hm.put("ameRgt", pvo.getAmeRgt() - 1);
            adminMenuMapper.updateAdminMenuRgtForInsert(hm);
            
            vo.setAmeParntsId(parntsId);
            vo.setAmeName(nameArr[i]);
            vo.setAmeLft(pvo.getAmeRgt());
            vo.setAmeRgt(pvo.getAmeRgt() + 1);
            vo.setAmeLvl(pvo.getAmeLvl() + 1);
            adminMenuMapper.insertAdminMenu(vo);
        }
    }
    
    @Override
    public void updateAdminMenu(AdminMenuVO vo) throws Exception {
        
        adminMenuMapper.updateAdminMenu(vo);
    }
    
    @Override
    public void deleteAdminMenu(AdminMenuVO vo) throws Exception {
        
        adminMenuMapper.deleteAdminMenu(vo);
        
        int width = vo.getAmeRgt() - vo.getAmeLft() + 1;
        
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("ameLft", vo.getAmeRgt());
        hm.put("width", width);
        adminMenuMapper.updateAdminMenuLftForDelete(hm);
        
        hm = new HashMap<String, Object>();
        hm.put("ameRgt", vo.getAmeRgt());
        hm.put("width", width);
        adminMenuMapper.updateAdminMenuRgtForDelete(hm);
    }
    
    @Override
    public void updateAdminMenuMove(String jsonData) throws Exception {
        
        Object obj = JSONValue.parse(jsonData);
        JSONArray array = (JSONArray) obj;
        
        int rootMenuId = adminMenuMapper.selectAdminMenuRootMenuId(null);
        
        AdminMenuVO vo = null;
        JSONObject obj2 = null;
        for(int i = 0; i < array.size(); i++) {
            obj2 = (JSONObject) array.get(i);
            if(obj2.get("item_id") == null) {
                continue;
            }
            
            int itemId = Integer.parseInt(EgovStringUtil.nullConvert(obj2.get("item_id")));
            int parentId = obj2.get("parent_id") == null
                    ? rootMenuId
                    : Integer.parseInt(EgovStringUtil.nullConvert(obj2.get("parent_id")));
            int depth = ((Long) obj2.get("depth")).intValue();
            int left = ((Long) obj2.get("left")).intValue();
            int right = ((Long) obj2.get("right")).intValue();
            
            vo = new AdminMenuVO();
            vo.setAmeId(itemId);
            vo.setAmeParntsId(parentId);
            vo.setAmeLvl(depth + 1);
            vo.setAmeLft(left - 1);
            vo.setAmeRgt(right - 1);
            
            adminMenuMapper.updateAdminMenuMove(vo);
        }
    }
    
}