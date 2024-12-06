package com.itheima.web;


import com.itheima.mapper.OrderItemMapper;
import com.itheima.service.OrderItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@WebServlet("/salesReportServlet")
public class SalesReportServlet extends HttpServlet {
    private OrderItemService orderItemService = new OrderItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取排序参数
        String sortOrder = request.getParameter("sortOrder");

        // 获取销售统计数据
        List<Map<String, Object>> salesReports;
        if (sortOrder == null || sortOrder.equals("default")) {
            // 默认按商品ID排序
            salesReports = orderItemService.getSalesReport();
        } else if (sortOrder.equals("totalSales")) {
            // 按销售额排序
            salesReports = orderItemService.getSalesReportSortedBySales();
        } else if (sortOrder.equals("totalQuantity")) {
            // 按销售总量排序
            salesReports = orderItemService.getSalesReportSortedByQuantity();
        } else {
            // 默认排序
            salesReports = orderItemService.getSalesReport();
        }

        // 将数据传递给前端
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("salesReports", salesReports);

        // 跳转到销售报表页面
        request.getRequestDispatcher("/salesReport.jsp").forward(request, response);
    }
}

/*
@WebServlet("/salesReportServlet")
public class SalesReportServlet extends HttpServlet {
    private OrderItemService orderItemService=new OrderItemService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取销售统计数据
        List<Map<String, Object>> salesReports = orderItemService.getSalesReport();

        // 将数据传递给前端
        request.setAttribute("salesReports", salesReports);
        // 跳转到销售报表页面
        request.getRequestDispatcher("/salesReport.jsp").forward(request, response);
    }
}
*/
