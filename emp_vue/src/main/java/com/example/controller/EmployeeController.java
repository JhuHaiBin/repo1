package com.example.controller;

import com.example.entity.Employee;
import com.example.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/emp")
@CrossOrigin
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    //读取配置文件中某属性的值并赋予变量realPath
    @Value("${photo.dir}")
    private String realPath;

    /**
     * 用于修改一名员工
     * @param employee
     * @return
     */
    @PostMapping("/updateOne")
    public Map<String ,Object> updateOne(Employee employee, MultipartFile photo) throws IOException{
        log.info("修改员工信息 [{}]",employee.toString());
        Map<String, Object> map = new HashMap<>();
        try {
            //如果前台需要修改照片
            if(photo != null && photo.getSize()!=0){

                //一、删除员工旧头像
                Employee emp = employeeService.findOne(Integer.parseInt(employee.getId()));
                File file = new File(realPath,emp.getPath());//查找realPath路径下名为emp.getPath()的值的文件
                if(file.exists()){
                    file.delete();
                }
                //二、添加员工新头像和新信息
                String newFileName = UUID.randomUUID().toString()+"."+ FilenameUtils.getExtension(photo.getOriginalFilename());
                //将该文件上传到realPath路径中，文件名是newFileName
                photo.transferTo(new File(realPath,newFileName));
                //设置头像地址
                employee.setPath(newFileName);
                //员工信息修改
                employeeService.updateOne(employee);
            }else{
                employeeService.updateOneExceptPicture(employee);
            }
            map.put("state",true);
            map.put("msg","修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","修改失败");
        }
        return map;
    }

    /**
     * 用于查询一名员工
     * @param id
     * @return
     */
    @GetMapping("/findOne")
    public Map<String ,Object> findOne(int id){
        log.info("查询员工的ID [{}]",id);
        Map<String, Object> map = new HashMap<>();

        try {
            Employee emp = employeeService.findOne(id);
            map.put("state",true);
            map.put("emp",emp);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
            map.put("emp","null");
        }


        return map;
    }

    /**
     * 用于查询所有员工
     * @return 所有的员工信息列表
     */
    @GetMapping("/findAll")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }

    /**
     * 用于删除一名员工
     * @param id
     * @return
     */
    @GetMapping("/deleteByid")
    public Map<String,Object> deleteById(int id){
        log.info("删除员工ID [{}]",id);
        Map<String, Object> map = new HashMap<>();
        try {
            //删除员工头像
            Employee emp = employeeService.findOne(id);
            File file = new File(realPath,emp.getPath());//查找realPath路径下名为emp.getPath()的值的文件
            if(file.exists()){
                file.delete();
            }
            //删除员工信息
            employeeService.deleteById(id);
            map.put("state",true);
            map.put("msg","删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","删除失败");
        }

        return map;
    }

    /**
     * 用于添加员工
     * @param employee
     * @param photo
     * @return
     */
    @PostMapping("/save")
    public Map<String ,Object> save(Employee employee, MultipartFile photo) throws IOException {
        log.info("员工信息 [{}]",employee.toString());
        log.info("头像信息 [{}]",photo.getOriginalFilename());
        Map<String, Object> map = new HashMap<>();
        try {
            //头像保存 FilenameUtils.getExtension(photo.getOriginalFilename())→获取文件扩展名
            //UUID.randomUUID().toString() →生成一个随机数
            // 将文件名这样子转换的原因是不需要担心数据库中文件名重复
            String newFileName = UUID.randomUUID().toString()+"."+ FilenameUtils.getExtension(photo.getOriginalFilename());
            //将该文件上传到realPath路径中，文件名是newFileName
            photo.transferTo(new File(realPath,newFileName));
            //设置头像地址
            employee.setPath(newFileName);
            //员工信息保存
            employeeService.save(employee);

            map.put("state",true);
            map.put("msg","添加成功!");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","添加失败!");
        }
        return map;
    }
}
