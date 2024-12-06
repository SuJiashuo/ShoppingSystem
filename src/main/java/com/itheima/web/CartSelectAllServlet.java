package com.itheima.web;

import com.itheima.pojo.Cart;
import com.itheima.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cartSelectAllServlet")
public class CartSelectAllServlet extends HttpServlet {
    private CartService cartService = new CartService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.调用cartService完成查询
        List<Cart> carts = cartService.selectAllCart();

        //2.将查到的对象保存在req域中
        req.setAttribute("carts", carts);

        //3.转发到cart.jsp
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
