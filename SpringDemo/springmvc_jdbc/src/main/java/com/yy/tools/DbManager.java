package com.yy.tools;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 17:18 2019/8/7
 */
public class DbManager {
    Connection connection;

    public DbManager() {

    }
    public Connection getConnection() {
        Properties properties = Utils.getProperties("db.properties");
        try {
            String driver = properties.getProperty("jdbc.driver");
            String url = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            Class.forName(driver);
            this.connection = DriverManager.getConnection(url,username,password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.connection;
    }

    //获得查询结果
    public ResultSet getQueryResultSet(String sql) {
        ResultSet resultSet = null;
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void closeSource() {

    }

    public static Properties getProperties(String fileName) {
        Properties properties = new Properties();
        //InputStream in =
        //properties.load();
        return null;
    }


}
