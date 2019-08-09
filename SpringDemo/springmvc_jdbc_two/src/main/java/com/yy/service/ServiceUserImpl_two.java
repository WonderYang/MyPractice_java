package com.yy.service;

import com.yy.dao.UserDao;
import com.yy.po.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 18:01 2019/8/9
 */
public class ServiceUserImpl_two implements ServiceUser{

    //通过配置方式注入
    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getUserList() throws Exception{
        return userDao.queryUserDao();
    }
}
