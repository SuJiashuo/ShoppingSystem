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

@WebServlet("/submitOrderServlet")
public class SubmitOrderServlet extends HttpServlet {
    private OrderItemService orderItemService = new OrderItemService();
    private MailService mailService = new MailService(); // 用于发送邮件
    private BrandService brandService = new BrandService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");


        if (userId != null) {
            // 获取该用户的订单项
            List<OrderItem> orderItems = orderItemService.getLatestOrderItemsByUserId(userId);
            String time = null;

            boolean success = true; // 标记订单处理是否成功
            try {
                // 开始事务（在 Service 层实现）
                for (OrderItem orderItem : orderItems) {
                    // 更新订单状态为已提交
                    orderItemService.updateOrderItemStatus(orderItem.getId(), 1);
                    time = orderItem.getCreateTime();

                    // 减少库存,订单只有提交后已付款,才会更新库存
                    boolean stockUpdated = brandService.reduceStock(orderItem.getBrandId(), orderItem.getQuantity());
                    if (!stockUpdated) {
                        throw new RuntimeException("库存不足，商品 ID：" + orderItem.getBrandId());
                    }
                }
                // 如果全部成功，则提交事务
            } catch (Exception e) {
                e.printStackTrace();
                success = false;
            }

            if (success) {
                // 发送邮件通知用户购买成功
                String userEmail = "2939346806@qq.com"; // 从 Session 或数据库获取用户的邮箱
                String subject = "购买成功";
                String body = "您的订单已成功提交！感谢您的购买。\n\n购买时间：" + time;

                mailService.sendEmail(userEmail, subject, body); // 发送邮件

                // 跳转到订单提交成功页面
                req.setAttribute("message", "订单提交成功，邮件已发送至您的邮箱。");
                req.getRequestDispatcher("/orderSuccess.jsp").forward(req, resp);
            } else {
                // 处理失败，跳转到错误页面
                req.setAttribute("message", "订单提交失败，请稍后重试。");
                req.getRequestDispatcher("/orderSuccess.jsp").forward(req, resp);
            }
        } else {
            resp.sendRedirect("/login.jsp"); // 未登录，跳转到登录页面
        }
    }
/*
        if (userId != null) {
            // 获取该用户的订单项
            List<OrderItem> orderItems = orderItemService.getLatestOrderItemsByUserId(userId);
            String time=null;

            boolean success = true; // 标记订单处理是否成功
            // 更新所有订单项的状态为1（表示已提交）
            for (OrderItem orderItem : orderItems) {
                orderItemService.updateOrderItemStatus(orderItem.getId(), 1);
                time=orderItem.getCreateTime();// 1表示已提交
            }

            // 发送邮件通知用户购买成功
            String userEmail = "2939346806@qq.com"; // 从Session或者数据库获取用户的邮箱
            String subject = "购买成功";
            String body = "您的订单已成功提交！感谢您的购买。\n\n购买时间："+time;

            mailService.sendEmail(userEmail, subject, body); // 发送邮件

            // 跳转到订单提交成功页面
            req.setAttribute("message", "订单提交成功，邮件已发送至您的邮箱。");
            req.getRequestDispatcher("/orderSuccess.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/login.jsp"); // 未登录，跳转到登录页面
        }*/
    }

