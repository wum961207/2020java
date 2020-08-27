
package com.lemon.easyApiTest.Utils;
import com.lemon.easyApiTest.pojo.Member;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class SQLUtils {
    public static void main(String[] args) throws SQLException {
        selectScalarHander();
        return;
    }

    /**
     * sql查询
     * @param sql SQL语句
     */
    public static Object getSingleResult(String sql) {
        if(StringUtils.isBlank(sql)){
            return null;
        }
        //1.定义返回值
        Object result = null;
        try {
            //2.创建DButils sql语句操作类
            QueryRunner queryRunner = new QueryRunner();
            //3.获取数据库连接
            Connection connection = JDBCUtils.getConnection();
            //4.创建ScalarHandle 针对单行单列的数据
            ScalarHandler<BigDecimal> scalarHandler = new ScalarHandler<>();
            //5.执行SQL语句
            result = queryRunner.query(connection, sql, scalarHandler);
            System.out.println(result);
            //6.关闭数据连接
            JDBCUtils.close(connection);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void selectScalarHander() throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select count(*) from member where id = 2073699;";//scalarHander一行一列的数据可以这样写，数字
        // 返回值为long，但是泛型可以设置，是String可以改为String
        Connection connection = JDBCUtils.getConnection();
        ScalarHandler<BigDecimal> scalarHandler = new ScalarHandler<>();
        BigDecimal amount = queryRunner.query(connection,sql,scalarHandler);
        System.out.println(amount);
        JDBCUtils.close(connection);
    }

    public static void selectBeanListHander() throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from member limit 5;";
        Connection connection = JDBCUtils.getConnection();
        BeanListHandler<Member> beanHandler = new BeanListHandler<>(Member.class);
        Member member = (Member)queryRunner.query(connection,sql,beanHandler);
        System.out.println(member.toString());
        JDBCUtils.close(connection);
    }

    public static void selectBeanHander() throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from member where id = '2073699'";
        Connection connection = JDBCUtils.getConnection();
        BeanHandler<Member> beanHandler = new BeanHandler<>(Member.class);
        Member member = (Member)queryRunner.query(connection,sql,beanHandler);
        System.out.println(member.toString());
        JDBCUtils.close(connection);
    }

    public static void selectMapHander() throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from member where id = '2073699'";
        Connection connection = JDBCUtils.getConnection();
        MapHandler handler = new MapHandler();
        Map<String,Object> map = queryRunner.query(connection,sql,handler);
        System.out.println(map);
        JDBCUtils.close(connection);
    }

    public static void insert() throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "insert into member values(null,'武萌','123456','15029674486'1,500,now());";
        Connection connection = JDBCUtils.getConnection();
        int count = queryRunner.update(connection, sql);//count:影响多少行
        System.out.println(count);
        JDBCUtils.close(connection);
    }

    public static void update() throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "update member set leave_amount = 200 where id = 2073699";
        Connection connection = JDBCUtils.getConnection();
        int count = queryRunner.update(connection,sql);//count:影响多少行
        System.out.println(count);
        JDBCUtils.close(connection);
    }
}
