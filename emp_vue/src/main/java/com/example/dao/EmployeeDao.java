package com.example.dao;

import com.example.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmployeeDao {
    List<Employee> findAll();

    void save(Employee employee);

    void deleteById(int id);

    Employee findOneById(int id);

    void updateOne(Employee employee);

    void updateOneExceptPicture(Employee employee);
}
