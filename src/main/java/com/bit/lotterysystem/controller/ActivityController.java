package com.bit.lotterysystem.controller;

import com.bit.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.bit.lotterysystem.common.exception.ControllerException;
import com.bit.lotterysystem.common.pojo.Result;
import com.bit.lotterysystem.common.utils.JacksonUtil;
import com.bit.lotterysystem.controller.param.CreateActivityParam;
import com.bit.lotterysystem.controller.result.CreateActivityResult;

import com.bit.lotterysystem.service.ActivityService;
import com.bit.lotterysystem.service.dto.CreateActivityDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;


    @RequestMapping("/createActivity")
    public Result<CreateActivityResult> createActivity(
            @Validated @RequestBody CreateActivityParam param){
        /**
         * 日志打印
         */
        log.info("createActivity CreateActivityParam:{}",
                JacksonUtil.writeValueAsString(param));

        /**
         * 调用service层
         * 构造返回
         */
        return Result.success(convertToResult(activityService.createActivity(param)));




    }

    private  CreateActivityResult convertToResult(CreateActivityDTO activityDTO) {
        if (activityDTO==null){
            throw new ControllerException(ControllerErrorCodeConstants.CREATE_ACTIVITY_ERROR);
        }
        CreateActivityResult createActivityResult=new CreateActivityResult();
        createActivityResult.setActivityId(activityDTO.getActivityId());
        return createActivityResult;
    }
}
