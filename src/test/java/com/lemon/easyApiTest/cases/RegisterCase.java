package com.lemon.easyApiTest.cases;
import com.alibaba.fastjson.JSONPath;
import com.lemon.easyApiTest.Utils.SQLUtils;
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

public class RegisterCase extends BaseCase{
    @Test(dataProvider = "datas")
    public void test(CaseInfo caseInfo) {
        //1.参数化替换
        paramsReplace(caseInfo);
        //2.数据库前置查询结果（数据断言必须在接口执行前后查询）
        Long beforeSQLResult = (Long)SQLUtils.getSingleResult(caseInfo.getSql());

        //3.调用注册接口
        String responseBody =  HttpUtils.call(caseInfo,UserData.DEFAUT_HEADERS);
        //4.断言响应结果
        boolean responseAssertFlag = assertFunction(caseInfo.getExpectedResult(), responseBody);
        //5.添加接口响应回写内容
        addWriteBaseData(sheetIndex,caseInfo.getId(),8,responseBody);
        //6.数据库后置查询结果
        Long afterSQLResult = (Long)SQLUtils.getSingleResult(caseInfo.getSql());
        //7.数据库断言
        sqlAssert(caseInfo, beforeSQLResult, afterSQLResult);
    }

    /**
     * 数据库断言：与业务逻辑绑定，每个接口业务不一样，数据库断言方法无法抽取供其他子类使用
     * @param caseInfo
     * @param beforeSQLResult
     * @param afterSQLResult
     */
    public boolean sqlAssert(CaseInfo caseInfo, Long beforeSQLResult, Long afterSQLResult) {
        boolean flag = false;
        //7.数据库断言
        if(StringUtils.isNotBlank(caseInfo.getSql())){//如果sql为空，不需要断言
            if(beforeSQLResult == 0 && afterSQLResult == 1){
                System.out.println("数据库断言成功");
                flag = true;
            }else{
                System.out.println("数据库断言失败");
            }
        }
        return flag;
    }

    @DataProvider(name = "datas")
    public Object[] datas() {
        List list = ExcelUtils.read(sheetIndex,sheetNum,CaseInfo.class,path);
        return list.toArray();
    }
}
