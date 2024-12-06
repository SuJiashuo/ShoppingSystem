package com.itheima.web;

import com.itheima.pojo.Cart;
import com.itheima.pojo.CartItem;
import com.itheima.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/updateCartQuantityServlet")
public class UpdateCartQuantityServlet extends HttpServlet {
    private CartService cartService = new CartService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
    /*    request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");*/

        // 从表单接收数据
        //从会话中获取用户名
        Integer userId = (Integer) session.getAttribute("userId");
        //从表单接收商品名称,点击的数量变化,库存
        int brandId = Integer.parseInt(request.getParameter("brandId"));
        int change = Integer.parseInt(request.getParameter("change"));
        int number = Integer.parseInt(request.getParameter("number"));
        String brandName = request.getParameter("searchQuery1");
        byte[] bytes = brandName.getBytes(StandardCharsets.ISO_8859_1);
        //3.2字节数组解码
        brandName = new String(bytes, StandardCharsets.UTF_8);


      /*  System.out.println("brandId: " + brandId);
        System.out.println("change: " + change);
        System.out.println("number: " + number);
        System.out.println("brandName: " + brandName);
*/
        // 查询当前购物车中该商品的数量
        List<CartItem> cartItems = cartService.selectCartItemsByUserIdAndBrandName(userId, brandName);
        int currentQuantity = 0;

        // 找到当前商品的数量
        for (CartItem item : cartItems) {
            if (item.getBrandId() == brandId) {
                currentQuantity = item.getQuantity();
                break;
            }
        }

        // 计算新的数量
        int newQuantity = currentQuantity + change;

        // 确保数量不小于 1
        if (newQuantity < 1) {
            request.setAttribute("errorMessage", "该商品不能再少了哦");
            request.getRequestDispatcher("/selectCartItemsByUserIdServlet").forward(request, response);
            return;
        }

        // 确保最大添加数量不能大于库存
        if (newQuantity > number) {
            change = number - newQuantity + 1;
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setBrandId(brandId);
            cart.setQuantity(change);
            cartService.updateCart(cart);
            request.setAttribute("errorMessage", "该商品添加量已达最大库存了哦");
            request.getRequestDispatcher("/selectCartItemsByUserIdServlet").forward(request, response);
            return;
        }

        // 创建 Cart 对象，更新数量
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setBrandId(brandId);
        cart.setQuantity(change);
        cartService.updateCart(cart);

        // 重定向回查询页面，并传递 searchQuery1 参数
        String searchQuery = request.getParameter("searchQuery1");
        // 重定向回查询页面，并传递 searchQuery1 参数
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/selectCartItemsByUserIdServlet?searchQuery1=" +searchQuery);
        } else {
            System.out.println("错啦错啦,商品名称为空");
            response.sendRedirect(request.getContextPath() + "/selectCartItemsByUserIdServlet");
        }
    }
}