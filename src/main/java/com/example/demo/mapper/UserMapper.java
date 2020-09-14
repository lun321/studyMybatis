/*
 * @Descripttion: 
 * @version: 
 * @Author: lijialun
 * @Date: 2020-09-14 15:09:00
 * @LastEditors: lijialun
 * @LastEditTime: 2020-09-14 16:56:52
 */
package com.example.demo.mapper;

import com.example.demo.entity.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
 
/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 15:20
 */
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User Sel(@Param("id") int id);
    @Select("SELECT * FROM user WHERE userName = #{userName}")
    User getByUsername(@Param("userName") String userName);

    @Insert("INSERT INTO user(id,userName, passWord) VALUES(#{id},#{userName}, #{passWord})")
    int insert(@Param("id") int id,@Param("userName") String userName,@Param("passWord") String passWord);
}