package com.itheima.service;


import com.itheima.mapper.CartMapper;
import com.itheima.pojo.Cart;
import com.itheima.pojo.CartItem;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class CartService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    public List<Cart> selectAllCart() {
        //调用查询所有的方法

        //2.获取SqlSession
        SqlSession sqlSession = factory.openSession();

        //3.获取mapper
        CartMapper mapper = sqlSession.getMapper(CartMapper.class);

        //4.调用方法,查询,不需要提交事务
        List<Cart> carts = mapper.selectAllCart();
        System.out.println(carts);

        sqlSession.close();
        return carts;

    }

    /*public Integer checkBrandIdExists(Cart cart) {
        System.out.println("Checking existence for User ID: " + cart.getUserId() + " Brand ID: " + cart.getBrandId());

        SqlSession sqlSession = factory.openSession();
        CartMapper mapper = sqlSession.getMapper(CartMapper.class);

        Integer count = mapper.checkBrandIdExists(cart.getUserId(), cart.getBrandId());
        sqlSession.close();
        return count;
    }
*/



    public void updateCart(Cart cart) {
        // 2. 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        // 3. 获取mapper
        CartMapper mapper = sqlSession.getMapper(CartMapper.class);

        // 4. 检查brand_id是否存在
        //System.out.println(cart.getUserId()+" "+ cart.getBrandId());
        //int count = mapper.checkBrandIdExists(cart.getUserId(), cart.getBrandId());
       /* System.out.println(cart.getUserId().getClass().getSimpleName()+" "+ cart.getBrandId().getClass().getSimpleName());
        System.out.println(count);*/

        int i = mapper.updateCartQuantity(cart.getUserId(), cart.getBrandId(), cart.getQuantity());

        // 如果没有记录，添加购物车
        if (i > 0) {
            //修改成功
        }else {
            mapper.addCart(cart);
        }
        sqlSession.commit();
         // 提交事务
        sqlSession.close();  // 关闭会话
    }


    //展示购物车页面
    public List<CartItem> selectCartItemsByUserIdAndBrandName(int userId, String brandName) {
        //2.获取SqlSession
        SqlSession sqlSession = factory.openSession();

        //3.获取mapper
        CartMapper mapper = sqlSession.getMapper(CartMapper.class);

        List<CartItem> cartItems=new ArrayList<CartItem>();
        //4.调用方法
        //判断字符串为空、为空字符串或者只包含空白字符
        if (StringUtils.isBlank(brandName)){
            //为空时,查询所有
            cartItems = mapper.selectCartItemsByUserId(userId);
        }else{
            cartItems=mapper.selectCartItemsByUserName(userId,brandName);
        }

        sqlSession.close();

        return cartItems;
    }

    //删除购物车商品
    public void deleteCartItemById(int id) {
        //2.获取SqlSession
        SqlSession sqlSession = factory.openSession();

        //3.获取mapper
        CartMapper mapper = sqlSession.getMapper(CartMapper.class);

        //4.调用方法
       mapper.deleteCartItemById(id);

       sqlSession.commit();

        sqlSession.close();
    }

}