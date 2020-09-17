package com.example.service;

import com.example.entity.Employee;
import com.example.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface EmployeeService {

    //查询所有
    List<Employee> findAll();
    //添加员工
    void save(Employee employee);
    //删除员工
    void deleteById(int id);
    //查找一名员工
    Employee findOne(int id);
    //修改员工
    void updateOne(Employee employee);

    void updateOneExceptPicture(Employee employee);
}
