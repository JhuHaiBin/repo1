package com.example.service;

import com.example.dao.UserDao;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public void register(User user) {
        //查询用户名是否为空
        if(userDao.findByUserName(user.getUserName()) == null){
            //生成用户状态
            user.setStatus("已激活");
            //设置用户注册时间
            user.setRegisterTime(new Date());
            //调用Dao
            userDao.save(user);
        }else{
            throw new RuntimeException("用户名已存在");
        }
    }

    @Override
    public User login(User user) {
        //检验是否存在该用户名
        User userDB = userDao.findByUserName(user.getUserName());
        if(userDB != null){
            //检验密码是否正确
            if(userDB.getPassword().equals(user.getPassword())){
                return userDB;
            }else{
                throw new RuntimeException("密码错误");
            }
        }else{
            throw new RuntimeException("该账号不存在");//抛出异常之后，可以不用return
        }
    }

}
