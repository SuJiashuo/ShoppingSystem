<%--
  Created by IntelliJ IDEA.
  User: 29393
  Date: 2024/11/17
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>我的订单</title>
    <a href="${pageContext.request.contextPath}/selectAllServlet" style="float:right; margin-right: 20px;">返回商品展示页面</a>

    <style>
        /* 状态颜色样式 */
        .status-green {
            color: green;
            font-weight: bold;
        }
        .status-orange {
            color: orange;
            font-weight: bold;
        }
        /* 弹出提示框样式 */
        #successMessage {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #dff0d8;
            color: #3c763d;
            border: 1px solid #d6e9c6;
            padding: 15px;
            display: none;
            z-index: 1000;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        button {
            padding: 5px 10px;
            background-color: #ff4d4d;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 3px;
        }
        button:hover {
            background-color: #cc0000;
        }
    </style>
</head>
<body>
<h1>我的订单</h1>

<!-- 成功提示 -->
<%-- 如果取消订单成功则显示 --%>
<c:if test="${not empty successMessage}">
    <div id="successMessage">${successMessage}</div>
</c:if>

<!-- 遍历订单分组 -->
<c:forEach var="entry" items="${groupedOrders}">
    <!-- 订单日期 -->
    <h2>
        订单日期: ${entry.key}
        <!-- 取消订单表单 -->
        <form action="${pageContext.request.contextPath}/cancelOrderServlet" method="post" style="display:inline;">
            <input type="hidden" name="orderTime" value="${entry.key}"/>
            <input type="submit" value="取消订单" />
        </form>
        <!-- 去付款按钮 -->
        <form action="${pageContext.request.contextPath}/payOrderServlet" method="post" style="display:inline;">
            <input type="hidden" name="orderTime" value="${entry.key}"/>
            <input type="submit" value="去付款" />
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

<!-- JavaScript 控制提示框的显示 -->
<script type="text/javascript">
    // 页面卸载时存储滚动位置
    window.onbeforeunload = function () {
        sessionStorage.setItem('scrollPosition', window.scrollY);
    };

    // 页面加载时恢复滚动位置
    window.onload = function () {

        }
    window.onload = function() {
        const scrollPos = sessionStorage.getItem('scrollPosition');
        if (scrollPos) {
            window.scrollTo(0, scrollPos);
            sessionStorage.removeItem('scrollPosition');
        }// 移除已存储的滚动位置以避免重复加载
        var successMessage = document.getElementById("successMessage");
        if (successMessage) {
            successMessage.style.display = "block";
            setTimeout(function() {
                successMessage.style.display = "none";
            }, 3000);  // 1秒后隐藏
        }
    }
</script>

</body>
</html>

