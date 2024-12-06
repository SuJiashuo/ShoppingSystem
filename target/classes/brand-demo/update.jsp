<%--
  Created by IntelliJ IDEA.
  User: 29393
  Date: 2024/11/3
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<%--数据需要回显一下--%>
<head>
    <meta charset="UTF-8">
    <title>修改品牌</title>
</head>
<body>
<h3>修改品牌</h3>
<form action="${pageContext.request.contextPath}/updateServlet" method="post">

    <%--隐藏域,提交id--%>
    <input type="hidden" name="id" value="${brand.id}"><br>

    品牌名称：<input name="brandName" value="${brand.brandName}"><br>
    企业名称：<input name="companyName" value="${brand.companyName}"><br>
    排序：<input name="ordered" value="${brand.ordered}"><br>
    描述信息：<textarea rows="5" cols="20" name="description">${brand.description}</textarea><br>
    状态：
    <%--checked表示被选中--%>
    <c:if test="${brand.status==0}">
        <input type="radio" name="status" value="0" checked>禁用
        <input type="radio" name="status" value="1">启用<br>
    </c:if>
    <c:if test="${brand.status==1}">
        <input type="radio" name="status" value="0">禁用
        <input type="radio" name="status" value="1" checked>启用<br>
    </c:if>
    价格:<input name="price" value="${brand.price}"><br>
    库存:<input name="number" value="${brand.number}"><br>

    <input type="submit" value="提交">
</form>
</body>
</html>