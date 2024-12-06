<%--
  Created by IntelliJ IDEA.
  User: 29393
  Date: 2024/11/4
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>欢迎注册</title>
  <link href="css/register.css" rel="stylesheet">
</head>
<body>

<div class="form-div">
  <div class="reg-content">
    <h1>欢迎注册</h1>
    <span>已有帐号？</span> <a href="login.jsp">登录</a>
  </div>
  <form id="reg-form" action="${pageContext.request.contextPath}/registerServlet" method="post">

    <table>

      <tr>
        <td>用户名</td>
        <td class="inputs">
          <input name="username" type="text" id="username">
          <br>
          <span id="username_err" class="err_msg">${register_msg}</span>
        </td>

      </tr>

      <tr>
        <td>密码</td>
        <td class="inputs">
          <input name="password" type="password" id="password">
          <br>
          <span id="password_err" class="err_msg" style="display: none">密码格式有误</span>
        </td>
      </tr>


      <tr>
        <td>验证码</td>
        <td class="inputs">
          <input name="checkCode" type="text" id="checkCode">
          <%--获取验证码图片--%>
          <img id="checkCodeImg" src="${pageContext.request.contextPath}/checkCodeServlet">
          <a href="#" id="changeImg">看不清？</a>
        </td>
      </tr>

    </table>

    <div class="buttons">
      <input value="注 册" type="submit" id="reg_btn">
    </div>
    <%--给changImage绑定单击事件--%>
    <script>
      /*为字和图片都添加单击事件*/
      document.getElementById("changeImg").onclick=function (){
        //图片被浏览器缓存了,所以点击之后不会有变化,要加上个参数
        //加上个永远不会重复的变量:时间

        document.getElementById("checkCodeImg").src="${pageContext.request.contextPath}/checkCodeServlet?"+new Date().getMilliseconds()
      }

      document.getElementById("checkCodeImg").onclick=function (){
        document.getElementById("checkCodeImg").src="${pageContext.request.contextPath}/checkCodeServlet?"+new Date().getMilliseconds()
      }

    </script>
    <br class="clear">
  </form>

</div>
</body>
</html>