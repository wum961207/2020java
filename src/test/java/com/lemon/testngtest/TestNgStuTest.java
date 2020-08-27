package com.lemon.testngtest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/*
2、使用 @dataProvider 对 register(Student s)方法  进行3次测试 ,Student 有 username、password、type、sex 4个私有属性。
 */
public class TestNgStuTest {
    @Test(dataProvider = "datas")
    @Parameters({"username","password","type","sex"})
    public void register(Student stu){
        System.out.println("username = [" + stu.getUsername() + "], password = [" + stu.getPassword() + "], type = [" + stu.getType() + "], sex = [" + stu.getSex() + "]");
    }

    @DataProvider(name = "datas")
    public Object[] datasMethod(){
        Student stu1 = new Student("zhangsan","123456","get","男");
        Student stu2 = new Student("lisi","123456","get","男");
        Student stu3 = new Student("marray","123456","post","女");
        Object[] datas = {stu1,stu2,stu3};
        return datas;
    }
}
