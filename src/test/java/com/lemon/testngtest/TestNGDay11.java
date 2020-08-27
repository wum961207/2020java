package com.lemon.testngtest;

import org.testng.annotations.*;

/*用代码说明 @BeforeSuite  @BeforeTest @BeforeClass @BeforeMethod 执行顺序
* 结果：BeforeSuite > BeforeTest > BeforeClass > BeforeMethod
*       AfterMethod > AfterClass > AfterTest > BeforeSuite
* */
public class TestNGDay11 {
    @Test
    public void f(){
        System.out.println("TestNGDay11.f");
    }

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("TestNGDay11.beforeSuite");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("TestNGDay11.afterSuite");
    }

    @BeforeTest
    public void beforeTest(){
        System.out.println("TestNGDay11.beforeTest");
    }

    @AfterTest
    public void afterTest(){
        System.out.println("TestNGDay11.afterTest");
    }

    @BeforeClass
    public void beforeClass(){
        System.out.println("TestNGDay11.beforeClass");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("TestNGDay11.afterClass");
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("TestNGDay11.beforeMethod");
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("TestNGDay11.afterMethod");
    }
}
