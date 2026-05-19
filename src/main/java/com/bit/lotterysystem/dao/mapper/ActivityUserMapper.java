package com.bit.lotterysystem.dao.mapper;


import com.bit.lotterysystem.dao.dateobject.ActivityUserDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;
@Mapper
public interface ActivityUserMapper {
    @Insert(" <script>" +
            " insert into activity_user(activity_id,user_id,user_name,status)" +
            " <foreach collections='items' item='item' index='index' separator=','>" +
            " values(#{item.activityId},#{item.userId},#{item.userName},#{item.status})" +
            " </foreach>" +
            " </script>")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int batchInsertActivityUser(List<ActivityUserDO> activityUserDOList);
}
