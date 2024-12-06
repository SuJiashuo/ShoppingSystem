<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
  <title>购物车详情页</title>
  <style>
    table {
      width: 80%;
      border-collapse: collapse;
      table-layout: fixed; /* 固定表格列宽 */
    }

    th, td {
      border: 1px solid #ddd;
      padding: 8px;
      text-align: left;
      overflow: hidden; /* 防止内容溢出 */
      text-overflow: ellipsis; /* 如果内容过长，显示省略号 */
      white-space: nowrap; /* 防止内容换行 */
    }

    th {
      background-color: #f2f2f2;
    }

    /* 提示框的样式 */
    /* 提示框的样式 */
    .error-message {
      position: fixed; /* 固定定位 */
      top: 10%; /* 距离页面顶部一定距离，避免遮挡页面内容 */
      left: 50%; /* 水平居中 */
      transform: translateX(-50%); /* 调整位置使其完全居中 */
      background-color: #ffcccc;
      color: red;
      font-weight: bold;
      padding: 15px;
      border: 1px solid red;
      z-index: 9999; /* 确保在页面最上层 */
      display: none; /* 默认隐藏 */
      width: 70%; /* 可调整宽度 */
      max-width: 400px; /* 限制最大宽度 */
      text-align: center;
    }
    .quantity-container span {
      width: 40px; /* 固定宽度 */
      text-align: center;
      display: inline-block; /* 使其占用固定空间 */
    }
  </style>
  <script>
    // 页面卸载时存储滚动位置
    window.onbeforeunload = function () {
      sessionStorage.setItem('scrollPosition', window.scrollY);
    };

    // 页面加载时恢复滚动位置
    window.onload = function () {
      const scrollPos = sessionStorage.getItem('scrollPosition');
      if (scrollPos) {
        window.scrollTo(0, scrollPos);
        sessionStorage.removeItem('scrollPosition'); // 移除已存储的滚动位置以避免重复加载
      }
      // 如果存在错误信息，显示提示框并自动消失
      var errorMessage = '${errorMessage}';
      if (errorMessage) {
        var messageBox = document.getElementById('errorBox');
        messageBox.textContent = errorMessage;  // 设置提示框内容
        messageBox.style.display = 'block';  // 显示提示框

        // 设置1秒后自动消失
        setTimeout(function () {
          messageBox.style.display = 'none';
        }, 1000);  // 1秒后消失
      }
    };


    function selectAllRows(source) {
      checkboxes = document.getElementsByName('selected');
      for (var i = 0, n = checkboxes.length; i < n; i++) {
        checkboxes[i].checked = source.checked;
      }
    }

  /*
  // 更新总数和总价格
  function updateSummary() {
  const itemCheckboxes = document.querySelectorAll('.item-checkbox:checked');
  const summaryCount = document.getElementById('summaryCount');
  const summaryPrice = document.getElementById('summaryPrice');

      let totalQuantity = 0;
      let totalPrice = 0;

      itemCheckboxes.forEach(checkbox => {
        const row = checkbox.closest('tr');
        const quantity = parseInt(row.querySelector('.quantity span').textContent);
        const price = parseFloat(row.querySelector('.totalPrice').textContent);
        totalQuantity += quantity;
        totalPrice += price;
      });

      summaryCount.textContent = totalQuantity;
      summaryPrice.textContent = totalPrice.toFixed(2);
    }*/

  </script>

</head>
<body>
<h1>购物车详情</h1>
<a href="${pageContext.request.contextPath}/selectAllServlet" style="float:right; margin-right: 20px;">返回商品展示页面</a>

<input type="checkbox" onclick="selectAllRows(this)" /> 全选

<!-- 搜索框 -->
<form action="${pageContext.request.contextPath}/selectCartItemsByUserIdServlet" method="post" style="margin-bottom: 20px;">
  <input type="text" name="searchQuery1" placeholder="请输入商品名称" style="padding: 5px;" value="${searchQuery1}">
  <input type="submit" value="搜索" style="padding: 5px;">
</form>

<!-- 错误提示框 -->
<div id="errorBox" class="error-message"></div>

