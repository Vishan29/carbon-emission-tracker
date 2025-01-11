package com.amdocs.cet.service;

import com.amdocs.cet.bean.User;

public interface UserServiceIntf {
    boolean registerUser(User user);
    int loginUser(String username, String password);
}
