package com.itheima.web;

import com.itheima.pojo.Cart;
import com.itheima.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cartAddServlet")
public class CartAddServlet extends HttpServlet {
    private CartService service = new CartService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理post请求的乱码问题
        req.setCharacterEncoding("UTF-8");

        //1.接收表单的数据,封装为一个cart对象
        req.getParameter("userId");
        req.getParameter("brandId");
        req.getParameter("quantity");

        //封装为cart对象
        Cart cart = new Cart();
        cart.setUserId(Integer.parseInt(req.getParameter("userId")));
        cart.setBrandId(Integer.parseInt(req.getParameter("brandId")));
        cart.setQuantity(Integer.parseInt(req.getParameter("quantity")));

        //2.调用service完成添加
        service.updateCart(cart);

        // 在添加完成后，通过重定向返回到 selectAllServlet 页面
        // 这里可以传递添加成功的消息
        req.getSession().setAttribute("addSuccessMessage", "添加成功!");
        resp.sendRedirect(req.getContextPath() + "/selectAllServlet");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
