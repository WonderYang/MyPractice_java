package com.yy.service;

import com.yy.dao.UserDao;
import com.yy.po.User;

import java.util.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 10:17 2019/8/8
 */
public class ServiceUserImpl implements ServiceUser {

    UserDao userDao = new UserDao();
    @Override
    public List<User> getUserList() throws Exception{
        return userDao.queryUserDao();
    }
}
