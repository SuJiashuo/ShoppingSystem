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

@WebServlet("/userOrderDetailServlet")
public class UserOrderDetailServlet extends HttpServlet {
    private final OrderItemService orderItemService = new OrderItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从请求中获取用户 ID
        String userIdParam = request.getParameter("userId");
        String username = request.getParameter("username");
        System.out.println(username);
        System.out.println(userIdParam);

        if (userIdParam == null || userIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "用户 ID 不能为空");
            return;
        }

        Integer userId;
        try {
            userId = Integer.parseInt(userIdParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的用户 ID");
            return;
        }

        // 按时间获取并分组订单项
        List<OrderItem> orderItems = orderItemService.getOrderItemsSortedByCreateTime(userId);
        Map<String, List<OrderItem>> groupedOrders = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItem::getCreateTime));

        LinkedHashMap<String, List<OrderItem>> sortedGroupedOrders = groupedOrders.entrySet().stream()
                .sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey())) // 时间降序
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new // 保持顺序
                ));

        // 设置属性并转发到 JSP
        request.setAttribute("userId", userId);
        request.setAttribute("username", username);
        request.setAttribute("groupedOrders", sortedGroupedOrders);
        request.getRequestDispatcher("/userOrderDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
