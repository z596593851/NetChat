package com.hxm.server.module.user.entity;

/**
 * Created by xiaoming on 2019/3/1.
 */
public class User {
    private int userId;
    public User(int userId){
        this.userId=userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
