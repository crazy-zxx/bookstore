package com.me.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 * JDBC 的工具类
 */
@Slf4j
public class JDBCUtils {

    private static DataSource dataSource = null;

    static {
        dataSource = new ComboPooledDataSource("bookstore");
    }

    //获取数据库连接
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.error("Connection 获取错误!\n" + sw.toString());
        }

        return null;
    }

    //关闭数据库连接
    public static void release(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.warn("Connection 关闭错误!\n" + sw.toString());
        }
    }

    //关闭当前数据库查询的Statement对象和结果集
    public static void release(ResultSet rs, Statement statement) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.warn("ResultSet 关闭错误!\n" + sw.toString());
        }

        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.warn("Statement 关闭错误!\n" + sw.toString());
        }
    }

}
