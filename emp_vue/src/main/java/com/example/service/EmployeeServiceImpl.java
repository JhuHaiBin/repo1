package com.example.service;

import com.example.dao.EmployeeDao;
import com.example.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class) //这个类下的所有public方法都将会被加上事务管理（加上属性rollbackFor=Exception.class,则代表只要出现异常就会回滚）
public class EmployeeServiceImpl implements  EmployeeService{

    @Autowired
    private EmployeeDao employeeDao;



    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Override
    public void save(Employee employee) {
        employeeDao.save(employee);
    }

    @Override
    public void deleteById(int id) {
        employeeDao.deleteById(id);
    }

    @Override
    public Employee findOne(int id) {

        return employeeDao.findOneById(id);
    }

    @Override
    public void updateOne(Employee employee) {
        employeeDao.updateOne(employee);
    }

    @Override
    public void updateOneExceptPicture(Employee employee) {
        employeeDao.updateOneExceptPicture(employee);
    }


}
