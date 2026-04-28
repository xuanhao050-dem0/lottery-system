package com.bit.lotterysystem.controller;

import com.bit.lotterysystem.common.pojo.Result;
import com.bit.lotterysystem.common.utils.JWTUtil;
import com.bit.lotterysystem.common.utils.JacksonUtil;
import com.bit.lotterysystem.controller.param.PrizeUploadParam;
import com.bit.lotterysystem.controller.result.PrizeInfoResult;
import com.bit.lotterysystem.service.PictureService;
import com.bit.lotterysystem.service.PrizeService;
import com.bit.lotterysystem.service.dto.PrizeInfoDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/prize")
public class PrizeController {

    @Autowired
    PictureService pictureService;

    @Autowired
    PrizeService prizeService;

    @RequestMapping("/picture/upload")
    public String upload(MultipartFile file){
        return pictureService.savePicture(file);
    }

    /**
     * 奖品上传
     * @RequestPart 接受表单--》带有格式的文件：图片，视频，office
     * @param param
     * @param picFile
     * @return
     */
    @RequestMapping("/upload")
    public Result<Long> uploadPrize(
            @Valid @RequestPart("param") PrizeUploadParam param,
            @RequestPart("picFile") MultipartFile picFile){
        log.info("PrizeUploadParam:{},", JacksonUtil.writeValueAsString(param));



        return Result.success(prizeService.prizeUpload(param,picFile));
    }

    @RequestMapping("/getPrizeList")
    public Result<List<PrizeInfoResult>> getPrizeInfo(){

        List<PrizeInfoDTO> prizeInfo=prizeService.getPrizeInfo();
        return Result.success(convertToResult(prizeInfo));
    }

    private List<PrizeInfoResult> convertToResult(List<PrizeInfoDTO> prizeInfo) {
        List<PrizeInfoResult> prizeInfoResultList=prizeInfo.stream()
                .map(prizeInfoDTO -> {
                    PrizeInfoResult prizeInfoResult=new PrizeInfoResult();

                    prizeInfoResult.setId(prizeInfoDTO.getId());
                    prizeInfoResult.setImageUrl(prizeInfoDTO.getImageUrl());
                    prizeInfoResult.setPrizeName(prizeInfoDTO.getPrizeName());
                    prizeInfoResult.setDescription(prizeInfoDTO.getDescription());
                    prizeInfoResult.setPrice(prizeInfoDTO.getPrice());
                    return prizeInfoResult;
                }).collect(Collectors.toList());
        
    return prizeInfoResultList;
    }
}
