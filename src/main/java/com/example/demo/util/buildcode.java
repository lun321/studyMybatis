/*
 * @Descripttion: 
 * @version: 
 * @Author: jlunli
 * @Date: 2020-09-15 09:34:09
 * @LastEditors: jlunli
 * @LastEditTime: 2020-09-16 17:06:15
 */
package com.example.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class buildcode {
    private String url = "/codeOutPut";
    private String packageurl="com.example.demo";
    @Value("${databaseName}")
    private String databaseName;
    @Autowired
    JdbcTemplate jdbcTemplate;
    public void buildcode(){
        // 获取所有表名
        List<Map<String, Object>> tableNameList = jdbcTemplate.queryForList(
                "select table_name from information_schema.tables where table_schema='mybatis'");
        Iterator<Map<String,Object>> it = tableNameList.iterator();
        while(it.hasNext()){
            String name = it.next().get("TABLE_NAME").toString();
            buildController(packageurl, name);
            buildService(packageurl, name);
            buildEntity(packageurl, name);
            buildMapper(packageurl, name);
        }
        
    }
    public void buildMapper(String packageurl, String name){
        File f = new File(this.getClass().getResource("/").getPath()+url+"/mapper/"+Utils.upperCase(name)+"Mapper.java");
        if(!f.exists()){
            f.getParentFile().mkdirs();
        }
        try {
            List<Map<String,Object>> list = jdbcTemplate.queryForList("select COLUMN_NAME from information_schema.COLUMNS where table_name = '"+name+"' and table_schema = '"+databaseName+"'"); // 查询某数据库下的某表格的所有列名
            Iterator<Map<String,Object>> it = list.iterator();
            List<String> columnName=new ArrayList<String>();
            while(it.hasNext()){
                columnName.add(it.next().get("COLUMN_NAME").toString());
            }
            StringBuffer updateColumns = new StringBuffer();
            StringBuffer insertColumns = new StringBuffer();
            StringBuffer insertValues = new StringBuffer();
            for(int i=0;i<columnName.size();i++){
                updateColumns.append(columnName.get(i)+"=#{"+name+"Base."+columnName.get(i)+"}");
                insertColumns.append(columnName.get(i));
                insertValues.append("#{"+name+"Base."+columnName.get(i)+"}");
                if(i<columnName.size()-1){
                    updateColumns.append(",");
                    insertColumns.append(",");
                    insertValues.append(",");
                }
            }
            File template = ResourceUtils.getFile("classpath:codeTemplate/mapperTemplate.txt");
            FileReader in = new FileReader(template);
            char[] read = new char[8024];
            in.read(read);
            String dealString =new String(read);
            dealString=dealString.replaceAll("<className>", Utils.upperCase(name));
            dealString=dealString.replaceAll("<varName>", name);
            dealString=dealString.replaceAll("<updateColumns>", updateColumns.toString());
            dealString=dealString.replaceAll("<insertColumns>", insertColumns.toString());
            dealString=dealString.replaceAll("<insertValues>", insertValues.toString());
            dealString=dealString.trim();
            StringBuffer readBuffer = new StringBuffer("package "+packageurl+".mapper;\n");
            readBuffer.append(dealString);
            FileWriter out = new FileWriter(f);
            out.write(readBuffer.toString());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void buildEntity(String packageurl, String name){
        File f = new File(this.getClass().getResource("/").getPath()+url+"/entity/"+Utils.upperCase(name)+".java");
        if(!f.exists()){
            f.getParentFile().mkdirs();
        }
        try {
            List<Map<String,Object>> list = jdbcTemplate.queryForList("select COLUMN_NAME from information_schema.COLUMNS where table_name = '"+name+"' and table_schema = '"+databaseName+"'"); // 查询某数据库下的某表格的所有列名
            Iterator<Map<String,Object>> it = list.iterator();
            List<String> columnName=new ArrayList<String>();
            while(it.hasNext()){
                columnName.add(it.next().get("COLUMN_NAME").toString());
            }
            StringBuffer fields = new StringBuffer();
            StringBuffer methods = new StringBuffer();
            for(int i=0;i<columnName.size();i++){
                if(columnName.get(i).toString().equals("systemid")||columnName.get(i).toString().equals("deleteflag")){  // 忽略systemid主键项,deleteflag
                    continue;
                }
                fields.append("\tString "+columnName.get(i)+";\n");
                methods.append("\tpublic void set"+Utils.upperCase(columnName.get(i))+"(String "+columnName.get(i)+"){\n\t\tthis."+columnName.get(i)+"="+columnName.get(i)+"; \n\t}\n\n");
                methods.append("\tpublic String get"+Utils.upperCase(columnName.get(i))+"(){\n\t\treturn "+columnName.get(i)+"; \n\t}\n\n");
            }
            File template = ResourceUtils.getFile("classpath:codeTemplate/entityTemplate.txt");
            FileReader in = new FileReader(template);
            char[] read = new char[8024];
            in.read(read);
            String dealString =new String(read);
            dealString=dealString.replaceAll("<className>", Utils.upperCase(name));
            dealString=dealString.replaceAll("<varName>", name);
            dealString=dealString.replaceAll("<modal_fields>", fields.toString());
            dealString=dealString.replaceAll("<modal_methods>", methods.toString());
            dealString=dealString.trim();
            StringBuffer readBuffer = new StringBuffer("package "+packageurl+".entity;\n");
            readBuffer.append(dealString);
            FileWriter out = new FileWriter(f);
            out.write(readBuffer.toString());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * @param packageurl 项目包名 
     * @param name 类名
     * @return void
     * @description:根据包名和类名生成集成mybatis的controller的代码
     * 
     */
    public void buildController(String packageurl, String name) {
        File f = new File(this.getClass().getResource("/").getPath()+url+"/controller/"+Utils.upperCase(name)+"Controller.java");
        if(!f.exists()){
            f.getParentFile().mkdirs();
        }

        try {
            File template = ResourceUtils.getFile("classpath:codeTemplate/controllerTemplate.txt");
            FileReader in = new FileReader(template);
            char[] read = new char[8024];
            in.read(read);
            String dealString =new String(read);
            dealString=dealString.replaceAll("<className>", Utils.upperCase(name));
            dealString=dealString.replaceAll("<varName>", name);
            dealString=dealString.trim();
            StringBuffer readBuffer = new StringBuffer("package "+packageurl+".controller;\n");
            readBuffer.append(dealString);
            FileWriter out = new FileWriter(f);
            out.write(readBuffer.toString());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void buildService(String packageurl, String name){
        File f = new File(this.getClass().getResource("/").getPath()+url+"/service/"+Utils.upperCase(name)+"Service.java");
        if(!f.exists()){
            f.getParentFile().mkdirs();
        }

        try {
            File template = ResourceUtils.getFile("classpath:codeTemplate/serviceTemplate.txt");
            FileReader in = new FileReader(template);
            char[] read = new char[8024];
            in.read(read);
            String dealString =new String(read);
            dealString=dealString.replaceAll("<className>", Utils.upperCase(name));
            dealString=dealString.replaceAll("<varName>", name);
            dealString=dealString.trim();
            StringBuffer readBuffer = new StringBuffer("package "+packageurl+".service;\n");
            readBuffer.append(dealString);
            FileWriter out = new FileWriter(f);
            out.write(readBuffer.toString());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
