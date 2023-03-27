package com.springboot;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.mapper.SysUserMapper;
import com.springboot.pojo.SysUser;
import com.springboot.service.SysUserService;
import com.springboot.utils.PasswordEncryptionUtil;
import com.springboot.utils.SnowflakeIdGenerator;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

public class ApplicationTest {
    @Autowired
    private SysUserService sysUserService;

    @Test
    public void test1() {
        String token = UUID.randomUUID() + "";
        System.out.println(token);
        System.out.println("token长度为："+token.length());
    }

    //远程ip
    @Test
    public void test2() {
        try {
            InetAddress ip = InetAddress.getByName("www.google.com");
            System.out.println("IP地址: " + ip.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    //本地ip
    @Test
    public void test3() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("IP地址: " + ip.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    //加密和解密用户密码
    @Test
    public void test4() throws Exception {
        //加密
//        String password = PasswordEncryptionUtil.encryptPassword("1234567", null);
        //pfwndlH4hV+oy9LtWuTt/Q==
//        System.out.println(password);

        //解密
        String password1 = PasswordEncryptionUtil.decryptPassword("GyAAL/ICSLHXNDuBeRWnB5VtnYOjLEw1sKDdglGLD0w=", null);
        System.out.println(password1);

    }

    //雪花算法生成ID，长度为9-10
    @Test
    public void test5() throws Exception {
        // 创建 SnowflakeIdGenerator 对象，传入机器 ID
        String l = SnowflakeIdGenerator.generateUniqueId();
        System.out.println(l.length());

    }

    //生成token值
    @Test
    public void test6() throws Exception {
        System.out.println(Long.toString(System.currentTimeMillis()/1000L));
    }
}
