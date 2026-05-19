package com.bit.lotterysystem.dao.mapper;

import com.bit.lotterysystem.dao.dateobject.ActivityPrizeDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;
@Mapper
public interface ActivityPrizeMapper {
    @Insert(" <script>" +
            " insert into activity_prize(activity_id,prize_id,prize_amount,prize_tiers,status)" +
            " <foreach collections='items' item='item' index='index' separator=','>" +
            " values(#{item.activityId},#{item.prizeId},#{item.prizeAmount},#{item.prizeTiers},#{item.status})" +
            " </foreach>" +
            " </script>")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int batchInsertActivityPrize(List<ActivityPrizeDO> activityPrizeDOList);
}
