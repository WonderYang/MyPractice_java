package com.yy.pack;

import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2DTM2;
import com.yy.po.UserEx;

import java.util.List;

/**
 * @Author : YangY
 * @Description : User类的包装类
 * @Time : Created in 11:13 2019/8/10
 */
public class UserPack {
    private UserEx userEx;

    //辅助根据多个id值的查询
    private List<Integer> userIdList;

    public List<Integer> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Integer> userIdList) {
        this.userIdList = userIdList;
    }

    public UserEx getUserEx() {
        return userEx;
    }

    public void setUserEx(UserEx userEx) {
        this.userEx = userEx;
    }
}
