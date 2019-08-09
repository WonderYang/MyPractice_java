package com.yy.service;

import com.yy.po.User;

import java.util.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 19:28 2019/8/7
 */
public interface ServiceUser {
    List<User> getUserList() throws Exception;
}
