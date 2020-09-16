package com.example.demo.service;
/*
 * @Descripttion: 
 * @version: 
 * @Author: jlunli
 * @Date: 2020-09-14 15:06:54
 * @LastEditors: jlunli
 * @LastEditTime: 2020-09-16 15:49:44
 */


import java.util.HashMap;

import com.example.demo.Response.RespBean;
import com.example.demo.entity.Log;
import com.example.demo.mapper.LogMapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LogService {
    @Autowired
    LogMapper logMapper;

    public Log Sel(String systemid) {
        return logMapper.Sel(systemid);
    }
    public RespBean update(Log log) {
        int answer = logMapper.update(log);
        RespBean resp;
        if(answer>0){
            resp=RespBean.ok("更新成功!", new HashMap<String,Object>(){{put("code",1);}});
        }else{
            resp=RespBean.error("更新失败�?", new HashMap<String,Object>(){{put("code",0);}});
        }
        return resp;
    }
    public RespBean insert(Log log) {
        int answer = logMapper.insert(log);
        RespBean resp;
        if(answer>0){
            resp=RespBean.ok("插入成功!", new HashMap<String,Object>(){{put("code",1);}});
        }else{
            resp=RespBean.error("插入失败�?", new HashMap<String,Object>(){{put("code",0);}});
        }
        return resp;
        
    }
    public RespBean delete(String systemid) {
        int answer = logMapper.delete(systemid);
        RespBean resp;
        if(answer>0){
            resp=RespBean.ok("删除成功!", new HashMap<String,Object>(){{put("code",1);}});
        }else{
            resp=RespBean.error("删除失败�?", new HashMap<String,Object>(){{put("code",0);}});
        }
        return resp;
    }

}