package egovframework.coing.site.service.impl;

import egovframework.coing.cmm.Globals;
import egovframework.coing.cmm.util.EgovFileTool;
import egovframework.coing.site.service.SiteInfoService;
import egovframework.coing.site.vo.SiteInfoVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service("siteInfoService")
public class SiteInfoServiceImpl extends EgovAbstractServiceImpl implements SiteInfoService {
    
    private final SiteInfoMapper siteInfoMapper;
    private final SiteMenuMapper siteMenuMapper;

    @Override
    public List<SiteInfoVO> selectSiteInfoListAll(SiteInfoVO vo) throws Exception {
        
        return siteInfoMapper.selectSiteInfoListAll(vo);
    }
    
    @Override
    public Map<String, Object> selectSiteInfoList(SiteInfoVO vo) throws Exception {
        
        List<SiteInfoVO> result = new ArrayList<SiteInfoVO>();
        int cnt = siteInfoMapper.selectSiteInfoListCnt(vo);
        if(cnt > 0) {
            result = siteInfoMapper.selectSiteInfoList(vo);
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));
        
        return map;
    }
    
    
    @Override
    public SiteInfoVO selectSiteInfo(SiteInfoVO vo) throws Exception {
        
        return siteInfoMapper.selectSiteInfo(vo);
    }
    
    @Override //추가건X
    public int checkSiteId(SiteInfoVO vo) throws Exception {
        
        return siteInfoMapper.checkSiteId(vo);
    }
    
    @Override //추가건X
    public int checkSiteDomain(SiteInfoVO vo) throws Exception {
        
        return siteInfoMapper.checkSiteDomain(vo);
    }
    
    @Override //추가건과 상이
    public void insertSiteInfo(SiteInfoVO vo) throws Exception {
        
        siteInfoMapper.insertSiteInfo(vo);
        
        String createPath = Globals.DISTRIBUTE_PATH + "/" + vo.getSinId();
        createPath = createPath.replace('\\', File.separatorChar).replace('/', File.separatorChar);
        
        File createDir = new File(createPath);
        createDir.mkdir();
        
        // index.jsp 생성
        String file = "<%@ page contentType=\"text/html; charset=utf-8\" pageEncoding=\"utf-8\" %>\n";
        file += "<%\n";
        file += "response.sendRedirect(\"/" + vo.getSinId() + "/index.do\");";
        file += "%>";
        
        String fileAbsolutePath = EgovFileTool.createNewFile(createPath.concat("/index.jsp"));
        FileUtils.writeStringToFile(new File(fileAbsolutePath), file, "UTF-8");
    }
    
    @Override
    public void updateSiteInfo(SiteInfoVO vo) throws Exception {
        
        siteInfoMapper.updateSiteInfo(vo);
        
        String createPath = Globals.DISTRIBUTE_PATH + "/" + vo.getSinId();
        createPath = createPath.replace('\\', File.separatorChar).replace('/', File.separatorChar);
        
        File createDir = new File(createPath);
        if(!createDir.isDirectory()) {
            createDir.mkdir();
        }
    }
    
    @Override //추가건과 상이
    public void deleteSiteInfo(SiteInfoVO vo) throws Exception {
        
        siteInfoMapper.deleteSiteInfo(vo);
    }
    
}