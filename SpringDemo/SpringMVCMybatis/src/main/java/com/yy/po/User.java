package com.yy.po;

import java.util.Date;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 14:48 2019/8/27
 */
public class User {
    private int id;
    private String username;
    private Date birthday;
    private char sex;
    private String address;

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
