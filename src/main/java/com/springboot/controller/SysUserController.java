package com.springboot.controller;

import com.springboot.common.R;
import com.springboot.pojo.SysUser;
import com.springboot.service.SysUserService;
import com.springboot.common.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 用户登录管理控制类
 */
@Slf4j
@Api(tags = "用户登录控制类")
@RestController
@RequestMapping("/api/SysUser")
public class SysUserController {
    /**
     * 已知Bug
     *   1、退出功能，退出了之后却没有清空token值
     *          这个还是因为MyBaits-Plus的默认设置的问题，在更新数据库的时候，如果有字段是null的情况下是自动忽略的，就是执行的SQL语句里面是没有这个字段名的
     *      而我们设置的token就是null，导致在更新的时候没有token这个字段，所以无法进行token值的更新，只需要在属性名上面加上@TableField(updateStrategy = FieldStrategy.IGNORED) 标注即可
     *
     *   2、注册账号，登录时没有账号自动进行注册
     *      未实现，准备是在判断账号和密码哪里加入，具体怎么弄，后面在自己想想
     *
     */

    /**
     * 已实现的功能
     *      1、登录
     *          token 新增值
     *      2、注册
     *          事务管理
     *      3、退出
     *          清空session
     *          清空token
     *      4、拦截器
     */

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private HttpSession session;

    /**
     * 登录接口
     * @param phone    用户的手机号
     * @param password 用户的密码
     * @return 返回登录结果
     */
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public R<SysUser> login(String phone, String password) {
        //从service中调用方法 根据手机号查询单个数据
        SysUser sysUserOne = sysUserService.getSysUserLogin(phone, password);

        //登录成功之后，将用户的id存入session中，以便接下来的操作
        session.setAttribute("sysUser_Id", sysUserOne.getId());

        //将数据返回给前端
        return R.success(sysUserOne);
    }

    /**
     * 退出接口
     * @return 返回结果
     */
    @ApiOperation("退出接口")
    @GetMapping("/logout")
    private R<String> logout() {
        //查询用户的信息
        String sysUser_id =(String) session.getAttribute("sysUser_Id");
        SysUser sysUser = sysUserService.getSysUserById(sysUser_id);

        //如果有值的话，清理session中的id和token
        if (sysUser != null) {
            // 根据用户ID，更新用户的token值为null
            sysUser.setToken("");
            //调用service中 根据用户id进行修改信息
            sysUserService.updateSysUserById(sysUser);
            //清除session的值
            session.removeAttribute("sysUser_Id");
            //返回成功结果
            return R.success(sysUser.getName() + "退出登录成功");
        }
        //返回错误结果
        return R.error("退出登录失败");
    }

    /**
     * 用户注册（新增）接口
     * @param sysUser 用户实体类用来接受json数据
     * @return 返回结果
     */
    @ApiOperation("注册接口")
    @PostMapping("/register")
    private R<String> register(@RequestBody SysUser sysUser) {
        //调用service中 用户注册信息
        boolean b = sysUserService.saveSysUser(sysUser);
        //进行判断，返回合适的结果
        if (b) {
            //返回成功结果
            return R.success("注册成功");
        }
        //返回失败结果
        return R.error("注册失败");
    }

    /**
     * 全局统一异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public R<String> handleBusinessException(BusinessException ex) {
        //将异常信息转换为适合前端展示的格式
        return R.error(ex.getMessage());
    }
}
