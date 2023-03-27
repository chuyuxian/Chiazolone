package com.springboot.controller;

import com.springboot.common.R;
import com.springboot.entity.SysUser;
import com.springboot.service.SysUserService;
import com.springboot.common.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
     *      而我们设置的token就是null，导致在更新的时候没有token这个字段，所以无法进行token值的更新，
     *      只需要在属性名上面加上@TableField(updateStrategy = FieldStrategy.IGNORED) 标注即可恢复使用
     *
     *   2、注册账号，登录时没有账号自动进行注册
     *      未实现，准备是在判断账号和密码哪里加入，
     *          问题是，登录的时候将密码进行加密，然后自动注册的时候会将加密的密码再次进行加密操作,导致报错的
     *
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


    //使用构造器注入，比@Autowired好，一致性和完整性更好了,还可以避免空指针问题
    //final 保证了值不会被修改，虽然不加不也没关系，但是加上去会更好
    private  SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 登录接口
     * @param phone    用户的手机号（账号）
     * @param password 用户的密码
     * @return 返回登录结果
     */
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public R<SysUser> login(String phone, String password,HttpSession session,HttpServletResponse response) throws IOException {
        //从service中调用方法 根据手机号查询单个数据
        SysUser sysUserOne = sysUserService.getSysUserLogin(phone, password);

        //登录成功之后，将用户的id存入session中，以便接下来的操作
        session.setAttribute("sysUser_Id", sysUserOne.getId());

        //跳转到首页
        response.sendRedirect("/index.html");
        //将数据返回给前端
        return R.success(sysUserOne);

    }

    /**
     * 退出接口
     * @return 返回结果
     */
    @ApiOperation("退出接口")
    @GetMapping("/logout")
    private R<String> logout(HttpSession session) throws IOException {
        //查询用户的信息
        String sysUser_Id =(String) session.getAttribute("sysUser_Id");
        SysUser sysUser = sysUserService.getSysUserById(sysUser_Id);
        //清除session的值
        session.removeAttribute("sysUser_Id");

        //如果有值的话，清理session中的id和token
        if (sysUser != null) {
            // 根据用户ID，更新用户的token值为null
            sysUser.setToken("");
            //调用service中 根据用户id进行修改信息
            sysUserService.updateSysUserById(sysUser);
            //跳传到登录页面
//            response.sendRedirect("/login.html");
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
    private R<String> register(@RequestBody SysUser sysUser) throws IOException {
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
