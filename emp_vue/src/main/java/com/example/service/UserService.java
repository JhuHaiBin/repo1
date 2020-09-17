package com.example.service;

import com.example.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface UserService {

    //注册
    void register(User user);
    //登录
    User login(User user);
}
