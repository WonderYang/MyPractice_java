package com.yy.po;

/**
 * @Author : YangY
 * @Description : 属性名必须与数据库对应的表的名称一致
 * @Time : Created in 15:34 2019/8/7
 */
public class User {
    private int userid;
    private String username;
    private String useraddr;

    //要保留一个无参构造
    public User() {

    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseraddr() {
        return useraddr;
    }

    public void setUseraddr(String useraddr) {
        this.useraddr = useraddr;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", useraddr='" + useraddr + '\'' +
                '}';
    }
}
