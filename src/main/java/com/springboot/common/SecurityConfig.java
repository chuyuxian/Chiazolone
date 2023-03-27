//package com.springboot.common;
//
//import com.springboot.service.SysUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * Spring Security 的配置文件
// *      实现拦截器的功能
// */
////@Configuration //标识为配置类
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private SysUserService sysUserService;
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //设置用户名和密码
//        auth.inMemoryAuthentication().withUser("15565139513").password("1234567").roles("zhangsan");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.formLogin(). //设置自定义编写的登录页面
//                        loginPage("/login.html") //登录页面设置,设置自定义登录页面的路径
//                        .defaultSuccessUrl("/index.html").permitAll() //登录成功，跳传路径
//                .and().authorizeRequests()
//                .antMatchers("/login.html","/css/**","/js/**","/img/**",
//                                        "/api/SysUser").permitAll();//设置那些路径可以直接访问，不需要认证
//
//
//        http.authorizeRequests() //设置拦截那些
//                .antMatchers("/**").authenticated(); //拦截所有请求
//
//    }
//}
