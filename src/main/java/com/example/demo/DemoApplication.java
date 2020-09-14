/*
 * @Descripttion: 
 * @version: 
 * @Author: lijialun
 * @Date: 2020-09-08 15:25:54
 * @LastEditors: lijialun
 * @LastEditTime: 2020-09-14 15:12:19
 */
package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.mapper") //扫描的mapper
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
