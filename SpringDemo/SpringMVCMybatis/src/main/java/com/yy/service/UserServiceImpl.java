package com.yy.service;

import com.yy.mapper.UserMapper;
import com.yy.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 15:17 2019/8/27
 */

public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> queryUserListService() throws Exception {

        return userMapper.queryUserList();
    }
}
