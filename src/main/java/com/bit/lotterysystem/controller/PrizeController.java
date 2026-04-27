package com.bit.lotterysystem.controller;

import com.bit.lotterysystem.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PrizeController {

    @Autowired
    PictureService pictureService;

    @RequestMapping("/picture/upload")
    public String upload(MultipartFile file){
        return pictureService.savePicture(file);
    }
}
