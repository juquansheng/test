package com.malaxiaoyugan.test;

import com.mysql.jdbc.Driver;

import java.sql.*;

/**
 * description: JdbcTest
 * date: 2021/3/4 16:02
 * author: juquansheng
 * version: 1.0 <br>
 */
public class JdbcTest1 {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //注册驱动2 反射
            /*Driver driver = new Driver();
            DriverManager.registerDriver(driver);*/
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //获取连接对象
            connection = DriverManager.getConnection("jdbc:mysql://106.13.15.122:3306/forum", "root", "MIma123456!");
            System.out.println("connection:"+connection);
            //获取数据库操作对象 执行sql语句
            statement = connection.createStatement();
            System.out.println("connection:"+connection);
            //执行sql
            String sql = "SELECT * FROM `forum`.`user`";
            resultSet = statement.executeQuery(sql);
            System.out.println("resultSet:"+resultSet);
            while (resultSet.next()){
                System.out.print(resultSet.getString(1));
                System.out.print(resultSet.getString(2));
                System.out.print(resultSet.getString(3));
                System.out.print(resultSet.getString(4));
                System.out.print(resultSet.getString(5));
                System.out.print(resultSet.getString(6));
                System.out.print(resultSet.getString(7));
                System.out.print(resultSet.getString(8));
                System.out.print(resultSet.getString(9));

                System.out.println(resultSet.getString(9));
            }

            System.out.println("resultSet:"+resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                assert resultSet != null;
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
