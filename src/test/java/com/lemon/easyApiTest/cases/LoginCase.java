package com.lemon.easyApiTest.cases;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.lemon.easyApiTest.Utils.UserData;
import com.lemon.easyApiTest.pojo.CaseInfo;
import com.lemon.easyApiTest.Utils.ExcelUtils;
import com.lemon.easyApiTest.Utils.HttpUtils;
import com.lemon.easyApiTest.pojo.WriteBackData;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

public class LoginCase extends BaseCase{

    @Test(dataProvider = "datas")
    public void test(CaseInfo caseInfo) {
        //1.参数化替换
        paramsReplace(caseInfo);
        //3.调用注册接口
        String responseBody = HttpUtils.call(caseInfo,UserData.DEFAUT_HEADERS);
        getParamsInUserData(responseBody,"$.data.token_info.token","${token}");
        getParamsInUserData(responseBody,"$.data.id","${member_id}");
        //4.断言响应结果 实际==期望，成功，否则失败
        boolean responseAssertFlag = assertFunction(caseInfo.getExpectedResult(), responseBody);
        //5.添加接口响应回写内容
        addWriteBaseData(sheetIndex,caseInfo.getId(),8, responseBody);
        //6.数据库后置查询结果
        //7.数据库断言
        //8.添加断言回写内容
        String assertResult = responseAssertFlag ? "PASSED":"FAILED";
        addWriteBaseData(sheetIndex,caseInfo.getId(),10, assertResult);
        //9.添加日志

    }

    @DataProvider(name = "datas")
    public Object[] datas() {
        List list = ExcelUtils.read(sheetIndex,sheetNum,CaseInfo.class,path);
        System.out.println(path);
        return list.toArray();
    }
}
