package com.lemon.json;

import com.alibaba.fastjson.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
/*
1、String json = {"name": "张三", "age": "18", "score":"100"}; 解析成Student对象。
2、String json = 	[{"name": "张三", "age": "18", "score":"100"},{"name": "李四", "age": "16", "score":"100"}] 解析成List<Student>对象。
3、有config.properties文件内容如下
username=zhangsan
password=123456
使用Properties类的中方法读取到java程序中并输出在控制台上。
4、把下面json字符串解析成java对象（扩展题目，可以不做）
{ "name": "中国", "provinces": [{ "name": "黑龙江", "capital": "哈尔滨" }, { "name": "广东", "capital": "广州" }, { "name": "湖南", "capital": "长沙" }] }
 */
public class JsonTest {

    public static void main(String[] args) throws Exception{
        //作业1:
        String jsonNew = "{\"name\": \"张三\", \"age\": \"18\", \"score\":\"100\"}";
        Student stu = JSONObject.parseObject(jsonNew, Student.class);
        System.out.println(stu.toString());
        //作业2
        String jsonNew2 = "[{\"name\": \"张三\", \"age\": \"18\", \"score\":\"100\"},{\"name\": \"李四\", \"age\": \"16\", \"score\":\"100\"}]";
        List<Student> list = JSONObject.parseArray(jsonNew2, Student.class);
        System.out.println(list);
        //作业3
        /**方法一：使用getProperty*/
        Properties pro = new Properties();
        pro.setProperty("username","zhangsan");
        pro.setProperty("password","123456");
        String username = pro.getProperty("username");
        String password = pro.getProperty("password");
        System.out.println("username:" + username + ",password:" + password);
        /**方法一：使用io流*/
        FileInputStream fis = new FileInputStream("src\\test\\resources\\config.properties");
        pro.load(fis);
        System.out.println(pro);
        fis.close();
        //作业4：思路，生成country对象，属性有国家名，省名，城市名
        String json4 = "{ \"name\": \"中国\", \"provinces\": [{ \"name\": \"黑龙江\", \"capital\": \"哈尔滨\" }, { \"name\": \"广东\", \"capital\": \"广州\" }, { \"name\": \"湖南\", \"capital\": \"长沙\" }] }";
        Map map = JSONObject.parseObject(json4,Map.class);
        System.out.println(map);
        String country = map.get("name").toString(); //获取国家名
        String provinces = map.get("provinces").toString();
        //不知道如何将身份拆出来

    }
}
