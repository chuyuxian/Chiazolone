package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.mapper.SysUserMapper;
import com.springboot.entity.SysUser;
import com.springboot.service.SysUserService;
import com.springboot.common.BusinessException;
import com.springboot.utils.PasswordEncryptionUtil;
import com.springboot.utils.SnowflakeIdGenerator;
import com.springboot.utils.TokenGenerator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author 陈臣
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-03-02 21:16:00
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser>
    implements SysUserService {

    //登录功能接口信息
    @Override
    public SysUser getSysUserLogin(String phone,String password) {
        //使用工具类进行加密获取的密码,密码6-12长度,前端设置密码长度
        password = PasswordEncryptionUtil.encryptPassword(password,null);

        //调用本方法中 getSysUserOne根据手机号查询
        SysUser sysUserOne = this.getSysUserByPhone(phone);

        //判断账号是否存在、密码是否正确
        if (sysUserOne == null || !sysUserOne.getPassword().equals(password)) {
            //抛出自定义异常
            throw new BusinessException("账号或密码错误");
        }

        //判断状态是否可用，1：表示正常 0：表示不可用
        if (sysUserOne.getStatus().equals("0")) {
            //抛出自定义异常
            throw new BusinessException("状态不可用，请联系管理员");
        }

        //生成或者更新用户的token String token = UUID.randomUUID().toString(); 这个是原本的，唯一性没有下面的有保证
        String token = TokenGenerator.generateToken();
        sysUserOne.setToken(token);
        //更新数据库中token值
        baseMapper.updateById(sysUserOne);

        return sysUserOne;
    }

    //根据手机号查询
    @Override
    public SysUser getSysUserByPhone(String phone) {
        //创建条件构造器
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        //根据条件构造器 查询手机号
        wrapper.eq(SysUser::getPhone,phone);

        return baseMapper.selectOne(wrapper);
    }

    //根据用户id进行查询
    @Override
    public SysUser getSysUserById(String userId) {
        return baseMapper.selectById(userId);
    }

    //根据用户id进行修改信息
    @Override
    public Integer updateSysUserById(SysUser sysUser) {
        //直接调用SysUserMapper的方法即可
        return baseMapper.updateById(sysUser);
    }

    //用户注册信息
    @Override
    @Transactional //事务管理
    public boolean saveSysUser(SysUser sysUser) {
        //创建条件构造器
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        // 获取手机号（账号）
        String phone = sysUser.getPhone();

        SysUser existUser = null;
        try {
            //调用service中 根据手机号查询单个用户
            existUser = this.getSysUserByPhone(phone);
        } catch (Exception e) {
            throw new BusinessException("系统错误，请稍后再试");
        }

        //如果查询到了，那么就报错，停止注册
        if (existUser != null) {
            throw new BusinessException("该账号已被注册");
        }

        //使用工具类创建唯一性 ID
        String id = SnowflakeIdGenerator.generateUniqueId();
        //根据条件构造器，查询用户id
        wrapper.eq(SysUser::getId,id);

        //根据工具类创建的唯一性的id进行查询
        SysUser one = baseMapper.selectById(id);

        if (one != null) { //判断是否有一样的id，如果有报错进行处理。一般情况下是没有的，但是有很小的几率
            throw new BusinessException("生成用户id错误！！");
        }

        //将密码进行加密操作
        String password = PasswordEncryptionUtil.encryptPassword(sysUser.getPassword(), null);

        sysUser.setId(id); //设置创建好的唯一性ID
        sysUser.setPassword(password);

        // 设置其他默认属性
        sysUser.setStatus("1"); // 默认开启账号

        // 保存用户信息
        int insert = baseMapper.insert(sysUser);

        //返回给控制类结果
        return insert != 0;
    }

//    //拦截器那边的请求，判断是否有正确的用户名和密码
//    @Override
//    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
//        //调用本方法中 getSysUserOne根据手机号查询
//        SysUser sysUserOne = this.getSysUserByPhone(phone);
//
//        //判断账号是否存在、密码是否正确
//        if (sysUserOne == null ) {
//            //抛出自定义异常
//            throw new BusinessException("账号不存在");
//        }
//
//        List<GrantedAuthority> auths =
//                AuthorityUtils.commaSeparatedStringToAuthorityList("role");
//
//        //从查询数据库返回users对象，得到用户名和密码，返回
//        return null;
//    }
}




