package com.bit.lotterysystem.service.impl;
import java.util.Date;

import com.bit.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.bit.lotterysystem.common.exception.ServiceException;
import com.bit.lotterysystem.controller.param.CreateActivityParam;
import com.bit.lotterysystem.controller.param.CreatePrizeByActivityParam;
import com.bit.lotterysystem.controller.param.CreateUserByActivityParam;
import com.bit.lotterysystem.dao.dateobject.ActivityDO;
import com.bit.lotterysystem.dao.dateobject.ActivityPrizeDO;
import com.bit.lotterysystem.dao.dateobject.ActivityUserDO;
import com.bit.lotterysystem.dao.mapper.*;
import com.bit.lotterysystem.service.ActivityService;
import com.bit.lotterysystem.service.dto.CreateActivityDTO;
import com.bit.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.bit.lotterysystem.service.enums.ActivityStatusEnum;
import com.bit.lotterysystem.service.enums.ActivityUserStatusEnum;
import com.bit.lotterysystem.service.enums.PrizeTiersEnum;
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

    @Autowired
    ActivityPrizeMapper activityPrizeMapper;

    @Autowired
    ActivityUserMapper activityUserMapper;
    @Override
    public CreateActivityDTO createActivity(CreateActivityParam param) {
        /**
         * 校验参数
         */
        checkActivityParam(param);
        /**
         *保存活动信息表
         * 构建DO传入mapper
         * 写入数据库
         * 返回DO
         */
        ActivityDO activityDO=new ActivityDO();
        activityDO.setActivityName(param.getName());
        activityDO.setDescription(param.getDescription());
        activityDO.setStatus(ActivityStatusEnum.RUNNING.name());
        activityMapper.insertActivity(activityDO);
        /**
         * 活动人员表
         * 将参数转换为列表
         */
        List<CreateUserByActivityParam> userParams=param.getCreateUserByActivityList();
        List<ActivityUserDO> activityUserDOList=userParams
                .stream()
                .map(createUserByActivityParam -> {
                    ActivityUserDO activityUserDO=new ActivityUserDO();
                    activityUserDO.setActivityId(activityDO.getId());
                    activityUserDO.setUserId(createUserByActivityParam.getUserId());
                    activityUserDO.setUserName(createUserByActivityParam.getUserName());
                    activityUserDO.setStatus(ActivityUserStatusEnum.INIT.name());
                    return activityUserDO;

                }).collect(Collectors.toList());

        activityUserMapper.batchInsertActivityUser(activityUserDOList);
        /**
         * 活动奖品表
         */
        List<CreatePrizeByActivityParam> prizeParams=param.getCreatePrizeByActivityList();
        List<ActivityPrizeDO> activityPrizeDOList=prizeParams
                .stream()
                .map(createPrizeByActivityParam -> {
                    ActivityPrizeDO activityPrizeDO=new ActivityPrizeDO();
                    activityPrizeDO.setActivityId(activityDO.getId());
                    activityPrizeDO.setPrizeId(createPrizeByActivityParam.getPrizeId());
                    activityPrizeDO.setPrizeCount(createPrizeByActivityParam.getPrizeCount());
                    activityPrizeDO.setPrizeTiers(createPrizeByActivityParam.getPrizeLevel());
                    activityPrizeDO.setStatus(ActivityPrizeStatusEnum.INIT.name());
                    return activityPrizeDO;

                }).collect(Collectors.toList());
        activityPrizeMapper.batchInsertActivityPrize(activityPrizeDOList);


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
        /**
         * 校验奖品等级是否合法
         */
        List<String> levelList=param.getCreatePrizeByActivityList().stream()
                .map(CreatePrizeByActivityParam::getPrizeLevel)
                .collect(Collectors.toList());
        for (String level:levelList){
            if (null== PrizeTiersEnum.forName(level)){
                throw new ServiceException(ServiceErrorCodeConstants.PRIZE_LEVEL_ERROR);
            }
        }


    }
}
