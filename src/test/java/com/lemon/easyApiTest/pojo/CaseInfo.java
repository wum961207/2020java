package com.lemon.easyApiTest.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class CaseInfo {
    @Excel(name = "用例编号")
    private int id;
    @Excel(name = "接口名称")
    private String name;
    @Excel(name = "url")
    private String url;
    @Excel(name = "请求方式")
    private String type;
    @Excel(name = "用例描述")
    private String desc;
    @Excel(name = "参数")
    private String params;
    @Excel(name = "参数类型")
    private String contentType;
    @Excel(name = "期望结果")
    private String expectedResult;
    @Excel(name="sql")
    private String sql;
    @Excel(name="isPass")
    private String isPass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    @Override
    public String toString() {
        return "CaseInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", params='" + params + '\'' +
                ", contentType='" + contentType + '\'' +
                ", expectedResult='" + expectedResult + '\'' +
                ", sql='" + sql + '\'' +
                ", isPass='" + isPass + '\'' +
                '}';
    }
}
