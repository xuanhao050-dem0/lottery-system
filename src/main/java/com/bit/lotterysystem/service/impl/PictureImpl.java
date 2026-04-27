package com.bit.lotterysystem.service.impl;

import com.bit.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.bit.lotterysystem.common.exception.ServiceException;
import com.bit.lotterysystem.service.PictureService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Configuration
public class PictureImpl implements PictureService {

    //指定图片储存地点
    @Value("${pic.local-path}")
    private String localPath;

    @Override
    public String savePicture(MultipartFile multipartFile) {

        //创建目录
        File dir=new File(localPath);
        if (!dir.exists()){
            dir.mkdirs();
        }

        //创建索引
        String filename=multipartFile.getOriginalFilename();
        assert filename!=null;
        String suffix=filename.substring(filename.lastIndexOf("."));
        filename= UUID.randomUUID()+suffix;

        //图片保存
        try {
            multipartFile.transferTo(new File(localPath+"/"+filename));
        } catch (IOException e) {
            throw new ServiceException(ServiceErrorCodeConstants.PICTURE_UPLOAD_FAIL);
        }

        return filename;
    }
}
