package com.bit.lotterysystem.service.impl;
import java.util.Date;

import com.bit.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.bit.lotterysystem.common.exception.ServiceException;
import com.bit.lotterysystem.controller.param.CreateActivityParam;
import com.bit.lotterysystem.controller.param.CreatePrizeByActivityParam;
import com.bit.lotterysystem.controller.param.CreateUserByActivityParam;
import com.bit.lotterysystem.dao.dateobject.ActivityDO;
import com.bit.lotterysystem.dao.mapper.ActivityMapper;
import com.bit.lotterysystem.dao.mapper.PrizeMapper;
import com.bit.lotterysystem.dao.mapper.UserMapper;
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

    @Autowired
    UserMapper userMapper;

    @Autowired
    PrizeMapper prizeMapper;
    @Override
    public CreateActivityDTO createActivity(CreateActivityParam param) {
        /**
         * 校验参数
         */
        checkActivityParam(param);
        /**
         *保存活动信息
         */
        ActivityDO activityDO=new ActivityDO();
        activityDO.setActivityName(param.getName());
        activityDO.setDescription(param.getDescription());
        activityDO.setStatus();



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
         * 校验（传入参数）奖品id是否存在于（数据库）奖品表
         *
         * 在**参数**中取出传入id-->PrizeId
         * 根据PrizeId去**数据库**找id
         * 参数中传入非法的id，在数据库中找不到id，返回空
         * 如：数据库1，2 参数1，2，3
         * 如果数据库中不包含参数的id，则传入非法id，创建活动失败
         */
        List<Long> PrizeIds=param.getCreatePrizeByActivityList()
                .stream()
                .map(CreatePrizeByActivityParam::getPrizeId)
                .distinct()//去重
                .collect(Collectors.toList());

        List<Long> existPrizeId =prizeMapper.selectExistId(PrizeIds);

        PrizeIds.forEach(id->{
            if (!existPrizeId.contains(id)){
                throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_ERROR);
            }
        });
        /**
         * 校验（传入参数）人员id是否存在于（数据库）人员表
         *
         * 在**参数**中取出传入id-->UserId
         * 根据UserId去**数据库**找id
         * 参数中传入非法的id，在数据库中找不到id，返回空
         * 如：数据库1，2 参数1，2，3
         * 如果数据库中不包含参数的id，则传入非法id，创建活动失败
         */
        List<Long> userIds=param.getCreateUserByActivityList()
                .stream()
                .map(CreateUserByActivityParam::getUserId)
                .distinct()//去重
                .collect(Collectors.toList());

        List<Long> existUserId =userMapper.selectExistId(userIds);

        userIds.forEach(id->{
            if (!existUserId.contains(id)){
                throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_USER_ERROR);
            }
        });

        /**
         * 人员数量大于奖品数量创建活动失败
         * 获取人员数量
         * 获取奖品数量
         */
        int userCount = param.getCreateUserByActivityList().size();
        long prizeCount=param.getCreatePrizeByActivityList()
                .stream()
                .mapToLong(CreatePrizeByActivityParam::getPrizeCount)
                .sum();
        if (userCount>prizeCount){
            throw new ServiceException(ServiceErrorCodeConstants.PRIZE_USER_AMOUNT_ERROR);
        }

    }
}
