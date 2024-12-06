package com.itheima.web;

import com.itheima.pojo.User;
import com.itheima.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/adminOrderListServlet")
public class AdminOrderListServlet extends HttpServlet {
    private UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从数据库中获取用户和订单数据

        List<User> users = userService.getAllUsers();

        // 将用户信息存储到 request 对象中
        request.setAttribute("users", users);

        // 转发到 adminOrderList.jsp
        request.getRequestDispatcher("/adminOrderList.jsp").forward(request, response);
    }
}
