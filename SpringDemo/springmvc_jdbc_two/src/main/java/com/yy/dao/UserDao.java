package com.yy.dao;

import com.yy.po.User;
import com.yy.tools.DbManager;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 19:02 2019/8/7
 */
@Repository
public class UserDao {
    DbManager dbManager = new DbManager();
    private List<User> list = new ArrayList<>();
    //查询表中全部信息，并以User类封装结果进行返回
    public List<User> queryUserDao() throws Exception{
        String sql = "select * from user";
        ResultSet resultSet = dbManager.getQueryResultSet(sql);
        while (resultSet.next()) {
            User user = new User();
            user.setUserid(resultSet.getInt("userid"));
            user.setUsername(resultSet.getString("username"));
            user.setUseraddr(resultSet.getString("useraddr"));
            list.add(user);
        }
        return list;
    }
}
