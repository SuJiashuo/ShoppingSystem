package com.itheima.web;

import com.itheima.pojo.Administer;
import com.itheima.pojo.User;
import com.itheima.service.AdministerService;
import com.itheima.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();
    private AdministerService adminService = new AdministerService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取用户名和密码及登录类型,数据是从login.jsp获取的
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String loginType = req.getParameter("loginType");

        //获取复选框数据
        String remember = req.getParameter("remember");
        //判断
        // 根据登录类型进行不同的处理
        //用户登录
        if ("customer".equals(loginType)) {
            //2.调用service方法
            User user = userService.login(username, password);
            if (user!= null) {
                //登录成功,跳转到用户的界面
                //判断用户是否勾选记住我
                if ("1".equals(remember)) {
                    //勾选了,发送cookie

                    //1.创建cookie对象
                    Cookie c_username = new Cookie("username", username);
                    Cookie c_password = new Cookie("password", password);

                    //设置cookie的存活时间,1周
                    c_username.setMaxAge(60 * 60 * 24 * 7);
                    c_password.setMaxAge(60 * 60 * 24 * 7);

                    //2.发送cookie
                    resp.addCookie(c_username);
                    resp.addCookie(c_password);

                }

                /*登录成功的话,会在session里面存入一个user对象*/
                //将登录成功后的User对象,存储到session
           /* HttpSession session = req.getSession();
            session.setAttribute("user", user);*/
                req.getSession().setAttribute("user", user);
                req.getSession().setAttribute("userId", user.getId());

                //使用重定向,因为没有数据要共享
                //虚拟目录使用动态的
                String contextPath = req.getContextPath();
                resp.sendRedirect(contextPath + "/selectAllServlet");
            } else {
                //登录失败

                //存储错误信息到request
                req.setAttribute("login_msg", "用户名或密码错误");

                //跳转到login.jsp,转发
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }


        } else {//管理员登录
            Administer administer = adminService.login(username, password);
            if (administer != null) {
                //登录成功,跳转到查询所有的BrandService
                //判断用户是否勾选记住我
                if ("1".equals(remember)) {
                    //勾选了,发送cookie

                    //1.创建cookie对象
                    Cookie c_username = new Cookie("username", username);
                    Cookie c_password = new Cookie("password", password);

                    //设置cookie的存活时间,1周
                    c_username.setMaxAge(60 * 60 * 24 * 7);
                    c_password.setMaxAge(60 * 60 * 24 * 7);

                    //2.发送cookie
                    resp.addCookie(c_username);
                    resp.addCookie(c_password);

                }

                /*登录成功的话,会在session里面存入一个user对象*/
                //将登录成功后的User对象,存储到session
           /* HttpSession session = req.getSession();
            session.setAttribute("user", user);*/
                req.getSession().setAttribute("user", administer);

                //使用重定向,因为没有数据要共享
                //虚拟目录使用动态的
                String contextPath = req.getContextPath();
                resp.sendRedirect(contextPath + "/selectAllServlet");
            } else {
                //登录失败

                //存储错误信息到request
                req.setAttribute("login_msg", "用户名或密码错误");

                //跳转到login.jsp,转发
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
