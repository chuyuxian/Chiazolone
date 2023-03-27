package com.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication //标识为启动类
@MapperScan("com.springboot.mapper") //扫描所有的mapper
@EnableTransactionManagement //开启事务注解
@ServletComponentScan("com.springboot") //用于扫描指定包下的Servlet组件，主要就是拦截器
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
