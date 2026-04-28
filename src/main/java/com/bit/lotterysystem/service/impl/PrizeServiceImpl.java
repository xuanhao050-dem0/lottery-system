package com.bit.lotterysystem.service.impl;

import com.bit.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.bit.lotterysystem.common.exception.ServiceException;
import com.bit.lotterysystem.controller.param.PrizeUploadParam;
import com.bit.lotterysystem.controller.result.PrizeInfoResult;
import com.bit.lotterysystem.dao.dateobject.PrizeDO;
import com.bit.lotterysystem.dao.mapper.PrizeMapper;
import com.bit.lotterysystem.service.PictureService;
import com.bit.lotterysystem.service.PrizeService;
import com.bit.lotterysystem.service.dto.PrizeInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrizeServiceImpl implements PrizeService {
    @Autowired
    PrizeMapper prizeMapper;
    @Autowired
    PictureService pictureService;

    @Override
    public List<PrizeInfoDTO> getPrizeInfo() {
        //PrizeDO prizeDO=null;
        List<PrizeDO> prizeDOList=prizeMapper.getPrizeInfo();

        List<PrizeInfoDTO> prizeInfoDTOList=prizeDOList.stream()
                .map(prizeDO -> {
                    PrizeInfoDTO prizeInfoDTO=new PrizeInfoDTO();
                    prizeInfoDTO.setId(prizeDO.getId());
                    prizeInfoDTO.setImageUrl(prizeDO.getImageUrl());
                    prizeInfoDTO.setPrizeName(prizeInfoDTO.getPrizeName());
                    prizeInfoDTO.setDescription(prizeInfoDTO.getDescription());
                    prizeInfoDTO.setPrice(prizeDO.getPrice());
                    return prizeInfoDTO;
        }).collect(Collectors.toList());
        return prizeInfoDTOList;
    }

    /**
     * 新增奖品
     * 参数校验--为空，抛出异常
     * 上传照片--》获取照片地址
     * 更新数据库
     * 通过数据库返回的数据判断
     * @param param
     * @param picFile
     * @return
     */
    @Override
    public Long prizeUpload(PrizeUploadParam param, MultipartFile picFile) {

        /**
         * 参数校验
         */
        if (param==null||picFile.isEmpty()){
            throw new ServiceException(ServiceErrorCodeConstants.PRIZE_PARAM_IS_EMPTY);
        }

        /**
         * 上传照片
         */
        String picFileName = pictureService.savePicture(picFile);

        /**
         * 操作数据库
         */
        PrizeDO prizeDO=new PrizeDO();
        prizeDO.setPrizeName(param.getPrizeName());
        prizeDO.setDescription(param.getDescription());
        prizeDO.setPrice(param.getPrice());
        prizeDO.setImageUrl(picFileName);
        prizeMapper.insertPrize(prizeDO);


        return prizeDO.getId();
    }
}
