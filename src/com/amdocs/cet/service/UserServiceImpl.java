package com.amdocs.cet.service;

import com.amdocs.cet.bean.User;
import com.amdocs.cet.dao.UserDao;
import com.amdocs.cet.utils.PasswordUtils;

public class UserServiceImpl implements UserServiceIntf {

    private UserDao userDao = new UserDao();

    @Override
    public boolean registerUser(User user) {
        // Hash the password before storing
        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        return userDao.registerUser(user);
    }

    @Override
    public int loginUser(String username, String password) {
        // Hash the provided password to compare with stored hash
        String hashedPassword = PasswordUtils.hashPassword(password);
        return userDao.validateUser(username, hashedPassword);
    }
}
