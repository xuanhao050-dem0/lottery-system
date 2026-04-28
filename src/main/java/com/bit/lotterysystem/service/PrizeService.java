package com.bit.lotterysystem.service;

import com.bit.lotterysystem.controller.param.PrizeUploadParam;
import com.bit.lotterysystem.controller.result.PrizeInfoResult;
import com.bit.lotterysystem.service.dto.PrizeInfoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PrizeService {
    /**
     * 创建奖品
     * @param param
     * @param picFile
     * @return
     */
    Long prizeUpload(PrizeUploadParam param, MultipartFile picFile);

    List<PrizeInfoDTO> getPrizeInfo();
}
