<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
  <title>订单确认</title>
</head>
<body>
<h1>订单确认</h1>
<table border="1">
  <tr>
    <th>商品编号</th>
    <th>价格</th>
    <th>数量</th>
    <th>总价</th>
  </tr>
  <c:forEach var="orderItem" items="${orderItems}">
    <tr>
      <td>${orderItem.brandId}</td>
      <td>${orderItem.totalPrice / orderItem.quantity}</td>
      <td>${orderItem.quantity}</td>
      <td>${orderItem.totalPrice}</td>
    </tr>
  </c:forEach>
</table>

<!-- 提交按钮 -->
<form action="submitOrderServlet" method="post">
  <input type="submit" value="提交订单" />
</form>

</body>
</html>
