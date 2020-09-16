package com.example.demo.mapper;
/*
 * @Descripttion: 
 * @version: 
 * @Author: jlunli
 * @Date: 2020-09-14 15:06:54
 * @LastEditors: jlunli
 * @LastEditTime: 2020-09-16 16:59:09
 */


import com.example.demo.entity.Log;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
 
@Mapper
public interface LogMapper {
    @Select("SELECT * FROM log WHERE systemid = #{systemid}")
    Log Sel(@Param("systemid") String systemid);

    @Update("update log set systemid=#{logBase.systemid},userid=#{logBase.userid},operation=#{logBase.operation},deleteflag=#{logBase.deleteflag},createtime=#{logBase.createtime},lastupdatetime=#{logBase.lastupdatetime} where systemid=#{logBase.systemid}")
    int update(@Param("logBase") Log logBase);

    @SelectKey(keyProperty = "logBase.systemid", resultType = String.class, before = true,
    statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "logBase.systemid", useGeneratedKeys = true)
    @Insert("insert into log(systemid,userid,operation,deleteflag,createtime,lastupdatetime) values(#{logBase.systemid},#{logBase.userid},#{logBase.operation},#{logBase.deleteflag},#{logBase.createtime},#{logBase.lastupdatetime})")
    int insert(@Param("logBase") Log logBase);

    @Update("update log set deleteflag=1 where systemid = #{systemid}")
    int delete(@Param("systemid") String systemid);


}