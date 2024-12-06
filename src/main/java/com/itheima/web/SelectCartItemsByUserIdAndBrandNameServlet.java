package com.itheima.web;

import com.itheima.pojo.CartItem;
import com.itheima.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/selectCartItemsByUserIdServlet")
public class SelectCartItemsByUserIdAndBrandNameServlet extends HttpServlet {
    private CartService service = new CartService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.setCharacterEncoding("UTF-8");
        //resp.setContentType("text/html;charset=UTF-8");

        // 从会话中获取用户 ID
        int userId = (int) req.getSession().getAttribute("userId");
        // 获取搜索框输入的商品名称
        String brandName = req.getParameter("searchQuery1");
        if (brandName != null) {
            byte[] bytes = brandName.getBytes(StandardCharsets.ISO_8859_1);
            //3.2字节数组解码
            brandName = new String(bytes, StandardCharsets.UTF_8);
            //System.out.println("brandName: " + brandName);
        }


        // 调用 service 查询
        List<CartItem> cartItems = service.selectCartItemsByUserIdAndBrandName(userId, brandName);

        // 数据存储到 resquest 域中
        req.setAttribute("cartItems", cartItems);
        // 将 searchQuery1 存储到请求域中
        req.setAttribute("searchQuery1", brandName);

        // 转发到 cart.jsp
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}