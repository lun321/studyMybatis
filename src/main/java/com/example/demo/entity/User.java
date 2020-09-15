/*
 * @Descripttion: 
 * @version: 
 * @Author: lijialun
 * @Date: 2020-09-14 15:06:54
 * @LastEditors: lijialun
 * @LastEditTime: 2020-09-15 16:54:04
 */
package com.example.demo.entity;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 14:39
 */
public class User implements Serializable, UserDetails {
    private String systemid;
    private String userName;
    private String passWord;
    private String realName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "User{" + "systemid=" + systemid + ", userName='" + userName + '\'' + ", passWord='" + passWord + '\''
                + ", realName='" + realName + '\'' + '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return passWord;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return userName;
    }

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }


}