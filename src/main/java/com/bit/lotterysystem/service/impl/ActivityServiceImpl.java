package com.bit.lotterysystem.service.impl;

import com.bit.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.bit.lotterysystem.common.exception.ServiceException;
import com.bit.lotterysystem.controller.param.CreateActivityParam;
import com.bit.lotterysystem.controller.param.CreateUserByActivityParam;
import com.bit.lotterysystem.dao.mapper.ActivityMapper;
import com.bit.lotterysystem.service.ActivityService;
import com.bit.lotterysystem.service.dto.CreateActivityDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    ActivityMapper activityMapper;
    @Override
    public CreateActivityDTO createActivity(CreateActivityParam param) {
        /**
         * 校验参数
         */
        checkActivityParam(param);
        /**
         *
         */
        return null;
    }

    private void checkActivityParam(CreateActivityParam param) {
        /**
         * 校验参数是否为空
         */
        if (param==null){
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PARAM_EMPTY);
        }
        /**
         * 校验人员id是否存在于人员表
         * 在参数中取出传入id-->UserId
         * 根据UserId去数据库找
         */
        List<Long> userIds=param.getCreateUserByActivityList()
                .stream()
                .map(CreateUserByActivityParam::getUserId)
                .distinct()//去重
                .collect(Collectors.toList());
        List<Long> existId =activityMapper.selectExistId(userIds);
    }
}
