package com.itheima.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//登录验证的过滤器
@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        //判断访问的资源路径是否和登录注册有关
        String[]urls={"/login.jsp","register.jsp","/css/","/imgs/","/loginServlet","/registerServlet","/checkCodeServlet"};
        //获取当前访问的资源路径
        String url = req.getRequestURL().toString();
        
        //循环判断
        for (String u : urls) {
            if(url.contains(u)){
                //找到了,放行
                filterChain.doFilter(servletRequest, servletResponse);

                //放行之后,后面的代码不要再执行
                return;
            }
        }

        //1.判断session中是否有user
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");

        //2.判断user是否为null
        if (user != null) {
            //用户登录过
            //放行
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            //没有登录,存储提示信息,跳转到登录页面
            req.setAttribute("login_msg","您尚未登录!");
            req.getRequestDispatcher("/login.jsp").forward(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
