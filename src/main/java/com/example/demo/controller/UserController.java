/*
 * @Descripttion: 
 * @version: 
 * @Author: lijialun
 * @Date: 2020-09-14 19:56:57
 * @LastEditors: lijialun
 * @LastEditTime: 2020-09-15 18:07:17
 */
package com.example.demo.controller;

import com.example.demo.Response.RespBean;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.buildcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */
 
@RestController
@RequestMapping("/api")
public class UserController {
 
    @Autowired
    private UserService userService;

    @Autowired
    private buildcode buildcode;
    @RequestMapping(value="User/{id}",method = RequestMethod.GET)
    public String GetUser(@PathVariable int id){
        return userService.Sel(id).toString();
    }
    @RequestMapping(value="User",method = RequestMethod.PUT)
    public RespBean updateUser(@RequestBody User user){
        return userService.update(user);
    }
    @RequestMapping(value="User",method = RequestMethod.POST)
    public RespBean insertUser(@RequestBody User user){
        return userService.insert(user);
    }
    @RequestMapping(value="User/{systemid}",method = RequestMethod.DELETE)
    public RespBean deleteUser(@PathVariable String systemid){
        return userService.delete(systemid);
    }

    @RequestMapping("buildcode")
    public void buildcode(){
        buildcode.buildcode();
    }
}