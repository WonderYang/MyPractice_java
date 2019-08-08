package com.yy;

import com.yy.dao.UserDao;
import com.yy.po.User;
import com.yy.tools.DbManager;
import org.junit.Assert;
import org.junit.Test;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 12:17 2019/8/8
 */
public class myTest {
    @Test
    public void test() {
        DbManager dbManager = new DbManager();
        ResultSet resultSet = dbManager.getQueryResultSet(" select * from user");
        System.out.println(resultSet);
        UserDao userDao = new UserDao();
        try {
            List<User> list = userDao.queryUserDao();
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
