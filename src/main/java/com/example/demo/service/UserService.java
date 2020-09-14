package com.example.demo.service;

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
 * @LastEditTime: 2020-09-14 17:56:57
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