/*
 * @Descripttion: 
 * @version: 
 * @Author: lijialun
 * @Date: 2020-09-14 15:09:00
 * @LastEditors: lijialun
 * @LastEditTime: 2020-09-15 16:54:29
 */
package com.example.demo.mapper;

import com.example.demo.entity.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
 
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User Sel(@Param("id") int id);

    @Update("update user set userName=#{userBase.userName},passWord=#{userBase.passWord},realName=#{userBase.realName} where systemid=#{userBase.systemid}")
    int update(@Param("userBase") User userBase);

    @SelectKey(keyProperty = "userBase.systemid", resultType = String.class, before = true,
    statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "userBase.systemid", useGeneratedKeys = true)
    @Insert("insert into user(systemid,userName,passWord,realName) values(#{userBase.systemid},#{userBase.userName}, #{userBase.passWord},#{userBase.realName})")
    int insert(@Param("userBase") User userBase);

    @Update("update user set deleteflag=1 where systemid = #{systemid}")
    int delete(@Param("systemid") String systemid);


    @Select("SELECT * FROM user WHERE userName = #{userName}")
    User getByUsername(@Param("userName") String userName);


}