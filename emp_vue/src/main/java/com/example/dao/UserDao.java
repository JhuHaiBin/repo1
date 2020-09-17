package com.example.dao;

import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    //用户注册
    void save(User user);
    //用户查询
    User findByUserName(String userName);
}
