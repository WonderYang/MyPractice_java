package com.yy.mapper;

import com.yy.pack.UserPack;
import com.yy.po.User;
import com.yy.po.UserEx;

import java.util.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 7:55 2019/8/10
 */
public interface IuserMapper {

    List<UserEx> queryUserBySN(UserPack userPack) throws Exception;

    List<UserEx> queryUserBySN_one(UserPack userPack) throws Exception;

    int queryUserCount(UserPack userPack) throws Exception;

    List<User> queryUserByAlias_two() throws Exception;

    List<User> queryUserByMultyIds(UserPack userPack) throws Exception;
}
