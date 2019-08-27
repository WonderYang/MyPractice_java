package com.yy.mapper;

import com.yy.po.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 14:45 2019/8/27
 */
@Component
public interface UserMapper {
    List<User> queryUserList() throws Exception;
}
