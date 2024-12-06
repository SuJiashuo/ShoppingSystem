package com.itheima.web;

import com.itheima.pojo.OrderItem;
import com.itheima.service.BrandService;
import com.itheima.service.OrderItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/cancelOrderServlet")
public class CancelOrderServlet extends HttpServlet {

    private/* final */OrderItemService orderItemService = new OrderItemService();
    private BrandService brandService = new BrandService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 获取订单时间
        String orderTime = request.getParameter("orderTime");

        // 获取当前登录用户ID
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("/login.jsp"); // 如果用户未登录，重定向到登录页面
            return;
        }

        try {

            //增加tb_brand表库存
            // 获取订单项
            List<OrderItem> orderItems = orderItemService.selectOrderItemsByTimeAndUser(orderTime, userId);
            // 恢复库存
            for (OrderItem orderItem : orderItems) {
                //只有已付款的订单取消后会更新库存
                if(orderItem.getStat()==1) {
                   // System.out.println(orderItem.getBrandId() + ":" + orderItem.getQuantity());
                    brandService.restoreStock(orderItem.getBrandId(), orderItem.getQuantity());
                    //System.out.println(orderItem.getBrandId() + ":" + orderItem.getQuantity());
                    //System.out.println();
                }
            }
            // 删除订单项
            orderItemService.deleteOrderItemsByTimeAndUser(orderTime, userId);

            // 设置成功提示消息
            request.setAttribute("successMessage", "取消订单成功");

            // 重定向到显示订单的页面
            request.getRequestDispatcher("/viewOrdersSevlet").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("successMessage", "取消订单失败");
            request.getRequestDispatcher("/viewOrdersSevlet").forward(request, response);
        }
    }
}


/*
@WebServlet("/cancelOrderServlet")
public class CancelOrderServlet extends HttpServlet {

    private final OrderItemService orderItemService = new OrderItemService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取订单时间
        String orderTime = request.getParameter("orderTime");

        // 获取当前登录用户ID
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("/login.jsp"); // 如果用户未登录，重定向到登录页面
            return;
        }

        try {
            // 删除订单项
            orderItemService.deleteOrderItemsByTimeAndUser(orderTime, userId);

            // 设置成功提示消息
            request.setAttribute("successMessage", "取消订单成功");

            // 重定向到显示订单的页面
            request.getRequestDispatcher("/viewOrdersSevlet").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("successMessage", "取消订单失败");
            request.getRequestDispatcher("/viewOrdersSevlet").forward(request, response);
        }
    }
}
*/
