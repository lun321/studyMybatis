/*
 * @Descripttion: 
 * @version: 
 * @Author: lijialun
 * @Date: 2020-09-14 16:11:44
 * @LastEditors: lijialun
 * @LastEditTime: 2020-09-14 16:14:23
 */
package com.example.demo.controller;

import org.junit.Test;

public class UserControllerTest {
    @Test
    public void GetUserTest(){
        UserController controller = new UserController();
        controller.GetUser(1);
    }
}
