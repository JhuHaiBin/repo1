package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import com.example.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin //允许跨域
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用于用户登录
     * @param user
     * @return state msg
     */
    @PostMapping("/login")
    public Map<String , Object> login(@RequestBody User user){
        log.info("用户信息:[{}]",user.toString());
        Map<String , Object> map = new HashMap<>();

        try {
            //如果login()方法抛异常，那么try中下面的代码都不再执行，直接跳转到catch代码块中
            User userDB = userService.login(user);
            map.put("state",true);
            map.put("msg","登录成功!");
            map.put("user",userDB);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
            map.put("msg",e.getMessage());//login()方法抛出的异常信息
        }

        return map;
    }

    /**
     * 用于用户注册
     * @param user
     * @param code
     * @param request
     * @return state msg
     */
    @PostMapping("/register")
    public Map<String , Object> register(@RequestBody User user,String code,HttpServletRequest request){
        log.info("用户信息:[{}]",user.toString());
        log.info("用户输入验证码信息:[{}]",code);
        Map<String, Object> map = new HashMap<>();
        try {
            //获取前端传来的用户输入的验证码
            String key = (String) request.getServletContext().getAttribute("code");
            //如果验证码匹配
            if(key.equalsIgnoreCase(code)){
                //开始注册，如果用户名存在，则抛出异常“用户名已存在”，直接跳转到catch下执行
                userService.register(user);
                map.put("state",true);
                map.put("msg","注册成功!");
            }else{
                throw new RuntimeException("验证码错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("state",false);
            map.put("msg",e.getMessage());
        }
        return map;
    }







    /**
     * 生成验证码
     */
    @GetMapping("/getImage")
    public String getImageCode(HttpServletRequest request){
        //1、使用工具类生成验证码(4位)
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //2、将验证码放入ServletContext作用域（最大的作用域）
        request.getServletContext().setAttribute("code",code);
        //3、将图片转化位base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            VerifyCodeUtils.outputImage(100,30,byteArrayOutputStream,code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "data:image/png;base64,"+ Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
    }


}
