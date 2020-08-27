package com.lemon.easyApiTest.Utils;

import cn.binarywang.tools.generator.ChineseMobileNumberGenerator;

import java.util.HashMap;
import java.util.Map;


public class UserData {
    //存储接口响应变量
    public static Map<String,Object> VARS = new HashMap<>();
    public static Map<String,String> DEFAUT_HEADERS = new HashMap<>();

    //静态代码块：类加载时会自动执行一次；
    //静态方法需要手动调用，静态代码类加载自动执行一次。
    static {
        DEFAUT_HEADERS.put("X-Lemonban-Media-Type","lemonban.v2");
        DEFAUT_HEADERS.put("Content-Type","application/json");
        //把需要参数化的数据存储到VARS
        //随机手机号码
        VARS.put("${register_mb}", ChineseMobileNumberGenerator.getInstance().generate());
        VARS.put("${register_pwd}","12345678");
        VARS.put("${amount}","5000");
    }
}
