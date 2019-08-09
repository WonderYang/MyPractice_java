package com.yy.service;

import com.yy.dao.UserDao;
import com.yy.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 10:17 2019/8/8
 */
@Service
public class ServiceUserImpl implements ServiceUser {

    @Autowired   //通过注解方式注入
    UserDao userDao;
    @Override
    public List<User> getUserList() throws Exception{
        return userDao.queryUserDao();
    }
}
