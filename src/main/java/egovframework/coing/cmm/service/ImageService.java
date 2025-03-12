package egovframework.coing.cmm.service;

import egovframework.coing.cmm.vo.FileVO;
import egovframework.coing.cmm.vo.ImageFileVO;
import egovframework.coing.cmm.vo.ImageInfoVO;

import java.util.List;
import java.util.Map;

public interface ImageService {
    
    Map<String, Object> selectImageInfoList(ImageInfoVO vo) throws Exception;
    
    ImageInfoVO selectImageInfo(ImageInfoVO vo) throws Exception;
    
    void insertImageInfo(ImageInfoVO vo, List<FileVO> uploadFileList) throws Exception;
    
    void updateImageInfo(ImageInfoVO vo, List<FileVO> uploadFileList) throws Exception;
    
    void deleteImageInfo(ImageInfoVO vo) throws Exception;
    
    List<ImageFileVO> selectImageFileList(ImageFileVO vo) throws Exception;
    
    ImageFileVO selectImageFile(ImageFileVO vo) throws Exception;
    
    void insertImageFile(ImageFileVO vo) throws Exception;
    
    void updateImageFile(ImageFileVO vo) throws Exception;
    
    void updateImageFileMemo(ImageFileVO vo) throws Exception;
    
    void updateImageFileDelYn(ImageFileVO vo) throws Exception;
    
    void updateImageFileRebuildNo(ImageFileVO vo) throws Exception;
    
    void deleteImageFile(ImageFileVO vo) throws Exception;
    
}