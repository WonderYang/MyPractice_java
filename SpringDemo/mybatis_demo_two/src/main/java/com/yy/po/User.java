package com.yy.po;

import java.util.Date;

/**
 * @Author : YangY
 * @Description : 属性名必须与数据库对应的表的名称一致,不对应的话查出来的属性值就会为null
 * @Time : Created in 15:34 2019/8/7
 */
public class User {
    private int id;
    private String username;
    private Date birthday;
    private char sex;
    private String address;

    public User() {

    }

    public int getUserid() {
        return id;
    }

    public void setUserid(int userid) {
        this.id = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + id +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                '}';
    }
}
