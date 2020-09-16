package com.example.demo.controller;
/*
 * @Descripttion: 
 * @version: 
 * @Author: jlunli
 * @Date: 2020-09-14 15:06:54
 * @LastEditors: jlunli
 * @LastEditTime: 2020-09-16 16:06:59
 */

import com.example.demo.Response.RespBean;
import com.example.demo.entity.Log;
import com.example.demo.service.LogService;
import com.example.demo.util.buildcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
 
@RestController
@RequestMapping("/api")
public class LogController {
 
    @Autowired
    private LogService logService;

    
    @RequestMapping(value="Log/{systemid}",method = RequestMethod.GET)
    public Log GetLog(@PathVariable String systemid){
        return logService.Sel(systemid);
    }
    @RequestMapping(value="Log",method = RequestMethod.PUT)
    public RespBean updateLog(@RequestBody Log log){
        return logService.update(log);
    }
    @RequestMapping(value="Log",method = RequestMethod.POST)
    public RespBean insertLog(@RequestBody Log log){
        return logService.insert(log);
    }
    @RequestMapping(value="Log/{systemid}",method = RequestMethod.DELETE)
    public RespBean deleteLog(@PathVariable String systemid){
        return logService.delete(systemid);
    }

}