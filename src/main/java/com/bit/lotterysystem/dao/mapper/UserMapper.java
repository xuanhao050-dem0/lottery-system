package com.bit.lotterysystem.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

@Mapper
public interface UserMapper {

    @Select("select count(*) from user where email=#{email}")
    int countByEmail(@Param("email") String email);

}
