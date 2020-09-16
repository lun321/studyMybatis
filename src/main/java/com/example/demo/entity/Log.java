package com.example.demo.entity;
/*
 * @Descripttion: 
 * @version: 
 * @Author: jlunli
 * @Date: 2020-09-14 15:06:54
 * @LastEditors: jlunli
 * @LastEditTime: 2020-09-16 17:09:21
 */


import java.io.Serializable;
import java.util.Collection;



public class Log implements Serializable {
    private String systemid;
    private int deleteflag=0;
	String userid;
	String operation;
	String createtime;
	String lastupdatetime;


    

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public int getDeleteflag(){
        return deleteflag;
    }

    public void setDeleteflag(int deleteflag){
        this.deleteflag=deleteflag;
    }
	public void setUserid(String userid){
		this.userid=userid; 
	}

	public String getUserid(){
		return userid; 
	}

	public void setOperation(String operation){
		this.operation=operation; 
	}

	public String getOperation(){
		return operation; 
	}

	public void setCreatetime(String createtime){
		this.createtime=createtime; 
	}

	public String getCreatetime(){
		return createtime; 
	}

	public void setLastupdatetime(String lastupdatetime){
		this.lastupdatetime=lastupdatetime; 
	}

	public String getLastupdatetime(){
		return lastupdatetime; 
	}



}