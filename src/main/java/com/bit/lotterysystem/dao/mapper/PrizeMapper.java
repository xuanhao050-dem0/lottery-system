package com.bit.lotterysystem.dao.mapper;

import com.bit.lotterysystem.controller.param.PrizeUploadParam;
import com.bit.lotterysystem.dao.dateobject.PrizeDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PrizeMapper {

    @Insert("insert into prize(prize_name,description,price,image_url)" +
            "values (#{prizeName},#{description},#{price},#{imageUrl})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    Long insertPrize(PrizeDO prizeDO);

    @Select("select * from prize")
    List<PrizeDO> getPrizeInfo();
}
