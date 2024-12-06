/*
package com.itheima.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet("/checkoutServlet")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收购物车页面已经被勾选的参数
        String[] selectedItems = req.getParameterValues("selected");

       */
/* //测试是否已经成功接收到勾选的复选框的数据
        // 获取当前时间
        Date currentDate = new Date();

        // 定义时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String submitTime = sdf.format(currentDate);
        System.out.println("提交时间是:"+submitTime);

        if (selectedItems != null && selectedItems.length > 0) {
            for (String selectedItem : selectedItems) {
                System.out.print(req.getParameter("quantity_"+selectedItem)+" ");
                System.out.print(req.getParameter("price_"+selectedItem)+" ");
                System.out.println(req.getParameter("totalPrice_"+selectedItem)+" ");
            }
            System.out.println(selectedItems.length);
        }else {
            System.out.println("没有选择任何商品。");
        }*//*

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }


}
*/

package com.itheima.web;

import com.itheima.pojo.OrderItem;
import com.itheima.service.CartService;
import com.itheima.service.OrderItemService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/checkoutServlet")
public class CheckoutServlet extends HttpServlet {

    private OrderItemService orderItemService = new OrderItemService();
    private CartService cartservice = new CartService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] selectedItems = req.getParameterValues("selected");
        if (selectedItems != null && selectedItems.length > 0) {
            //从会话获取用户ID
            Integer userId = (Integer) req.getSession().getAttribute("userId");
            if (userId != null) {
                //从表单接收参数
                for (String selectedItem : selectedItems) {
                    //删除购物车中对应的购物车项
                    cartservice.deleteCartItemById(Integer.parseInt(selectedItem));
                    Integer quantity = Integer.parseInt(req.getParameter("quantity_" + selectedItem));
                    double totalPrice = Double.parseDouble(req.getParameter("totalPrice_" + selectedItem));
                    OrderItem orderItem = new OrderItem();
                    orderItem.setUserId(userId);
                    orderItem.setBrandId(Integer.parseInt(selectedItem));
                    orderItem.setQuantity(quantity);
                    orderItem.setTotalPrice(totalPrice);
                    orderItem.setStat(1);
                    orderItem.setLogistics(0);
                    orderItem.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    orderItemService.addOrderItem(orderItem);
                }
                resp.sendRedirect("/brand-demo/orderConfirmationServlet");
            } else {
                resp.sendRedirect("/login.jsp");
            }
        } else {
            resp.sendRedirect("/cart.jsp");
        }

        /*// 接收购物车页面已勾选的参数
        String[] selectedItems = req.getParameterValues("selected");

        if (selectedItems != null && selectedItems.length > 0) {
            // 获取用户ID和当前时间
            // 从会话中获取用户 ID
            int userId = (int) req.getSession().getAttribute("userId");// 假设用户ID保存在session中
            Date currentDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String submitTime = sdf.format(currentDate);

            // 遍历所有被选中的商品
            for (String selectedItem : selectedItems) {
                // 获取商品的数量、价格和总价
                Integer quantity = Integer.parseInt(req.getParameter("quantity_" + selectedItem));
                //Integer price = Integer.parseInt(req.getParameter("price_" + selectedItem));
                double totalPrice = Double.parseDouble(req.getParameter("totalPrice_" + selectedItem));

                // 创建 OrderItem 对象
                OrderItem orderItem = new OrderItem();
                orderItem.setUserId(userId);
                orderItem.setCreateTime(submitTime);
                orderItem.setBrandId(Integer.parseInt(selectedItem));
                orderItem.setQuantity(quantity);
                orderItem.setTotalPrice(totalPrice);
                orderItem.setStat(1);  // 假设1代表正常状态
                orderItem.setLogistics(0); // 假设0代表未发货

                // 保存订单项到数据库
                orderItemService.addOrderItem(orderItem);
            }

            // 保存成功后，跳转到订单确认页面
            resp.sendRedirect("/brand-demo/orderConfirmation.jsp");
        } else {
            // 如果没有选择商品，返回购物车页面
            resp.sendRedirect("/cart.jsp");
        }*/
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

