package com.lemon.easyApiTest.cases;

import com.alibaba.fastjson.JSONObject;
import com.lemon.easyApiTest.Utils.ExcelUtils;
import com.lemon.easyApiTest.Utils.UserData;
import com.lemon.easyApiTest.pojo.CaseInfo;
import com.lemon.easyApiTest.pojo.WriteBackData;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import com.alibaba.fastjson.JSONPath;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BaseCase {
    public int sheetIndex;
    public int sheetNum;
    public String path;

    @BeforeClass
    @Parameters({"sheetIndex","sheetNum"})
    public void beforeClass(int sheetIndex,int sheetNum)throws Exception{
        this.sheetIndex = sheetIndex;
        this.sheetNum = sheetNum;
        this.path = ExcelUtils.readConfig();
        System.out.println(path);
    }

    @AfterSuite
    public void afterClass() throws Exception {
        ExcelUtils.batchWrite();
        //删除垃圾数据
    }

    /**
     * 从 responseBody 通过jsonpaht取出对应参数，存入到UserData.VARS中
     * @param responseBody              接口响应json字符串
     * @param jsonPathExpression        jsonpath表达式
     * @param userDataKey               VARS中key
     */
    public void getParamsInUserData(String responseBody,String jsonPathExpression,String userDataKey) {
        Object token = JSONPath.read(responseBody, jsonPathExpression);
        //存储到vars中
        if(token != null) {
            UserData.VARS.put(userDataKey,token);
        }
    }

    /**
     * 获取鉴权头，加入默认请求头，并且返回。
     * @return
     */
    public HashMap<String, String> getAuthorization() {
        Object token = UserData.VARS.get("${token}");
        HashMap<String,String> headers = new HashMap<>();
        //添加鉴权头
        headers.put("Authorization","Bearer " + token);
        //添加默认头
        headers.putAll(UserData.DEFAUT_HEADERS);
        return headers;
    }

    /**
     * 添加回写对象到集合中
     * @param sheetIndex Excel的sheet索引
     * @param rowNum  行号
     * @param cellNum 列号
     * @param content 内容
     */
    public void addWriteBaseData(int sheetIndex,int rowNum, int cellNum,String content) {
        //响应回写
        WriteBackData wbd = new WriteBackData(sheetIndex,rowNum,cellNum,content);
        //批量回写存储到List集合
        ExcelUtils.wbdlist.add(wbd);
    }

    /**
     * 断言方法
     * @param expectedResult 期望结果
     * @param responseBody 响应体
     */
    public boolean assertFunction(String expectedResult, String responseBody) {
        //4.断言响应结果 断言：期望值与实际值匹配
        //expectedResult转成Map
        Map<String,Object> map = JSONObject.parseObject(expectedResult,Map.class);
        //遍历map
        Set<String> keySet = map.keySet();
        boolean responseAssertFlag = true;
        for(String actualExpression:keySet){
            //获取期望值
            Object expectedValue = map.get(actualExpression);
            //通过表达式从响应体获取实际值
            Object actualValue = JSONPath.read(responseBody,actualExpression);
            //断言逻辑：失败一次，整个断言失败
            if(!expectedValue.equals(actualValue)) {
                //断言失败
                responseAssertFlag = false;
                break;
            }
        }
        System.out.println("断言结果："+responseAssertFlag);
        return responseAssertFlag;
    }

    /**
     * 参数化替换
     * @param caseInfo
     */
    public void paramsReplace(CaseInfo caseInfo) {
        //sql:select count(*) from menmber a where a.mobile_phone = '${register_id}';
        //1.取出需要替换的字段
        String params = caseInfo.getParams();
        String sql = caseInfo.getSql();
        String expectedResult = caseInfo.getExpectedResult();
        String url = caseInfo.getUrl();
        //2.获取所有占位符和实际值
        Set<String> keySet = UserData.VARS.keySet();
        //3.遍历所有的占位符和实际值
        for (String placeHolder:keySet){
            //4.取出实际值
            String value = UserData.VARS.get(placeHolder).toString();
            if(StringUtils.isNotBlank(params)){
                //实际值value替换占位符placeHolder
                params = params.replace(placeHolder,value);
                caseInfo.setParams(params);
            }
            if(StringUtils.isNotBlank(sql)){
                //实际值value替换占位符placeHolder
                sql = sql.replace(placeHolder,value);
                caseInfo.setSql(sql);
            }
            if(StringUtils.isNotBlank(expectedResult)){
                //实际值value替换占位符placeHolder
                expectedResult = expectedResult.replace(placeHolder,value);
                caseInfo.setExpectedResult(expectedResult);
            }
            if(StringUtils.isNotBlank(url)){
                //实际值value替换占位符placeHolder
                url = url.replace(placeHolder,value);
                caseInfo.setUrl(url);
            }
        }
    }
}
