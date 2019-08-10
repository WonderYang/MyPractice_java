package com.yy.mapper;

import com.yy.po.User;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 7:55 2019/8/10
 */
public interface IuserMapper {

    User queryUserById(int id) throws Exception;

    //add
//    public int addUser(User user) throws Exception;
//
//    public int modifyUser(int id, User user) throws Exception;
//
//    public int delUser(int id) throws Exception;

}
