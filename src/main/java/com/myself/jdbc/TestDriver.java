package com.myself.jdbc;

import com.mysql.cj.jdbc.Driver;
import sun.misc.Service;

import java.sql.*;
import java.util.Properties;
import java.util.ServiceLoader;

/**
 *  JDBC 打破双亲委派机制以及 SPI 的使用
 *
 *
 */

public class TestDriver {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Properties properties = new Properties();
        properties.put("user","quanan50jia");
        properties.put("password","Quanan@50Jia");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection =DriverManager.getConnection("jdbc:mysql://rm-2zex5h4lnktr554is.mysql.rds.aliyuncs.com:3306/rules",properties);


//        Connection connection = new Driver().connect("jdbc:mysql://rm-2zex5h4lnktr554is.mysql.rds.aliyuncs.com:3306/rules",properties);
//        Statement  statement =connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("select  * from rules");
//        System.out.println(resultSet);

    }
}
