package com.example.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//该工具类在springboot启动时，用于获取springboot自动创建好的工厂
@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    //ApplicationContext是Spring中的容器，可以用来获取容器中的各种bean组件
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    //根据名字获取bean组件
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }
}
