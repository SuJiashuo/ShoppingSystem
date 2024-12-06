<%@ page import="com.itheima.pojo.Brand" %><%--
  Created by IntelliJ IDEA.
  User: 29393
  Date: 2024/10/31
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--使用jstl,在JSP页面上引入JSTL标签库--%>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>--%>
<%--问题出在 JSTL 标签库的使用上。
错误提示“According to TLD or attribute directive in tag file,
attribute items does not accept any expressions”
表明 JSTL 标签库不支持表达式。
这个问题通常发生在使用了 JSP 2.0 但没有使用 JSTL 标签库的备用版本（RT库）的情况下--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>
    ${user.adname},欢迎您!
</h1>
<a href="${pageContext.request.contextPath}/adminOrderListServlet" style="float:right; margin-right: 20px;">查看所有订单</a>

<%--2024.11.19--%>
<a href="${pageContext.request.contextPath}/salesReportServlet" style="float:right; margin-right: 20px;">查看销售统计报表</a>
<a href="${pageContext.request.contextPath}/logoutServlet" style="float:right; margin-right: 20px;">注销用户</a>
<input type="button" value="新增" id="add"><br>
<hr>
<table border="1" cellspacing="0" width="80%">
    <tr>
        <th>序号</th>
        <th>品牌名称</th>
        <th>企业名称</th>
        <th>排序</th>
        <th>品牌介绍</th>
        <th>状态</th>
        <th>价格</th>
        <th>库存</th>
        <th>操作</th>
    </tr>
    <%--
    对名为 brands 的集合进行循环，
    var="brand" 表示在每次循环中，当前元素会被赋值给变量 brand。
    你可以在循环体内使用 brand 来引用集合中的每一个元素。
    --%>

    <c:forEach items="${brands}" var="brand" varStatus="status">
        <%--使用varStatus取对应的序号,vasStatus有两个属性,index从0开始,count从1开始--%>
        <tr align="center">
                <%--这里使用成员变量的名称,但是不是成员变量,会自动解析,调用get函数--%>
                <%--<td>${brand.id}</td>--%>
            <td>${status.count}</td>
            <td>${brand.brandName}</td>
            <td>${brand.companyName}</td>
            <td>${brand.ordered}</td>
            <td>${brand.description}</td>
            <td>${brand.status}</td>
            <td>${brand.price}</td>
            <td>${brand.number}</td>
            <td><a href="${pageContext.request.contextPath}/selectByIdServlet?id=${brand.id}">修改</a>
                <a href="${pageContext.request.contextPath}/deleteByIdServlet?id=${brand.id}">删除</a></td>
        </tr>
    </c:forEach>


</table>

<%--通过js给按钮绑定单击事件--%>
<script>
    document.getElementById("add").onclick=function (){
        location.href="addBrand.jsp";
    }
</script>
</body>
</html>