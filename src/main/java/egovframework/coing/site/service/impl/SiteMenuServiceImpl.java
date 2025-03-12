package egovframework.coing.site.service.impl;

import egovframework.coing.cmm.Globals;
import egovframework.coing.cmm.util.EgovFileTool;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.vo.SearchDocumentVO;
import egovframework.coing.site.indexer.SiteMenuIndexer;
import egovframework.coing.site.service.SiteMenuService;
import egovframework.coing.site.vo.SiteMenuJsonVO;
import egovframework.coing.site.vo.SiteMenuVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service("siteMenuService")
public class SiteMenuServiceImpl extends EgovAbstractServiceImpl implements SiteMenuService {
    
    private final SiteMenuMapper siteMenuMapper;
    
    @Override
    public List<SiteMenuVO> selectSiteMenuList(SiteMenuVO vo) throws Exception {
        
        return siteMenuMapper.selectSiteMenuList(vo);
    }
    
    @Override
    public List<SiteMenuVO> selectParentSiteMenuList(SiteMenuVO vo) throws Exception {
        
        return siteMenuMapper.selectParentSiteMenuList(vo);
    }
    
    @Override
    public List<SiteMenuVO> selectChildrenSiteMenuList(SiteMenuVO vo) throws Exception {
        
        return siteMenuMapper.selectChildrenSiteMenuList(vo);
    }
    
    @Override
    public SiteMenuVO selectSiteMenu(SiteMenuVO vo) throws Exception {
        
        return siteMenuMapper.selectSiteMenu(vo);
    }
    
    @Override
    public int selectSiteMenuRootMenuId(SiteMenuVO vo) throws Exception {
        
        return siteMenuMapper.selectSiteMenuRootMenuId(vo);
    }
    
    @Override
    public void insertSiteMenu(SiteMenuVO vo) throws Exception {
        
        if(vo.getSmeParntsId() < 1) {
            SiteMenuVO siteMenuVO = new SiteMenuVO();
            siteMenuVO.setSinId(vo.getSinId());
            vo.setSmeParntsId(selectSiteMenuRootMenuId(siteMenuVO));
        }
        
        int smeParntsId = vo.getSmeParntsId();
        
        SiteMenuVO pvo = null;
        String[] menuNm = vo.getSmeName().split(",");
        HashMap<String, Object> hm = null;
        for(int i = 0; i < menuNm.length; i++) {
            pvo = new SiteMenuVO();
            pvo.setSinId(vo.getSinId());
            pvo.setSmeId(smeParntsId);
            pvo = selectSiteMenu(pvo);
            if(pvo == null) {
                pvo = new SiteMenuVO();
                pvo.setSinId(vo.getSinId());
                pvo.setSmeRgt(0);
                pvo.setSmeLvl(0);
            }
            
            hm = new HashMap<String, Object>();
            hm.put("sinId", pvo.getSinId());
            hm.put("smeLft", pvo.getSmeRgt());
            siteMenuMapper.updateSiteMenuLftForInsert(hm);
            
            hm = new HashMap<String, Object>();
            hm.put("sinId", pvo.getSinId());
            hm.put("smeRgt", pvo.getSmeRgt() - 1);
            siteMenuMapper.updateSiteMenuRgtForInsert(hm);
            
            vo.setSmeParntsId(smeParntsId);
            vo.setSmeName(menuNm[i]);
            vo.setSmeLft(pvo.getSmeRgt());
            vo.setSmeRgt(pvo.getSmeRgt() + 1);
            vo.setSmeLvl(pvo.getSmeLvl() + 1);
            
            siteMenuMapper.insertSiteMenu(vo);
        }
    }
    
    @Override
    public void updateSiteMenu(SiteMenuVO vo) throws Exception {
        
        if("Y".equals(vo.getUpdateChildrenDesign())) {
            siteMenuMapper.updateChildrenSiteMenu(vo);
        }
        
        siteMenuMapper.updateSiteMenu(vo);
    }
    
