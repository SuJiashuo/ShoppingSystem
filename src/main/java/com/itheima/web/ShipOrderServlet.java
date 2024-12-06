package com.itheima.web;

import com.itheima.pojo.OrderItem;
import com.itheima.service.BrandService;
import com.itheima.service.MailService;
import com.itheima.service.OrderItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/shipOrderServlet")
public class ShipOrderServlet extends HttpServlet {
    private OrderItemService orderItemService = new OrderItemService();
    private MailService mailService = new MailService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Integer userId = (Integer) req.getSession().getAttribute("userId");*/
        String orderTime = req.getParameter("orderTime");
        Integer userId = Integer.valueOf(req.getParameter("userId"));
        System.out.println("用户名是:"+userId);
        if (userId != null && orderTime != null) {
            // 查询该时间段的所有订单项
            List<OrderItem> orderItems = orderItemService.getOrderItemsSortedByCreateTime(userId);

            boolean success = true; // 标记发货是否成功

            try {
                for (OrderItem orderItem : orderItems) {
                    if (orderItem.getCreateTime().equals(orderTime) && orderItem.getLogistics() == 0) {
                        // 更新状态为已付款
                        System.out.println(orderItem.getLogistics());
                        orderItemService.updateOrderItemLogistics(orderItem.getId(), 1);
                        System.out.println(orderItem.getLogistics());
                    }else{
                        //已经付款过
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                success = false;
            }
            System.out.println(success);
            if (success) {
                // 发送成功付款邮件
                String userEmail = "2939346806@qq.com"; // 从 Session 或数据库获取用户邮箱
                String subject = "订单已经发货";
                String body = "您于"+orderTime+"购买的的订单已成功发货！感谢您的购买。";

                mailService.sendEmail(userEmail, subject, body);

                req.setAttribute("message", "发货成功！");
                req.getRequestDispatcher("/sendorderSuccess.jsp").forward(req, resp);
            } else {
                req.setAttribute("message", "发货失败！");
                req.getRequestDispatcher("/sendorderSuccess.jsp").forward(req, resp);
            }
        } else {
            resp.sendRedirect("/login.jsp"); // 未登录
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
