package com.bit.lotterysystem.dao.mapper;

import com.bit.lotterysystem.dao.dateobject.ActivityDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivityMapper {

    @Select("insert into activity(activity_name,description,status)" +
            "values (#{activityName},#{description},#{status})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int insertActivity(ActivityDO activityDO);

}
