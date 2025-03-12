package egovframework.coing.cmm.web;

import egovframework.coing.cmm.CmsManager;
import egovframework.coing.cmm.Globals;
import egovframework.coing.cmm.service.DbTableService;
import egovframework.coing.cmm.util.*;
import egovframework.coing.cmm.vo.DbTableColumnVO;
import egovframework.coing.cmm.vo.DbTableInfoVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/db/table.do")
public class DbTableController {
    
    private final String CONTENT_PATH = String.format("%s/egovframework/coing/", CmsManager.getModulePath());
    private final EgovPropertyService propertiesService;
    private final DbTableService dbTableService;
    
    private void initParam(DbTableInfoVO searchVO) {
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("searchCondition", searchVO.getSearchCondition());
        param.put("searchKeyword", searchVO.getSearchKeyword());
        param.put("pageIndex", searchVO.getPageIndex());
        
        searchVO.setTableSchema(EgovProperties.getProperty("Globals.DbTableSchema"));
        searchVO.setQueryString(MapQuery.urlEncodeUTF8(param));
    }
    
    @RequestMapping()
    public String list(@ModelAttribute("searchDbTableInfoVO") DbTableInfoVO searchVO,
                       @RequestParam Map<String, Object> paramMap,
                       HttpServletRequest request,
                       ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
        searchVO.setPageSize(propertiesService.getInt("pageSize"));
        
        PaginationInfo paginationInfo = new PaginationInfo();
        
        paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
        paginationInfo.setPageSize(searchVO.getPageSize());
        
        searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
        searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(paramMap);
        param.put("pageIndex", null);
        String paginationQueryString = MapQuery.urlEncodeUTF8(param);
        
        Map<String, Object> map = dbTableService.selectDbTableInfoList(searchVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));
        
