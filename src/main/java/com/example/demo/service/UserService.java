package com.example.demo.service;

import java.util.HashMap;

import com.example.demo.Response.RespBean;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
/*
 * @Descripttion: 
 * @version: 
 * @Author: lijialun
 * @Date: 2020-09-14 15:08:15
 * @LastEditors: lijialun
 * @LastEditTime: 2020-09-15 16:46:30
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    public User Sel(int id) {
        return userMapper.Sel(id);
    }
    public RespBean update(User user) {
        int answer = userMapper.update(user);
        RespBean resp;
        if(answer>0){
            resp=RespBean.ok("更新成功!", new HashMap<String,Object>(){{put("code",1);}});
        }else{
            resp=RespBean.error("更新失败！", new HashMap<String,Object>(){{put("code",0);}});
        }
        return resp;
    }
    public RespBean insert(User user) {
        int answer = userMapper.insert(user);
        RespBean resp;
        if(answer>0){
            resp=RespBean.ok("插入成功!", new HashMap<String,Object>(){{put("code",1);}});
        }else{
            resp=RespBean.error("插入失败！", new HashMap<String,Object>(){{put("code",0);}});
        }
        return resp;
        
    }
    public RespBean delete(String systemid) {
        int answer = userMapper.delete(systemid);
        RespBean resp;
        if(answer>0){
            resp=RespBean.ok("删除成功!", new HashMap<String,Object>(){{put("code",1);}});
        }else{
            resp=RespBean.error("删除失败！", new HashMap<String,Object>(){{put("code",0);}});
        }
        return resp;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User user = userMapper.getByUsername(userName);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }

}