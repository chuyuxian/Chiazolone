package com.springboot.filter;

import com.springboot.common.BaseContext;
import com.springboot.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.info("拦截到请求：{}",request.getRequestURI());

        //1、获取本次请求的URL
        String requestURI = request.getRequestURI();

        //定义不需要处理的请求路径,
        String[] urls = new String[]{
                "/login.html",
                "/register.html",
                "/css/**",
                "/js/**",
                "/img/**",
                "/api/SysUser/**",
                "/api/SysUser/register"
        };

        //2、判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        //3、如果不需要处理直接放行
        if (check) {
            filterChain.doFilter(request,response);
            return;
        }

        //4-1、判断后端系统员工用户登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("sysUser_Id") != null) {

            String empId =(String) request.getSession().getAttribute("sysUser_Id");
            log.info("用户已登录，用户id为{}",empId);
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request,response);
            return;
        }
        //4-2、判断前端用户登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("user") != null) {

            String empId =(String) request.getSession().getAttribute("user");
            log.info("用户已登录，用户id为{}",empId);
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request,response);
            return;
        }

        //5、如果未登录则返回登录页面,
        log.info("用户未登录");
        response.sendRedirect("/login.html");
        return;
    }

    /**
     * 路径匹配，检查当前请求是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI) {
        for (String url : urls) {
            //match:匹配的意思
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
