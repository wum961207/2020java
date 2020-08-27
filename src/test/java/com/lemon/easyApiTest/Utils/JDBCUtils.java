package com.lemon.easyApiTest.Utils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class JDBCUtils {
    public static Connection getConnection(){
        //定义数据库连接
        String url = "jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8";
        String user="future";
        String password="123456";
        //定义数据库连接对象
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return connection;
    }

    public static void close(Connection connection) throws SQLException {
        if(connection!=null){
            connection.close();
        }
    }
}
