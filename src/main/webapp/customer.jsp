<%--
  Created by IntelliJ IDEA.
  User: 29393
  Date: 2024/11/5
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Customer Page</title>
    <style>
        table {
            width: 80%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
    <style>
        /* 弹出框的样式 */
        .alert {
            display: none; /* 默认隐藏 */
            position: fixed;
            top: 50%; /* 垂直居中 */
            left: 50%; /* 水平居中 */
            transform: translate(-50%, -50%); /* 偏移自身宽度和高度的50% */
            padding: 15px;
            background-color: #4CAF50; /* 成功的绿色背景 */
            color: white;
            border-radius: 5px;
            z-index: 1000; /* 确保在其他元素之上 */
        }

    </style>
    <script>
        // 页面加载时显示弹出框
        window.onload = function() {
            // 获取消息框元素
            const alertBox = document.getElementById("alertBox");
            // 检查是否有成功消息
            const successMessage = "${addSuccessMessage}";

            if (successMessage) {
                alertBox.innerHTML = successMessage; // 设置弹出框内容
                alertBox.style.display = "block"; // 显示
                setTimeout(function() {
                    alertBox.style.display = "none"; // 一秒后隐藏
                }, 1000); // 1000毫秒
            }
        };
    </script>

    <script>
        // 页面卸载时存储滚动位置
        window.onbeforeunload = function() {
            sessionStorage.setItem('scrollPosition', window.scrollY);
        };

        // 页面加载时恢复滚动位置
        window.onload = function() {
            const scrollPos = sessionStorage.getItem('scrollPosition');
            if (scrollPos) {
                window.scrollTo(0, scrollPos);
                sessionStorage.removeItem('scrollPosition'); // 移除已存储的滚动位置以避免重复加载
            }

            // 显示添加成功的消息
            const alertBox = document.getElementById("alertBox");
            const successMessage = "${addSuccessMessage}";
            if (successMessage) {
                alertBox.innerHTML = successMessage;
                alertBox.style.display = "block";
                setTimeout(function() {
                    alertBox.style.display = "none"; // 一秒后隐藏
                }, 1000); // 1000毫秒
            }
        };
    </script>

</head>
<body>
<h1>
    ${user.username},欢迎您!
</h1>
<div id="alertBox" class="alert"></div>
<a href="${pageContext.request.contextPath}/selectCartItemsByUserIdServlet" style="float:right; margin-right: 20px;">我的购物车</a>
<a href="${pageContext.request.contextPath}/viewOrdersSevlet?userId=${userId}"style="float:right; margin-right: 20px;">查看我的订单</a>

<!-- 在此处添加搜索框 -->
<form action="${pageContext.request.contextPath}/selectAllServlet" method="post" style="float: left; margin-right: 20px;">
    <input type="text" name="searchQuery" placeholder="请输入商品名称" style="padding: 5px;">
    <input type="submit" value="搜索" style="padding: 5px;">
</form>
<h1>商品信息</h1>
<a href="${pageContext.request.contextPath}/logoutServlet" style="float:right; margin-right: 20px;">注销用户</a>

<table>
    <tr>
        <th>序号</th>
        <th>商品名称</th>
        <th>企业名称</th>
        <th>描述信息</th>
        <th>价格</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${brands}" var="brand" varStatus="status">
        <tr align="center">
            <td>${status.count}</td>
            <td>${brand.brandName}</td>
            <td>${brand.companyName}</td>
            <td>${brand.description}</td>
            <td>${brand.price}</td>
            <td>
                <form action="${pageContext.request.contextPath}/cartAddServlet" method="post">
                    <input type="hidden" name="userId" value="${user.id}">
                    <input type="hidden" name="brandId" value="${brand.id}">
                    <input type="number" name="quantity" value="1">
                    <input type="submit" value="加入购物车">
                </form>
            </td>

        </tr>
    </c:forEach>
</table>
</body>
</html>