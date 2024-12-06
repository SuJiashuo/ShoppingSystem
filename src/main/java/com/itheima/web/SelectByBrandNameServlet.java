/*
package com.itheima.web;

import com.itheima.pojo.Administer;
import com.itheima.pojo.Brand;
import com.itheima.service.BrandService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/selectAllServlet")
public class SelectByBrandNameServlet extends HttpServlet {
    private BrandService service = new BrandService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从表单接收数据
        req.setCharacterEncoding("UTF-8");
        String brandName = req.getParameter("searchQuery");
        // 从 session 中获取添加成功的消息
        HttpSession session = req.getSession();
        // 获取当前会话
        //HttpSession session = req.getSession();
        Object sessionObject = session.getAttribute("user");

        //获取当前用户角色
        String userRole = (sessionObject instanceof Administer) ? "admin" : "customer";
        // 1.调用 BrandService 完成查询，传递用户角色
        List<Brand> brands = service.selectByBrandName(brandName, userRole);
        //1.调用BrandService完成查询
        //List<Brand> brands = service.selectByBrandName(brandName);

        //2.存入resquest域中
        req.setAttribute("brands", brands);


        String addSuccessMessage = (String) session.getAttribute("addSuccessMessage");
        // 如果有消息，则放入请求域中，并清空 session 中的消息
        if (addSuccessMessage != null) {
            req.setAttribute("addSuccessMessage", addSuccessMessage);
            session.removeAttribute("addSuccessMessage");
        }

        //3.转发到brand.jsp和customer.jsp

        // 检查 session 中存储的是管理员还是普通用户，并转发到不同的页面
        if (sessionObject instanceof Administer) {
            // 如果 session 中存储的是 Administer 对象，则转发到管理员页面
            req.getRequestDispatcher("/brand.jsp").forward(req, resp);
        } else {
            // 否则，转发到普通用户页面
            req.getRequestDispatcher("/customer.jsp").forward(req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}







*/





/*

package com.itheima.web;

import com.itheima.pojo.Administer;
import com.itheima.pojo.Brand;
import com.itheima.service.BrandService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/selectAllServlet")
public class SelectByBrandNameServlet extends HttpServlet {
    private BrandService service = new BrandService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从表单接收数据
        req.setCharacterEncoding("UTF-8");
        String brandName = req.getParameter("searchQuery");


        //1.调用BrandService完成查询
        List<Brand> brands = service.selectByBrandName(brandName);

        //2.存入resquest域中
        req.setAttribute("brands", brands);

        // 从 session 中获取添加成功的消息
        HttpSession session = req.getSession();
        String addSuccessMessage = (String) session.getAttribute("addSuccessMessage");
        // 如果有消息，则放入请求域中，并清空 session 中的消息
        if (addSuccessMessage != null) {
            req.setAttribute("addSuccessMessage", addSuccessMessage);
            session.removeAttribute("addSuccessMessage");
        }

        //3.转发到brand.jsp和customer.jsp
        // 获取当前会话
        //HttpSession session = req.getSession();
        Object sessionObject = session.getAttribute("user");

        //获取当前用户角色
        String userRole = (sessionObject instanceof Administer) ? "admin" : "customer";
        // 检查 session 中存储的是管理员还是普通用户，并转发到不同的页面
        if (sessionObject instanceof Administer) {
            // 如果 session 中存储的是 Administer 对象，则转发到管理员页面
            req.getRequestDispatcher("/brand.jsp").forward(req, resp);
        } else {
            // 否则，转发到普通用户页面
            req.getRequestDispatcher("/customer.jsp").forward(req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
*/


package com.itheima.web;

import com.itheima.pojo.Administer;
import com.itheima.pojo.Brand;
import com.itheima.service.BrandService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/selectAllServlet")
public class SelectByBrandNameServlet extends HttpServlet {
    private BrandService service = new BrandService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从表单接收数据
        req.setCharacterEncoding("UTF-8");
        String brandName = req.getParameter("searchQuery");



        // 从 session 中获取添加成功的消息
        HttpSession session = req.getSession();
        String addSuccessMessage = (String) session.getAttribute("addSuccessMessage");
        // 如果有消息，则放入请求域中，并清空 session 中的消息
        if (addSuccessMessage != null) {
            req.setAttribute("addSuccessMessage", addSuccessMessage);
            session.removeAttribute("addSuccessMessage");
        }

        //3.转发到brand.jsp和customer.jsp
        // 获取当前会话
        //HttpSession session = req.getSession();
        Object sessionObject = session.getAttribute("user");

        //获取当前用户角色
        String userRole = (sessionObject instanceof Administer) ? "admin" : "customer";
        //System.out.println(userRole);
        //1.调用BrandService完成查询
        List<Brand> brands = service.selectByBrandName(brandName,userRole);

        //2.存入resquest域中
        req.setAttribute("brands", brands);

        // 检查 session 中存储的是管理员还是普通用户，并转发到不同的页面
        if (sessionObject instanceof Administer) {
            // 如果 session 中存储的是 Administer 对象，则转发到管理员页面
            req.getRequestDispatcher("/brand.jsp").forward(req, resp);
        } else {
            // 否则，转发到普通用户页面
            req.getRequestDispatcher("/customer.jsp").forward(req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

