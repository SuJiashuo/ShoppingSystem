package com.itheima.web;

import com.itheima.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/deleteCartItemServlet")
public class DeleteCartItemServlet extends HttpServlet {
    private CartService service = new CartService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.接收表单的数据
        String id = req.getParameter("brandId");
        //2.调用service查询
        service.deleteCartItemById(Integer.parseInt(id));
        //3.数据存储到resquest域中
        //不需要存储对象

        //4.转发到查询所有jsp
        req.getRequestDispatcher("/selectCartItemsByUserIdServlet").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
