package com.itheima.web;

import com.itheima.pojo.OrderItem;
import com.itheima.service.OrderItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
//展示订单
@WebServlet("/orderConfirmationServlet")
public class OrderConfirmationServlet extends HttpServlet {
    private OrderItemService orderItemService = new OrderItemService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        System.out.println(userId);
        if (userId != null) {
            //List<OrderItem> orderItems = orderItemService.getOrderItemsByUserId(userId);

            //获取订单项
            List<OrderItem> orderItems = orderItemService.getLatestOrderItemsByUserId(userId);
           // System.out.println(orderItems);
            req.setAttribute("orderItems", orderItems);
            
            req.getRequestDispatcher("/orderConfirmation.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
