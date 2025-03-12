package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.service.DbTableService;
import egovframework.coing.cmm.vo.DbTableColumnVO;
import egovframework.coing.cmm.vo.DbTableInfoVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service("dbTableService")
public class DbTableServiceImpl extends EgovAbstractServiceImpl implements DbTableService {
    
    private final DbTableMapper dbTableMapper;
    
    @Override
    public Map<String, Object> selectDbTableInfoList(DbTableInfoVO vo) throws Exception {
        
        List<DbTableInfoVO> result = new ArrayList<DbTableInfoVO>();
        int cnt = dbTableMapper.selectDbTableInfoListCnt(vo);
        if(cnt > 0) {
            result = dbTableMapper.selectDbTableInfoList(vo);
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));
        
        return map;
    }
    
    @Override
    public DbTableInfoVO selectDbTableInfo(DbTableInfoVO vo) throws Exception {
        
        return dbTableMapper.selectDbTableInfo(vo);
    }
    
    @Override
    public List<DbTableColumnVO> selectDbTableColumnList(DbTableColumnVO vo) throws Exception {
        
        return dbTableMapper.selectDbTableColumnList(vo);
    }
    
    @Override
    public List<DbTableInfoVO> selectDbTableInfoListAll(DbTableInfoVO vo) throws Exception {
        
        return dbTableMapper.selectDbTableInfoListAll(vo);
    }
    
}