        paginationInfo.setTotalRecordCount(totCnt);
        
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("paginationQueryString", paginationQueryString);
        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "dbtable_list.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=view", method = RequestMethod.GET)
    public String view(@ModelAttribute("searchDbTableInfoVO") DbTableInfoVO searchVO,
                       @RequestParam(value = "tableName", required = false, defaultValue = "") String tableName,
                       ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        DbTableInfoVO dbTableInfoVO = dbTableService.selectDbTableInfo(searchVO);
        if(dbTableInfoVO == null) {
        
        }
        
        tableName = EgovWebUtil.removeSQLInjectionRisk(tableName);
        tableName = tableName.replace("'", "");
        
        DbTableColumnVO dbTableColumnVO = new DbTableColumnVO();
        dbTableColumnVO.setTableName(tableName);
        List<DbTableColumnVO> dbTableColumnList = dbTableService.selectDbTableColumnList(dbTableColumnVO);
        
        model.addAttribute("dbTableInfoVO", dbTableInfoVO);
        model.addAttribute("dbTableColumnList", dbTableColumnList);
        
        model.addAttribute("CONTENT_FILE", CONTENT_PATH + "dbtable_view.jsp");
        return "egovframework/coing/common/admin_view";
    }
    
    @RequestMapping(params = "act=download")
    public String download(@ModelAttribute("searchDbTableInfoVO") DbTableInfoVO searchVO, ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        String filePath = Globals.ROOT_PATH + "/WEB-INF/jsp/egovframework/coing/dbtable_form.html";
        filePath = filePath.replace('\\', File.separatorChar).replace('/', File.separatorChar);
        
        String content = "";
        String line = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
        while((line = br.readLine()) != null) {
            content = content + line;
        }
        br.close();
        
        String content2 = "";
        String line2 = null;
        String filePath2 = Globals.ROOT_PATH + "/WEB-INF/jsp/egovframework/coing/dbtable_content.html";
        
        BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath2), "UTF-8"));
        while((line2 = br2.readLine()) != null) {
            content2 = content2 + line2;
        }
        br2.close();
        
        List<DbTableInfoVO> list = dbTableService.selectDbTableInfoListAll(searchVO);
        
        String tmp = "";
        String tmp2 = "";
        for(int i = 0; i < list.size(); i++) {
            tmp2 = "";
            tmp2 = content2;
            tmp2 = tmp2.replace("{일련번호}", Integer.toString(i + 1));
            tmp2 = tmp2.replace("{테이블명}", EgovStringUtil.nullConvert(list.get(i).getTableName()));
            tmp2 = tmp2.replace("{테이블설명}", EgovStringUtil.nullConvert(list.get(i).getTableComment()));
            tmp2 = tmp2.replace("{행개수}", Integer.toString(i + 1));
            tmp += tmp2;
        }
        content = content.replace("{테이블행개수}", Integer.toString(1 + list.size()));
        content = content.replace("{내용}", tmp);
        
        model.addAttribute("content", content);
        return "egovframework/coing/dbtable_download";
    }
    
    @RequestMapping(params = "act=download2")
    public String download2(@ModelAttribute("searchDbTableInfoVO") DbTableInfoVO searchVO, ModelMap model) throws Exception {
        
        initParam(searchVO);
        
        String content = "";
        String line = null;
        String filePath = Globals.ROOT_PATH + "/WEB-INF/jsp/egovframework/coing/dbtable_form02.html";
        
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
        while((line = br.readLine()) != null) {
            content = content + line;
        }
        br.close();
        
        String content2 = "";
        String line2 = null;
        String filePath2 = Globals.ROOT_PATH + "/WEB-INF/jsp/egovframework/coing/dbtable_content02.html";
        
        BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath2), "UTF-8"));
        while((line2 = br2.readLine()) != null) {
            content2 = content2 + line2;
        }
        br2.close();
        
        String content3 = "";
        String line3 = null;
        String filePath3 = Globals.ROOT_PATH + "/WEB-INF/jsp/egovframework/coing/dbtable_content03.html";
        
        BufferedReader br3 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath3), "UTF-8"));
        while((line3 = br3.readLine()) != null) {
            content3 = content3 + line3;
        }
        br3.close();
        
        List<DbTableInfoVO> list = dbTableService.selectDbTableInfoListAll(searchVO);
        
        String tmp = "";
        if(list.size() > 0) {
            for(int i = 0; i < list.size(); i++) {
                String tmp2 = "";
                tmp2 = content2;
                tmp2 = tmp2.replace("{주제영역명}", EgovStringUtil.nullConvert(list.get(i).getTableComment()));
                tmp2 = tmp2.replace("{작성일}", EgovDateUtil.formatDate(EgovDateUtil.getToday(), "."));
                tmp2 = tmp2.replace("{테이블ID}", EgovStringUtil.nullConvert(list.get(i).getTableName()));
                tmp2 = tmp2.replace("{테이블명}", EgovStringUtil.nullConvert(list.get(i).getTableComment()));
                
                DbTableColumnVO vo = new DbTableColumnVO();
                vo.setTableName(list.get(i).getTableName());
                
                
                List<DbTableColumnVO> list2 = dbTableService.selectDbTableColumnList(vo);
                String tmp3 = "";
                for(int j = 0; j < list2.size(); j++) {
                    String tmp4 = "";
                    
                    tmp4 = content3;
                    tmp4 = tmp4.replace("{필드명}", EgovStringUtil.nullConvert(list2.get(j).getComment()));
                    tmp4 = tmp4.replace("{필드ID}", EgovStringUtil.nullConvert(list2.get(j).getField()));
                    tmp4 = tmp4.replace("{타입}", EgovStringUtil.nullConvert(list2.get(j).getType()));
                    tmp4 = tmp4.replace("{데이터정렬방식}", EgovStringUtil.nullConvert(list2.get(j).getCollation()));
                    tmp4 = tmp4.replace("{기본값}", EgovStringUtil.nullConvert(list2.get(j).getDefaultValue()));
                    tmp4 = tmp4.replace("{키}", EgovStringUtil.nullConvert(list2.get(j).getKey()));
                    tmp4 = tmp4.replace("{널}", EgovStringUtil.nullConvert(list2.get(j).getNullYn()));
                    tmp4 = tmp4.replace("{비고}", EgovStringUtil.nullConvert(list2.get(j).getExtra()));
                    tmp4 = tmp4.replace("{행번호}", Integer.toString(j + 5));
                    tmp3 += tmp4;
                }
                tmp2 = tmp2.replace("{내용2}", tmp3);
                tmp2 = tmp2.replace("{테이블행개수}", Integer.toString(5 + list2.size()));
                tmp += tmp2;
            }
        }
        
        content = content.replace("{내용1}", tmp);
        
        model.addAttribute("content", content);
        return "egovframework/coing/dbtable_download2";
    }
}