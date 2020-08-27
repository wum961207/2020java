package com.lemon.testngtest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/*
1、使用 @dataProvider 对 register(String username,String password,String type,String sex)方法 进行5次测试
 */
public class TestNgArrayTest {
    @Test(dataProvider = "datas")
    public void register(String username,String password,String type,String sex){
        System.out.println("username = [" + username + "], password = [" + password + "], type = [" + type + "], sex = [" + sex + "]");
    }

    @DataProvider(name = "datas")
    public Object[][] datasMethod(){
        Object[][] datas = new Object[5][4];
        for(int i=0;i<datas.length;i++){
            datas[i][0] = "张三" + (i+1);
            datas[i][1] = "123456";
            if(i%2 == 0){
                datas[i][2] = "get";
                datas[i][3] = "男";
            }else{
                datas[i][2] = "post";
                datas[i][3] = "女";
            }
        }
        return datas;
    }
}
