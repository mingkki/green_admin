package egovframework.coing.cmm.service.impl;

import egovframework.coing.cmm.vo.ImageFileVO;
import egovframework.coing.cmm.vo.ImageInfoVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("imageMapper")
public interface ImageMapper {
    
    Integer selectImageInfoListCnt(ImageInfoVO vo) throws Exception;
    
    List<ImageInfoVO> selectImageInfoList(ImageInfoVO vo) throws Exception;
    
    ImageInfoVO selectImageInfo(ImageInfoVO vo) throws Exception;
    
    void insertImageInfo(ImageInfoVO vo) throws Exception;
    
    void updateImageInfo(ImageInfoVO vo) throws Exception;
    
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