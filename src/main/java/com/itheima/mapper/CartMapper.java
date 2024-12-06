package com.itheima.mapper;

import com.itheima.pojo.Cart;
import com.itheima.pojo.CartItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CartMapper {


        //查询购物车中所有的数据
        @Select("SELECT * FROM cart;")
        @ResultMap("cartResultMap")
        List<Cart> selectAllCart();

        //添加购物车
        @Insert("INSERT INTO cart(user_id, brand_id, quantity) VALUES(#{userId}, #{brandId}, #{quantity})")
        @ResultMap("cartResultMap")
        void addCart(Cart cart);

        //根据用户名和商品名称查询购物车
        @Select("SELECT c.id AS cart_id, " +
                "       c.user_id, " +
                "       c.brand_id, " +
                "       c.quantity," +
                "       b.number, " +
                "       b.brand_name, " +
                "       b.price " +
                "FROM cart c " +
                "JOIN tb_brand b ON c.brand_id = b.id " +
                "WHERE c.user_id = #{userId} And brand_name LIKE CONCAT('%', #{brandName}, '%')")
        @ResultMap("cartItemResultMap")
        List<CartItem> selectCartItemsByUserName(@Param("userId") int userId, @Param("brandName") String brandName);

        //根据用户名查询购物车
        @Select("SELECT c.id AS cart_id, " +
                "       c.user_id, " +
                "       c.brand_id, " +
                "       c.quantity," +
                "       b.number, " +
                "       b.brand_name, " +
                "       b.price " +
                "FROM cart c " +
                "JOIN tb_brand b ON c.brand_id = b.id " +
                "WHERE c.user_id = #{userId}")
        @ResultMap("cartItemResultMap")
        List<CartItem> selectCartItemsByUserId(int userId);


        //更新购物车数量,返回受影响的行数
        @Update("UPDATE cart SET quantity = quantity + #{quantity} WHERE user_id = #{userId} AND brand_id = #{brandId}")
        @ResultMap("cartResultMap")
        int updateCartQuantity(@Param("userId") int userId, @Param("brandId") int brandId, @Param("quantity") int quantity);

        @Delete("DELETE FROM cart WHERE cart.brand_id = #{brandId}")
        @ResultMap("cartResultMap")
        void deleteCartItemById(@Param("brandId") int brandId);

}