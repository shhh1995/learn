package com.learn.thread.base;

import lombok.Data;

@Data
public class UserImpl {

    private String userName;
    private String userId;
    private int money;  //红包金额

    public UserImpl(String userId, String userName){
        this.userId = userId;
        this.userName = userName;
    }
}
