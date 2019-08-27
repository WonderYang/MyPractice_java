package com.yy.service;

import com.yy.po.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 15:16 2019/8/27
 */
@Component
public interface UserService {
    public List<User> queryUserListService() throws Exception;
}
