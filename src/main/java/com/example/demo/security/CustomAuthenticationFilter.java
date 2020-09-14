package com.example.demo.security;

/*
 * @Descripttion: 
 * @version: 
 * @Author: lijialun
 * @Date: 2020-08-05 16:15:23
 * @LastEditors: lijialun
 * @LastEditTime: 2020-09-14 16:44:02
 */



import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//重写拦截器，使其可处理json数据
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    // @Resource
    // private SessionRegistry sessionRegistry;
        @Override	
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {	
            System.out.println(request.getContentType());
            
            if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)	
                    || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {	
                ObjectMapper mapper = new ObjectMapper();	
                UsernamePasswordAuthenticationToken authRequest = null;	
                try (InputStream is = request.getInputStream()) {	
                    Map<String,String> authenticationBean = mapper.readValue(is, Map.class);	
                    authRequest = new UsernamePasswordAuthenticationToken(	
                            authenticationBean.get("username"), authenticationBean.get("password"));	
                            // sessionRegistry.registerNewSession(request.getSession().getId(),authRequest.getPrincipal());
                } catch (IOException e) {	
                    e.printStackTrace();	 
                    authRequest = new UsernamePasswordAuthenticationToken(	
                            "", "");	
                } finally {	
                    setDetails(request, authRequest);	
                    return this.getAuthenticationManager().authenticate(authRequest);	
                }	
            }	
            else {	
                return super.attemptAuthentication(request, response);	
            }	
        }	
    }