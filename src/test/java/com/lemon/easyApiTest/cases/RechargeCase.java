package com.lemon.easyApiTest.cases;

import com.alibaba.fastjson.JSONPath;
import com.lemon.easyApiTest.Utils.ExcelUtils;
import com.lemon.easyApiTest.Utils.HttpUtils;
import com.lemon.easyApiTest.Utils.SQLUtils;
import com.lemon.easyApiTest.pojo.CaseInfo;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class RechargeCase extends BaseCase{

    @Test(dataProvider = "datas")
    public void test(CaseInfo caseInfo) {
        //1.参数化替换
        paramsReplace(caseInfo);
        //2.数据库前置查询结果（数据断言必须在接口执行前后查询）
        BigDecimal beforeSQLResult = (BigDecimal) SQLUtils.getSingleResult(caseInfo.getSql());
        //3.调用接口
        //获取鉴权头+默认头
        HashMap<String, String> headers = getAuthorization();
        String responseBody = HttpUtils.call(caseInfo,headers);
        //4.断言响应接口回写内容
        boolean responseAssertFlag = assertFunction(caseInfo.getExpectedResult(), responseBody);
        //5.添加接口响应回写内容
        addWriteBaseData(sheetIndex,caseInfo.getId(),8, responseBody);
        //6.数据库后置查询结果
        BigDecimal afterSQLResult = (BigDecimal)SQLUtils.getSingleResult(caseInfo.getSql());
        //7.数据库断言
        boolean sqlAssertFlag = sqlAssert(caseInfo, beforeSQLResult, afterSQLResult);
    }

    public boolean sqlAssert(CaseInfo caseInfo, BigDecimal beforeSQLResult, BigDecimal afterSQLResult) {
        boolean flag = false;
        if(StringUtils.isNotBlank(caseInfo.getSql())) {
            //after - before = 参数中amount
            //使用JSONPath取出参数中的amount
            System.out.println(caseInfo.getParams());
            String amountStr = JSONPath.read(caseInfo.getParams(), "amount").toString();
            //String  --> BigDecimal "3.1412"-"3.4121" 计算机无法精确计算，BigDecimal相当于用字符串去减，没有损失
            BigDecimal amount = new BigDecimal(amountStr);
            //结果为0，说明相等；-1：小与它；1：大于它
            if (afterSQLResult.subtract(beforeSQLResult).compareTo(amount) == 0) {
                System.out.println("数据库断言成功");
                flag = true;
            } else {
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
