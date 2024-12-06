package com.itheima.service;

import com.itheima.mapper.BrandMapper;
import com.itheima.mapper.OrderItemMapper;
import com.itheima.pojo.OrderItem;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.util.Map;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class OrderItemService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    //添加订单项
    public void addOrderItem(OrderItem orderItem) {
        try (SqlSession session = factory.openSession()) {
            OrderItemMapper orderItemMapper = session.getMapper(OrderItemMapper.class);
            orderItemMapper.addOrderItem(orderItem);
            session.commit();  // 提交事务
        }
    }

    //查询订单项
    public List<OrderItem> getOrderItemsByUserId(Integer userId) {
        try (SqlSession session = factory.openSession()) {
            OrderItemMapper orderItemMapper = session.getMapper(OrderItemMapper.class);
            return orderItemMapper.getOrderItemsByUserId(userId);
        }
    }


    public List<OrderItem> getLatestOrderItemsByUserId(Integer userId) {
        try (SqlSession session = factory.openSession()) {
            OrderItemMapper orderItemMapper = session.getMapper(OrderItemMapper.class);
            return orderItemMapper.getLatestOrderItemsByUserId(userId);
        }
    }

    //更新订单发货状态
    public void updateOrderItemLogistics(Integer orderItemId, Integer logistics) {
        try (SqlSession session = factory.openSession()) {
            OrderItemMapper orderItemMapper = session.getMapper(OrderItemMapper.class);
            orderItemMapper.updateOrderItemLogistics(orderItemId, logistics); // 更新订单项的状态
            session.commit();
        }
    }

    //更新订单付款状态
    public void updateOrderItemStatus(Integer orderItemId, Integer status) {
        try (SqlSession session = factory.openSession()) {
            OrderItemMapper orderItemMapper = session.getMapper(OrderItemMapper.class);
            orderItemMapper.updateOrderItemStatus(orderItemId, status); // 更新订单项的状态
            session.commit();
        }
    }

    //按照时间给订单项分组
    public List<OrderItem> getOrderItemsSortedByCreateTime(Integer userId) {
        try (SqlSession session = factory.openSession()) {
            OrderItemMapper orderItemMapper = session.getMapper(OrderItemMapper.class);
            return orderItemMapper.getOrderItemsSortedByCreateTime(userId);
        }
    }


    //删除订单功能
    public void deleteOrderItemsByTimeAndUser(String orderTime, Integer userId) {
        try (SqlSession session = factory.openSession()) {
            OrderItemMapper orderItemMapper = session.getMapper(OrderItemMapper.class);
            orderItemMapper.deleteOrderItemsByTimeAndUser(orderTime, userId);
            session.commit();  // 确保事务提交
        }
    }


    //根据时间和用户 ID 查询订单项
    public List<OrderItem> selectOrderItemsByTimeAndUser(String orderTime, Integer userId) {
        try (SqlSession session = factory.openSession()) {
            OrderItemMapper orderItemMapper = session.getMapper(OrderItemMapper.class);
            List<OrderItem> orderItems = orderItemMapper.selectOrderItemsByTimeAndUser(orderTime, userId);
            return orderItems;
        }
    }

   /* //2024.11.19
    public List<Map<String, Object>> getSalesReport() {
        SqlSession sqlSession = factory.openSession();
        OrderItemMapper orderItemMapper = sqlSession.getMapper(OrderItemMapper.class);
        return orderItemMapper.getSalesReport();
    }*/

    // 获取销售报表，按销售额排序
    public List<Map<String, Object>> getSalesReportSortedBySales() {
        try (SqlSession session = factory.openSession()) {
            OrderItemMapper orderItemMapper = session.getMapper(OrderItemMapper.class);
            return orderItemMapper.getSalesReportSortedBySales();
        }
    }

    // 获取销售报表，按销售总量排序
    public List<Map<String, Object>> getSalesReportSortedByQuantity() {
        try (SqlSession session = factory.openSession()) {
            OrderItemMapper orderItemMapper = session.getMapper(OrderItemMapper.class);
            return orderItemMapper.getSalesReportSortedByQuantity();
        }
    }

    // 获取默认的销售报表（按商品ID排序）
    public List<Map<String, Object>> getSalesReport() {
        try (SqlSession session = factory.openSession()) {
            OrderItemMapper orderItemMapper = session.getMapper(OrderItemMapper.class);
            return orderItemMapper.getSalesReport();
        }
    }


//    //根据时间和用户 ID 查询订单项
//    public List<OrderItem> getOrderItemsByTimeAndUser(String orderTime, Integer userId) {
//        SqlSession session = factory.openSession();
//        OrderItemMapper orderItemMapper = session.getMapper(OrderItemMapper.class);
//        return orderItemMapper.getOrderItemsSortedByCreateTime(userId)
//                .stream()
//                .filter(orderItem -> orderItem.getCreateTime().equals(orderTime))
//                .collect(Collectors.toList());
//    }


}