    @Override
    public void deleteSiteMenu(SiteMenuVO vo) throws Exception {
        
        siteMenuMapper.deleteSiteMenu(vo);
        
        int width = vo.getSmeRgt() - vo.getSmeLft() + 1;
        
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("sinId", vo.getSinId());
        hm.put("smeLft", vo.getSmeRgt());
        hm.put("width", width);
        siteMenuMapper.updateSiteMenuLftForDelete(hm);
        
        hm = new HashMap<String, Object>();
        hm.put("sinId", vo.getSinId());
        hm.put("smeRgt", vo.getSmeRgt());
        hm.put("width", width);
        siteMenuMapper.updateSiteMenuRgtForDelete(hm);
    }
    
    @Override
    public void deleteSiteMenuBySiteId(SiteMenuVO vo) throws Exception {
        
        siteMenuMapper.deleteSiteMenuBySiteId(vo);
    }
    
    @Override
    public void updateSiteMenuMove(String sinId, String jsonData) throws Exception {
        
        if(!"".equals(EgovStringUtil.nullConvert(jsonData))) {
            Object obj = JSONValue.parse(jsonData);
            JSONArray array = (JSONArray) obj;
            
            SiteMenuVO searchVO = new SiteMenuVO();
            searchVO.setSinId(sinId);
            int rootMenuId = selectSiteMenuRootMenuId(searchVO);
            
            SiteMenuVO vo = null;
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
                
                vo = new SiteMenuVO();
                vo.setSinId(sinId);
                vo.setSmeId(itemId);
                vo.setSmeParntsId(parentId);
                vo.setSmeLvl(depth + 1);
                vo.setSmeLft(left - 1);
                vo.setSmeRgt(right - 1);
                
                siteMenuMapper.updateSiteMenuMove(vo);
            }
        }
    }
    
    @Override
    public void siteMenuDistribute(String sinId) throws Exception {
        
        SiteMenuJsonVO searchSiteMenuJsonVO = new SiteMenuJsonVO();
        searchSiteMenuJsonVO.setSinId(sinId);
        List<SiteMenuJsonVO> list = siteMenuMapper.selectSiteMenuJsonList(searchSiteMenuJsonVO);
        
        String filePath = Globals.DISTRIBUTE_PATH + "/" + sinId + "/data/menu.ser";
        EgovFileTool.createNewFile(filePath);
        
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
        oos.writeObject(list);
        oos.close();
        
        SearchDocumentVO searchDocumentVO = new SearchDocumentVO();
        searchDocumentVO.setSiteId(sinId);
        List<SearchDocumentVO> searchDocumentList = siteMenuMapper.selectSearchSiteMenuList(searchDocumentVO);
        
        SiteMenuIndexer siteMenuIndexer = new SiteMenuIndexer();
        siteMenuIndexer.create(searchDocumentList);
    }
    
    @Override
    public List<SiteMenuVO> selectParntsSiteMenuList(SiteMenuVO vo) throws Exception {
        return siteMenuMapper.selectParntsSiteMenuList(vo);
    }
    
    @Override
    public List<SiteMenuVO> selectChldrnSiteMenuList(SiteMenuVO vo) throws Exception {
        return siteMenuMapper.selectChldrnSiteMenuList(vo);
    }
    
    @Override
    public void siteMenuDistribute(HttpServletRequest request, String sinId) throws Exception {
        SiteMenuJsonVO searchSiteMenuJsonVO = new SiteMenuJsonVO();
        searchSiteMenuJsonVO.setSinId(sinId);
        List<SiteMenuJsonVO> list = siteMenuMapper.selectSiteMenuJsonList(searchSiteMenuJsonVO);
        
        String filePath = Globals.DISTRIBUTE_PATH + "/sites/" + sinId + "/data/menu.ser";
        EgovFileTool.createNewFile(filePath);
        
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
        oos.writeObject(list);
        oos.close();
        
    }
    
}