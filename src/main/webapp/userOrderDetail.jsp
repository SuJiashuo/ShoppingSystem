<%--
  Created by IntelliJ IDEA.
  User: 29393
  Date: 2024/11/18
  Time: 23:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>${username} 的订单详情</title>
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
  </style>
</head>
<body>
<h1>${username} 的订单详情</h1>
<a href="${pageContext.request.contextPath}/adminOrderListServlet" style="float:right; margin-right: 20px;">返回所有订单</a>
<hr>
<table>
  <!-- 遍历订单分组 -->
  <c:forEach var="entry" items="${groupedOrders}">
    <!-- 订单日期 -->
    <h2>
      订单日期: ${entry.key}
      <!-- 发货按钮 -->
      <form action="${pageContext.request.contextPath}/shipOrderServlet" method="post" style="display:inline;">
        <input type="hidden" name="orderTime" value="${entry.key}"/>
        <input type="hidden" name="userId" value="${userId}" />
        <input type="submit" value="发货" />
      </form>
    </h2>
    <table>
      <tr>
        <th>品牌</th>
        <th>数量</th>
        <th>总价</th>
        <th>付款状态</th>
        <th>发货状态</th>
      </tr>
      <c:forEach var="orderItem" items="${entry.value}">
        <tr>
          <td>${orderItem.brandId}</td>
          <td>${orderItem.quantity}</td>
          <td>${orderItem.totalPrice}</td>
          <td>
            <c:choose>
              <c:when test="${orderItem.stat == 0}">
                <span class="status-orange">未付款</span>
              </c:when>
              <c:when test="${orderItem.stat == 1}">
                <span class="status-green">已付款</span>
              </c:when>
            </c:choose>
          </td>
          <td>
            <c:choose>
              <c:when test="${orderItem.logistics == 0}">
                <span class="status-orange">未发货</span>
              </c:when>
              <c:when test="${orderItem.logistics == 1}">
                <span class="status-green">已发货</span>
              </c:when>
            </c:choose>
          </td>
        </tr>
      </c:forEach>
    </table>
  </c:forEach>
</table>
</body>
</html>
