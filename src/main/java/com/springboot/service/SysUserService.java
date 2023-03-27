package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.entity.SysUser;

/**
* @author 陈臣
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-03-02 21:16:00
*/
public interface SysUserService extends IService<SysUser> {

    /**
     * 登录功能接口信息
     * @param phone 用户的手机号（账号）
     * @param password 用户的密码
     * @return 返回登录结果
     */
    SysUser getSysUserLogin(String phone,String password);

    /**
     * 根据用户的手机号查询
     * @param phone 用户的手机号
     * @return 返回查询结果
     */
    SysUser getSysUserByPhone(String phone);

    /**
     * 根据用户id进行查询
     * @param userId 用户id
     * @return 返回查询结果
     */
    SysUser getSysUserById(String userId);

    /**
     * 根据用户id进行修改信息
     * @param sysUser 用户的实体类接受控制类的json数据
     */
    Integer updateSysUserById(SysUser sysUser);

    /**
     * 用户注册(新增)信息
     * @param sysUser 用户的实体类接受控制类的json数据
     * @return 返回注册结果
     */
    boolean saveSysUser(SysUser sysUser);
}
