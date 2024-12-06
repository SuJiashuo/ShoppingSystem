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

@WebServlet("/payOrderServlet")
public class PayOrderServlet extends HttpServlet {
    private OrderItemService orderItemService = new OrderItemService();
    private BrandService brandService = new BrandService();
    private MailService mailService = new MailService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        String orderTime = req.getParameter("orderTime");

        if (userId != null && orderTime != null) {
            // 查询该时间段的所有订单项
            List<OrderItem> orderItems = orderItemService.getOrderItemsSortedByCreateTime(userId);

            boolean success = true; // 标记付款是否成功

            try {
                for (OrderItem orderItem : orderItems) {
                    if (orderItem.getCreateTime().equals(orderTime) && orderItem.getStat() == 0) {
                        // 更新状态为已付款
                        orderItemService.updateOrderItemStatus(orderItem.getId(), 1);

                        // 减少库存
                        boolean stockUpdated = brandService.reduceStock(orderItem.getBrandId(), orderItem.getQuantity());
                        if (!stockUpdated) {
                            throw new RuntimeException("库存不足，商品 ID：" + orderItem.getBrandId());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                success = false;
            }

            if (success) {
                // 发送成功付款邮件
                String userEmail = "2939346806@qq.com"; // 从 Session 或数据库获取用户邮箱
                String subject = "订单付款成功";
                String body = "您的订单已成功付款！感谢您的购买。\n\n订单时间：" + orderTime;

                mailService.sendEmail(userEmail, subject, body);

                req.setAttribute("message", "付款成功！");
                req.getRequestDispatcher("/orderSuccess.jsp").forward(req, resp);
            } else {
                req.setAttribute("message", "付款失败，请稍后重试！");
                req.getRequestDispatcher("/orderSuccess.jsp").forward(req, resp);
            }
        } else {
            resp.sendRedirect("/login.jsp"); // 未登录
        }
    }
}
