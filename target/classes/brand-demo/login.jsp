<%--
  Created by IntelliJ IDEA.
  User: 29393
  Date: 2024/11/4
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>login</title>
  <link href="css/login.css" rel="stylesheet">
</head>

<body>
<div id="loginDiv" style="height: 350px">
  <%--<form action="/brand-demo/loginServlet" id="form">--%>
  <form action="${pageContext.request.contextPath}/loginServlet" id="form">
    <h1 id="loginMsg">LOGIN IN</h1>
    <div id="errorMsg">${login_msg} ${register_msg}</div>
    <p>Username:<input id="username" name="username" value="${cookie.username.value}" type="text"></p>
    <p>Password:<input id="password" name="password"  value="${cookie.password.value}"type="password"></p>
    <p>登录类型：
      <input type="radio" name="loginType" value="customer" checked>客户登录
      <input type="radio" name="loginType" value="admin">管理员登录
    </p>
    <p>Remember:<input id="remember" name="remember" value="1" type="checkbox"></p>
    <div id="subDiv">
      <input type="submit" class="button" value="login up">
     <%-- <input type="reset" class="button" value="reset">&nbsp;&nbsp;&nbsp;--%>
      <a href="register.jsp">没有账号？</a>
    </div>
  </form>
</div>

</body>
</html>