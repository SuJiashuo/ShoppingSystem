<%--
  Created by IntelliJ IDEA.
  User: 29393
  Date: 2024/11/18
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>所有用户订单</title>
  <style>
    table {
      width: 100%;
      border-collapse: collapse;
    }
    th, td {
      border: 1px solid #ddd;
      padding: 8px;
      text-align: center;
    }
    th {
      background-color: #f2f2f2;
    }
    a {
      text-decoration: none;
      color: blue;
    }
  </style>
</head>
<body>
<h1>所有用户订单</h1>
<a href="${pageContext.request.contextPath}/selectAllServlet" style="float:right; margin-right: 20px;">返回管理页面</a>
<hr>
<table>
  <tr>
    <th>用户名</th>
    <th>操作</th>
  </tr>
  <c:forEach var="user" items="${users}">
    <tr>
      <td>${user.username}</td>
      <td><a href="${pageContext.request.contextPath}/userOrderDetailServlet?userId=${user.id}&&username=${user.username}">查看详情</a></td>
    </tr>
  </c:forEach>
</table>
</body>
</html>

