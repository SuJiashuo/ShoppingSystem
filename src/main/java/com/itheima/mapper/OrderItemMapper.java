package com.itheima.mapper;

import com.itheima.pojo.Cart;
import com.itheima.pojo.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface OrderItemMapper {
    //添加订单项
    @Insert("INSERT INTO orderitem(user_id, create_time, brand_id,quantity,total_price) VALUES(#{userId}, #{createTime}, #{brandId},#{quantity},#{totalPrice})")
    @ResultMap("orderItemResultMap")
    void addOrderItem(OrderItem orderItem);

    //查询
    @Select("SELECT * FROM orderitem WHERE user_id = #{userId}")
    @ResultMap("orderItemResultMap")
    List<OrderItem> getOrderItemsByUserId(Integer userId);


    //查询最新添加的订单
    @Select("SELECT *FROM orderitem WHERE user_Id=#{userId} AND create_time=(SELECT create_time FROM OrderItem WHERE user_Id =#{userId} ORDER BY create_time DESC LIMIT 1)")
    @ResultMap("orderItemResultMap")
    List<OrderItem> getLatestOrderItemsByUserId(Integer userId);

    //更新商品付款状态
    @Update("UPDATE orderitem SET stat = #{status} WHERE id = #{orderItemId}")
    @ResultMap("orderItemResultMap")
    void updateOrderItemStatus(@Param("orderItemId") Integer orderItemId, @Param("status") Integer status);

    //更新商品发货状态
    @Update("UPDATE orderitem SET logistics = #{logistics} WHERE id = #{orderItemId}")
    @ResultMap("orderItemResultMap")
    void updateOrderItemLogistics(@Param("orderItemId") Integer orderItemId, @Param("logistics") Integer logistics);


    //按照时间分组查询订单项
    @Select("SELECT * FROM orderitem WHERE user_id = #{userId} ORDER BY create_time DESC")
    @ResultMap("orderItemResultMap")
    List<OrderItem> getOrderItemsSortedByCreateTime(Integer userId);

    //根据用户名和时间删除订单项
    @Delete("DELETE FROM orderitem WHERE create_time = #{orderTime} AND user_id = #{userId}")
    @ResultMap("orderItemResultMap")
    void deleteOrderItemsByTimeAndUser(@Param("orderTime") String orderTime, @Param("userId") Integer userId);

    //根据用户名和时间查询订单项
    @Select("SELECT* FROM orderitem WHERE create_time = #{orderTime} AND user_id = #{userId}")
    @ResultMap("orderItemResultMap")
    List<OrderItem> selectOrderItemsByTimeAndUser(@Param("orderTime") String orderTime, @Param("userId") Integer userId);

/*    //2024.11.19
    // 在 OrderItemMapper 接口中，定义查询方法
    @Select("SELECT brand_id, " +
            "SUM(quantity) AS totalQuantity, " +
            "SUM(total_price) AS totalSales " +
            "FROM orderitem " +
            "GROUP BY brand_id")
    @Results({
            @Result(property = "brandId", column = "brand_id"),
            @Result(property = "totalQuantity", column = "totalQuantity"),
            @Result(property = "totalSales", column = "totalSales")
    })
    List<Map<String, Object>> getSalesReport();*/
// 获取销售报表，按销售额排序
@Select("SELECT brand_id, SUM(quantity) AS totalQuantity, SUM(total_price) AS totalSales " +
        "FROM orderitem " +
        "GROUP BY brand_id " +
        "ORDER BY totalSales DESC")
@Results({
        @Result(property = "brandId", column = "brand_id"),
        @Result(property = "totalQuantity", column = "totalQuantity"),
        @Result(property = "totalSales", column = "totalSales")
})
List<Map<String, Object>> getSalesReportSortedBySales();

    // 获取销售报表，按销售总量排序
    @Select("SELECT brand_id, SUM(quantity) AS totalQuantity, SUM(total_price) AS totalSales " +
            "FROM orderitem " +
            "GROUP BY brand_id " +
            "ORDER BY totalQuantity DESC")
    @Results({
            @Result(property = "brandId", column = "brand_id"),
            @Result(property = "totalQuantity", column = "totalQuantity"),
            @Result(property = "totalSales", column = "totalSales")
    })
    List<Map<String, Object>> getSalesReportSortedByQuantity();

    // 获取默认的销售报表（按商品ID排序）
    @Select("SELECT brand_id, SUM(quantity) AS totalQuantity, SUM(total_price) AS totalSales " +
            "FROM orderitem " +
            "GROUP BY brand_id")
    @Results({
            @Result(property = "brandId", column = "brand_id"),
            @Result(property = "totalQuantity", column = "totalQuantity"),
            @Result(property = "totalSales", column = "totalSales")
    })
    List<Map<String, Object>> getSalesReport();

    /**
     * 查询商品销售统计报表
     * 返回每个品牌的销售数量和总金额
     *
     * @return 销售统计数据
     */

}
