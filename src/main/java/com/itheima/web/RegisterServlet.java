package com.itheima.web;

import com.itheima.pojo.User;
import com.itheima.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    private UserService service = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取用户名和密码
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //封装user对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        //获取用户输入的验证码
        String checkCode = req.getParameter("checkCode");

        //获取程序生成的验证码
        HttpSession session = req.getSession();
        String checkCodeGen = (String) session.getAttribute("checkCodeGen");

        //判断验证码是否正确 忽略大小写
        if(!checkCodeGen.equalsIgnoreCase(checkCode)){
            //不一样,不允许注册
            //注册失败,跳转到注册界面
            req.setAttribute("register_msg","验证码错误");
            req.getRequestDispatcher("/register.jsp").forward(req,resp);

            return;
        }

        //2.调用service注册
        boolean flag = service.register(user);

        //3.判断注册成功与否
        if(flag){
            //注册成功,跳转到登录界面
            req.setAttribute("register_msg","注册成功,请登录");
            //转发
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }else{
            //注册失败,跳转到注册界面
            req.setAttribute("register_msg","用户名已存在或为空");
            req.getRequestDispatcher("/register.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
