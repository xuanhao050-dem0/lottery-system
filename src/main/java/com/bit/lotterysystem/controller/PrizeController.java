package com.bit.lotterysystem.controller;

import com.bit.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.bit.lotterysystem.common.exception.ControllerException;
import com.bit.lotterysystem.common.pojo.Result;
import com.bit.lotterysystem.common.utils.JWTUtil;
import com.bit.lotterysystem.common.utils.JacksonUtil;
import com.bit.lotterysystem.controller.param.PageParam;
import com.bit.lotterysystem.controller.param.PrizeUploadParam;
import com.bit.lotterysystem.controller.result.GetPrizeInfoResult;
import com.bit.lotterysystem.controller.result.PrizeInfoResult;
import com.bit.lotterysystem.service.PictureService;
import com.bit.lotterysystem.service.PrizeService;
import com.bit.lotterysystem.service.dto.PageListDTO;
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

    /**
     * 分页查询奖品列表
     * @param param 页数 每页查询结果总量
     * @return
     */
    @RequestMapping("/getPrizeList")
    public Result<GetPrizeInfoResult> getPrizeInfo(PageParam param){

        log.info("getPrizeInfo PageParam:{}",
                JacksonUtil.writeValueAsString(param));
        PageListDTO<PrizeInfoDTO> prizeListInfo=prizeService.getPrizeInfo(param);
        return Result.success(convertToResult(prizeListInfo));
    }

    /**
     * 查询奖品信息类型转换
     * 将DTO转换为result。
     * @param prizeInfoDTO
     * @return
     */
    private GetPrizeInfoResult convertToResult(PageListDTO<PrizeInfoDTO> prizeInfoDTO) {
        //判断获取奖品信息是否存在
        if (prizeInfoDTO==null){
            throw new ControllerException(ControllerErrorCodeConstants.PRIZEINFO_ERROR);
        }

        //转换
        GetPrizeInfoResult result=new GetPrizeInfoResult();
        result.setTotal(prizeInfoDTO.getTotal());
        result.setRecords(
                prizeInfoDTO.getRecords().stream()
                        .map(prizeDTO->{
                            GetPrizeInfoResult.PrizeInfo prizeInfo=new GetPrizeInfoResult.PrizeInfo();
                            prizeInfo.setId(prizeDTO.getId());
                            prizeInfo.setImageUrl(prizeDTO.getImageUrl());
                            prizeInfo.setPrizeName(prizeDTO.getPrizeName());
                            prizeInfo.setDescription(prizeDTO.getDescription());
                            prizeInfo.setPrice(prizeDTO.getPrice());
                            return prizeInfo;
                        }).collect(Collectors.toList())

        );

//        List<PrizeInfoResult> prizeInfoResultList=prizeInfo.stream()
//                .map(prizeInfoDTO -> {
//                    PrizeInfoResult prizeInfoResult=new PrizeInfoResult();
//
//                    prizeInfoResult.setId(prizeInfoDTO.getId());
//                    prizeInfoResult.setImageUrl(prizeInfoDTO.getImageUrl());
//                    prizeInfoResult.setPrizeName(prizeInfoDTO.getPrizeName());
//                    prizeInfoResult.setDescription(prizeInfoDTO.getDescription());
//                    prizeInfoResult.setPrice(prizeInfoDTO.getPrice());
//                    return prizeInfoResult;
//                }).collect(Collectors.toList());
        
    return result;
    }
}