<!-- 结算表单 -->
<form action="${pageContext.request.contextPath}/checkoutServlet" method="post" style="display: inline;">
  <input type="submit" value="去结算" />
  <table>
    <tr>
      <th>选择</th>
      <th>序号</th>
      <th>商品名称</th>
      <th>价格</th>
      <th>数量</th>
      <th>总价</th>
      <th>操作</th>
    </tr>
    <c:forEach items="${cartItems}" var="cartItem" varStatus="status">
      <tr align="center">
        <td>
          <input type="checkbox" name="selected" value="${cartItem.brandId}"/>
          <input type="hidden" name="quantity_${cartItem.brandId}" value="${cartItem.quantity}" />
          <input type="hidden" name="price_${cartItem.brandId}" value="${cartItem.price}" />
          <input type="hidden" name="totalPrice_${cartItem.brandId}" value="${cartItem.totalPrice}" />
        </td>
        <td>${status.count}</td>
        <td>${cartItem.brandName}</td>
        <td>${cartItem.price}</td>
        <td>
          <div class="quantity-container">
            <button type="button" onclick="submitQuantityChange(${cartItem.brandId}, ${cartItem.number}, -1, '${searchQuery1}')">-</button>
            <span>${cartItem.quantity}</span>
            <button type="button" onclick="submitQuantityChange(${cartItem.brandId}, ${cartItem.number}, 1, '${searchQuery1}')">+</button>
          </div>
        </td>
        <td>${cartItem.totalPrice}</td>
        <td>
          <button type="button" onclick="deleteCartItem(${cartItem.brandId})">删除</button>
        </td>
      </tr>
    </c:forEach>
  </table>
</form>

<!-- 数量更新表单 -->
<form id="quantityUpdateForm" action="${pageContext.request.contextPath}/updateCartQuantityServlet" method="post" style="display: none;">
  <input type="hidden" id="updateBrandId" name="brandId" />
  <input type="hidden" id="updateNumber" name="number" />
  <input type="hidden" id="updateSearchQuery" name="searchQuery1" />
  <input type="hidden" id="updateChange" name="change" />
</form>

<!-- 删除表单 -->
<form id="deleteForm" action="${pageContext.request.contextPath}/deleteCartItemServlet" method="post" style="display: none;">
  <input type="hidden" id="deleteBrandId" name="brandId" />
</form>

<script>
  function submitQuantityChange(brandId, number, change, searchQuery) {
    document.getElementById('updateBrandId').value = brandId;
    document.getElementById('updateNumber').value = number;
    document.getElementById('updateSearchQuery').value = searchQuery;
    document.getElementById('updateChange').value = change;
    document.getElementById('quantityUpdateForm').submit();
  }

  function deleteCartItem(brandId) {
    if(confirm('确定要删除该商品吗？')) {
      document.getElementById('deleteBrandId').value = brandId;
      document.getElementById('deleteForm').submit();
    }
  }
</script>
</body>
<%--666666修改前表单嵌套--%>
<%--<body>
<h1>购物车详情</h1>
<a href="${pageContext.request.contextPath}/selectAllServlet" style="float:right; margin-right: 20px;">返回商品展示页面</a>

&lt;%&ndash;
<div class="summary">
  <strong>已选择商品数量：</strong><span id="summaryCount">0 </span>
  <strong> 总价格：</strong><span id="summaryPrice">0.00</span>
  <form action="${pageContext.request.contextPath}/checkoutServlet" method="post" style="display: inline;">
    <input type="submit" value="去结算" />
  </form>
  <input type="checkbox" onclick="toggleSelectAll(this)" /> 全选
</div>

&ndash;%&gt;
<input type="checkbox" onclick="selectAllRows(this)" /> 全选

<!-- 在此处添加搜索框 -->
<form action="${pageContext.request.contextPath}/selectCartItemsByUserIdServlet" method="post" style="margin-bottom: 20px;">
  <input type="text" name="searchQuery1" placeholder="请输入商品名称" style="padding: 5px;"value="${searchQuery1}">
  <input type="submit" value="搜索" style="padding: 5px;">
