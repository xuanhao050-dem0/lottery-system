package com.bit.lotterysystem.dao.mapper;

import com.bit.lotterysystem.dao.dateobject.Encrypt;
import com.bit.lotterysystem.dao.dateobject.UserDO;
import jakarta.validation.constraints.NotBlank;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

@Mapper
/**
 * 传入参数 @Param
 * 语句占位参数 #{}
 * sql语句
 * 增 insert into table (列名1 2 3 ... n) values (参数)
 * 查 select （需求） from table where （条件）
 * 删
 * 改
 */
public interface UserMapper {

    @Select("select count(*) from user where email=#{email}")
    int countByEmail(@Param("email") String email);

    @Select("select count(*) from user where phone_number=#{phoneNumber}")
    int countByPhoneNumber(@Param("phoneNumber") Encrypt phoneNumber);

    @Insert("insert into user (user_name,email,phone_number,password,identity)"+
            " values (#{userName},#{email},#{phoneNumber},#{password},#{identity})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void insertUserInfo(UserDO userDO);

    @Select("select * from user where email=#{userName}")
    UserDO selectByEmail(@Param("userName") String userName);

    @Select("select * from user where phone_number=#{userName}")
    UserDO selectByMobile(@Param("userName") Encrypt userName);
}
