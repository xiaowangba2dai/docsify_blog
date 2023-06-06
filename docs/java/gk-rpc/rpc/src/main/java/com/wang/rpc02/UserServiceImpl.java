package com.wang.rpc02;

import com.wang.rpc.common.IUserService;
import com.wang.rpc.common.User;

public class UserServiceImpl implements IUserService {
    @Override
    public User findUserById(Integer id) {
        return new User(id, "Alice");
    }
}