</form>
<!-- 错误提示框 -->
<div id="errorBox" class="error-message"></div>

&lt;%&ndash;要用form标签包裹起来,不然复选框的数据不会提交&ndash;%&gt;
<form action="${pageContext.request.contextPath}/checkoutServlet" method="post" style="display: inline;">
  <input type="submit" value="去结算" />
<table>
  <tr>
    <th>选择</th>
    <th>序号</th>
    <th>商品名称</th>
    <th>价格</th>
    <th>数量</th>
    <th>总价</th>
    <th>操作</th>
  </tr>
  <c:forEach items="${cartItems}" var="cartItem" varStatus="status">
    <tr align="center">
      <!--复选框-->
      <td>
        <input type="checkbox"  name="selected" value="${cartItem.brandId}"/>
        <!-- 隐藏字段：数量 -->
        <input type="hidden" name="quantity_${cartItem.brandId}" value="${cartItem.quantity}" />
        <!-- 隐藏字段：价格 -->
        <input type="hidden" name="price_${cartItem.brandId}" value="${cartItem.price}" />
        <!-- 隐藏字段：总价 -->
        <input type="hidden" name="totalPrice_${cartItem.brandId}" value="${cartItem.totalPrice}" />
      </td>
      <td>${status.count}</td>
      <td>${cartItem.brandName}</td>
      <td>${cartItem.price}</td>
      <td>
        <div class="quantity-container">
        <!-- 减少数量的表单 -->
        <form action="${pageContext.request.contextPath}/updateCartQuantityServlet" method="post" style="display:inline;">
          <input type="hidden" name="brandId" value="${cartItem.brandId}"/>
          <input type="hidden" name="number" value="${cartItem.number}"/>
          <input type="hidden" name="searchQuery1" value="${searchQuery1 != null ? searchQuery1 : ''}">
          <input type="hidden" name="change" value="-1"/> <!-- 减少 -->
          <button type="submit">-</button>
        </form>
        <!-- 显示当前数量 -->
        <span>${cartItem.quantity}</span>
        <!-- 增加数量的表单 -->
        <form action="${pageContext.request.contextPath}/updateCartQuantityServlet" method="post" style="display:inline;">
          <input type="hidden" name="brandId" value="${cartItem.brandId}"/>
          <input type="hidden" name="number" value="${cartItem.number}"/>
          <input type="hidden" name="searchQuery1" value="${searchQuery1 != null ? searchQuery1 : ''}">
          <input type="hidden" name="change" value="1"/> <!-- 增加 -->
          <button type="submit">+</button>
        </form>
        </div>
      </td>
      <td>${cartItem.totalPrice}</td>
      <td>
        <form action="${pageContext.request.contextPath}/deleteCartItemServlet" method="post" style="display:inline;">
          <input type="hidden" name="brandId" value="${cartItem.brandId}"/>
          <input type="submit" value="删除" onclick="return confirm('确定要删除该商品吗？');"/>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>
</form>
</body>--%>
</html>


