package com.itheima.web;

import com.itheima.pojo.OrderItem;
import com.itheima.service.OrderItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/viewOrdersSevlet")
public class ViewOrdersServlet extends HttpServlet {
    private OrderItemService orderItemService = new OrderItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("/login"); // 如果用户未登录，重定向到登录页面
            return;
        }

        // 获取按时间排序的订单项
        List<OrderItem> orderItems = orderItemService.getOrderItemsSortedByCreateTime(userId);

        // 按createTime分组
        Map<String, List<OrderItem>> groupedOrders = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItem::getCreateTime));

        // 对分组结果按时间降序排序
        LinkedHashMap<String, List<OrderItem>> sortedGroupedOrders = groupedOrders.entrySet().stream()
                .sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey())) // 按时间降序排序
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new // 保持插入顺序
                ));

        // 获取 request 中的 successMessage
        String successMessage = (String) request.getAttribute("successMessage");
        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
        }

        // 将排序后的分组数据传递到前端
        request.setAttribute("groupedOrders", sortedGroupedOrders);

        // 转发到订单展示页面
        request.getRequestDispatcher("/orderList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}






/*
package com.itheima.web;

import com.itheima.pojo.OrderItem;
import com.itheima.service.OrderItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/viewOrdersSevlet")
public class ViewOrdersServlet extends HttpServlet {
    private OrderItemService orderItemService = new OrderItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("/login"); // 如果用户未登录，重定向到登录页面
            return;
        }

        // 获取按时间排序的订单项
        List<OrderItem> orderItems = orderItemService.getOrderItemsSortedByCreateTime(userId);

        // 按createTime分组
        Map<String, List<OrderItem>> groupedOrders = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItem::getCreateTime));

        // 对分组结果按时间降序排序
        LinkedHashMap<String, List<OrderItem>> sortedGroupedOrders = groupedOrders.entrySet().stream()
                .sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey())) // 按时间降序排序
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new // 保持插入顺序
                ));

        // 将排序后的分组数据传递到前端
        request.setAttribute("groupedOrders", sortedGroupedOrders);

        // 转发到订单展示页面
        request.getRequestDispatcher("/orderList.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
*/
