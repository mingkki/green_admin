package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.service.ImageService;
import egovframework.coing.cmm.util.EgovStringUtil;
import egovframework.coing.cmm.vo.FileVO;
import egovframework.coing.cmm.vo.ImageFileVO;
import egovframework.coing.cmm.vo.ImageInfoVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service("imageService")
public class ImageServiceImpl extends EgovAbstractServiceImpl implements ImageService {
    
    private final ImageMapper imageMapper;
    private final EgovIdGnrService egovImageInfoIdGnrService;
    private final EgovIdGnrService egovImageFileIdGnrService;
    
    @Override
    public Map<String, Object> selectImageInfoList(ImageInfoVO vo) throws Exception {
        
        List<ImageInfoVO> result = new ArrayList<ImageInfoVO>();
        int cnt = imageMapper.selectImageInfoListCnt(vo);
        if(cnt > 0) {
            result = imageMapper.selectImageInfoList(vo);
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));
        
        return map;
    }
    
    @Override
    public ImageInfoVO selectImageInfo(ImageInfoVO vo) throws Exception {
        
        return imageMapper.selectImageInfo(vo);
    }
    
    @Override
    public void insertImageInfo(ImageInfoVO vo, List<FileVO> uploadFileList) throws Exception {
        
        vo.setImgId(egovImageInfoIdGnrService.getNextIntegerId());
        imageMapper.insertImageInfo(vo);
        
        if(uploadFileList != null && uploadFileList.size() > 0) {
            ImageFileVO vo2 = null;
            String path = "/image/";
            String ext = "";
            String thumb1 = "";
            String thumb2 = "";
            for(int i = 0; i < uploadFileList.size(); i++) {
                ext = uploadFileList.get(i).getFileExtsn().toLowerCase();
                thumb1 = EgovStringUtil.nullConvert(uploadFileList.get(i).getThumbFileNm());
                thumb2 = EgovStringUtil.nullConvert(uploadFileList.get(i).getThumb2FileNm());
                vo2 = new ImageFileVO();
                vo2.setImgId(vo.getImgId());
                vo2.setImfId(egovImageFileIdGnrService.getNextIntegerId());
                vo2.setImfNo(uploadFileList.get(i).getFileSn());
                vo2.setImfName(path + uploadFileList.get(i).getStreFileNm());
                vo2.setImfTname1(!"".equals(thumb1)
                        ? path + thumb1
                        : "");
                vo2.setImfTname2(!"".equals(thumb2)
                        ? path + thumb2
                        : "");
                vo2.setImfOname(uploadFileList.get(i).getOrignlFileNm());
                vo2.setImfExt(ext);
                vo2.setImfSize(uploadFileList.get(i).getFileMg());
                try {
                    vo2.setImfMemo(vo.getUploadFileMemo()[i]);
                } catch(Exception e) {
                    vo2.setImfMemo("");
                } finally {
                    if("".equals(vo2.getImfMemo())) {
                        vo2.setImfMemo(vo2.getImfOname());
                    }
                }
                vo2.setImfRegId(vo.getImgRegId());
                vo2.setImfRegIp(vo.getImgRegIp());
                imageMapper.insertImageFile(vo2);
            }
            
            // 파일순번 재조절
            vo2 = new ImageFileVO();
            vo2.setImgId(vo.getImgId());
            imageMapper.updateImageFileRebuildNo(vo2);
        }
    }
    
    @Override
    public void updateImageInfo(ImageInfoVO vo, List<FileVO> uploadFileList) throws Exception {
        
        imageMapper.updateImageInfo(vo);
        
        // 파일설명 업데이트
        if(vo.getUploadFileMemo() != null && vo.getUploadFileMemo().length > 0) {
            ImageFileVO vo2 = null;
            for(int i = 0; i < vo.getUploadFileMemo().length; i++) {
                vo2 = new ImageFileVO();
                vo2.setImgId(vo.getImgId());
                vo2.setImfNo(Integer.toString(i));
                vo2.setImfMemo(vo.getUploadFileMemo()[i]);
                imageMapper.updateImageFileMemo(vo2);
            }
        }
        
        // 첨부파일 삭제
        if(vo.getDeleteUploadFile() != null && vo.getDeleteUploadFile().length > 0) {
            ImageFileVO vo2 = null;
            for(int i = 0; i < vo.getDeleteUploadFile().length; i++) {
                vo2 = new ImageFileVO();
                vo2.setImgId(vo.getImgId());
                vo2.setImfNo(vo.getDeleteUploadFile()[i]);
                vo2.setImfDelId(vo.getImgUpdtId());
                vo2.setImfDelIp(vo.getImgUpdtIp());
                imageMapper.updateImageFileDelYn(vo2);
            }
            
            // 파일순번 재조절
            vo2 = new ImageFileVO();
            vo2.setImgId(vo.getImgId());
            imageMapper.updateImageFileRebuildNo(vo2);
        }
        
        if(uploadFileList != null && uploadFileList.size() > 0) {
            ImageFileVO vo2 = null;
            String path = "/image/";
            String ext = "";
            String thumb1 = "";
            String thumb2 = "";
            for(int i = 0; i < uploadFileList.size(); i++) {
                vo2 = new ImageFileVO();
                vo2.setImgId(vo.getImgId());
                vo2.setImfNo(uploadFileList.get(i).getFileSn());
                vo2 = imageMapper.selectImageFile(vo2);
                if(vo2 != null) {
                    imageMapper.updateImageFileDelYn(vo2);
                }
                
                ext = uploadFileList.get(i).getFileExtsn().toLowerCase();
                thumb1 = EgovStringUtil.nullConvert(uploadFileList.get(i).getThumbFileNm());
                thumb2 = EgovStringUtil.nullConvert(uploadFileList.get(i).getThumb2FileNm());
                
                vo2 = new ImageFileVO();
                vo2.setImfId(egovImageFileIdGnrService.getNextIntegerId());
                vo2.setImgId(vo.getImgId());
                vo2.setImfNo(uploadFileList.get(i).getFileSn());
                vo2.setImfName(path + uploadFileList.get(i).getStreFileNm());
                vo2.setImfTname1(!"".equals(thumb1)
                        ? path + thumb1
                        : "");
                vo2.setImfTname2(!"".equals(thumb2)
                        ? path + thumb2
                        : "");
                vo2.setImfOname(uploadFileList.get(i).getOrignlFileNm());
                vo2.setImfExt(ext);
                vo2.setImfSize(uploadFileList.get(i).getFileMg());
                try {
                    vo2.setImfMemo(vo.getUploadFileMemo()[Integer.parseInt(vo2.getImfNo())]);
                } catch(Exception e) {
                    vo2.setImfMemo("");
                } finally {
                    if("".equals(vo2.getImfMemo())) {
                        vo2.setImfMemo(vo2.getImfOname());
                    }
                }
                vo2.setImfRegId(vo.getImgUpdtId());
                vo2.setImfRegIp(vo.getImgUpdtIp());
                imageMapper.insertImageFile(vo2);
            }
        }
    }
    
    @Override
    public void deleteImageInfo(ImageInfoVO vo) throws Exception {
        
        imageMapper.deleteImageInfo(vo);
    }
    
    @Override
    public List<ImageFileVO> selectImageFileList(ImageFileVO vo) throws Exception {
        
        return imageMapper.selectImageFileList(vo);
    }
    
    @Override
    public ImageFileVO selectImageFile(ImageFileVO vo) throws Exception {
        
        return imageMapper.selectImageFile(vo);
    }
    
    @Override
    public void insertImageFile(ImageFileVO vo) throws Exception {
        
        imageMapper.insertImageFile(vo);
    }
    
    @Override
    public void updateImageFile(ImageFileVO vo) throws Exception {
        
        imageMapper.updateImageFile(vo);
    }
    
    @Override
    public void updateImageFileDelYn(ImageFileVO vo) throws Exception {
        
        imageMapper.updateImageFileDelYn(vo);
    }
    
    @Override
    public void deleteImageFile(ImageFileVO vo) throws Exception {
        
        imageMapper.deleteImageFile(vo);
    }
    
    @Override
    public void updateImageFileRebuildNo(ImageFileVO vo) throws Exception {
        
        imageMapper.updateImageFileRebuildNo(vo);
    }
    
    @Override
    public void updateImageFileMemo(ImageFileVO vo) throws Exception {
        
        imageMapper.updateImageFileMemo(vo);
    }
    
}