<%--

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
  <title>购物车详情页</title>
  <style>
    table {
      width: 80%;
      border-collapse: collapse;
      table-layout: fixed;
    }

    th, td {
      border: 1px solid #ddd;
      padding: 8px;
      text-align: left;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    th {
      background-color: #f2f2f2;
    }

    .error-message {
      position: fixed;
      top: 10%;
      left: 50%;
      transform: translateX(-50%);
      background-color: #ffcccc;
      color: red;
      font-weight: bold;
      padding: 15px;
      border: 1px solid red;
      z-index: 9999;
      display: none;
      width: 70%;
      max-width: 400px;
      text-align: center;
    }
  </style>
  <script>
    function updateSummary() {
      const itemCheckboxes = document.querySelectorAll('.item-checkbox:checked');
      const summaryCount = document.getElementById('summaryCount');
      const summaryPrice = document.getElementById('summaryPrice');

      let totalQuantity = 0;
      let totalPrice = 0;

      itemCheckboxes.forEach(checkbox => {
        const row = checkbox.closest('tr');
        const quantity = parseInt(row.querySelector('.quantity span').textContent);
        const price = parseFloat(row.querySelector('.totalPrice').textContent);
        totalQuantity += quantity;
        totalPrice += price;
      });

      summaryCount.textContent = totalQuantity;
      summaryPrice.textContent = totalPrice.toFixed(2);
    }

    // 页面加载时恢复滚动位置
    window.onload = function() {
      const scrollPos = sessionStorage.getItem('scrollPosition');
      if (scrollPos) {
        window.scrollTo(0, scrollPos);
        sessionStorage.removeItem('scrollPosition');
      }
      // 如果存在错误信息，显示提示框并自动消失
      var errorMessage = '${errorMessage}';
      if (errorMessage) {
        var messageBox = document.getElementById('errorBox');
        messageBox.textContent = errorMessage;
        messageBox.style.display = 'block';
        setTimeout(function() {
          messageBox.style.display = 'none';
        }, 1000);
      }
    };
  </script>
</head>
<body>
<h1>购物车详情</h1>
<a href="${pageContext.request.contextPath}/selectAllServlet" style="float:right; margin-right: 20px;">返回商品展示页面</a>

<div class="summary">
  <strong>已选择商品数量：</strong><span id="summaryCount">0 </span>
  <strong> 总价格：</strong><span id="summaryPrice">0.00</span>
  <form action="${pageContext.request.contextPath}/createOrderServlet" method="post" style="display: inline;">
    <!-- 添加用户ID，假设从 session 获取 -->
    <input type="hidden" name="userId" value="${sessionScope.userId}">
    <input type="submit" value="去结算" />
  </form>
  <input type="checkbox" onclick="toggleSelectAll(this)" /> 全选
</div>

<!-- 搜索框 -->
<form action="${pageContext.request.contextPath}/selectCartItemsByUserIdServlet" method="post" style="margin-bottom: 20px;">
  <input type="text" name="searchQuery1" placeholder="请输入商品名称" style="padding: 5px;" value="${searchQuery1}">
  <input type="submit" value="搜索" style="padding: 5px;">
</form>

<!-- 错误提示框 -->
<div id="errorBox" class="error-message"></div>

<form action="${pageContext.request.contextPath}/createOrderServlet" method="post">
  <table>
    <tr>
      <th>选择</th>
      <th>序号</th>
      <th>商品名称</th>
      <th>价格</th>
      <th>数量</th>
      <th>总价</th>
      <th>操作</th>
    </tr>
    <c:forEach items="${cartItems}" var="cartItem" varStatus="status">
      <tr align="center">
        <td><input type="checkbox" class="item-checkbox" name="selectedItems" value="${cartItem.brandId}" onclick="updateSummary()" /></td>
        <td>${status.count}</td>
        <td>${cartItem.brandName}</td>
        <td>${cartItem.price}</td>
        <td>
          <div class="quantity-container">
            <!-- 数量更新表单 -->
            <form action="${pageContext.request.contextPath}/updateCartQuantityServlet" method="post" style="display:inline;">
              <input type="hidden" name="brandId" value="${cartItem.brandId}" />
              <input type="hidden" name="number" value="${cartItem.number}" />
              <input type="hidden" name="change" value="-1" />
              <button type="submit">-</button>
            </form>
            <span>${cartItem.quantity}</span>
            <form action="${pageContext.request.contextPath}/updateCartQuantityServlet" method="post" style="display:inline;">
              <input type="hidden" name="brandId" value="${cartItem.brandId}" />
              <input type="hidden" name="number" value="${cartItem.number}" />
              <input type="hidden" name="change" value="1" />
              <button type="submit">+</button>
            </form>
          </div>
        </td>
        <td class="totalPrice">${cartItem.totalPrice}</td>
        <td>
          <form action="${pageContext.request.contextPath}/deleteCartItemServlet" method="post" style="display:inline;">
            <input type="hidden" name="brandId" value="${cartItem.brandId}" />
            <input type="submit" value="删除" onclick="return confirm('确定要删除该商品吗？');" />
          </form>
        </td>
      </tr>
    </c:forEach>
  </table>
</form>
</body>
</html>
--%>

