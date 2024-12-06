<%--
  Created by IntelliJ IDEA.
  User: 29393
  Date: 2024/11/19
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.util.List, java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>商品销售统计报表</title>
    <style>
        body {
            text-align: center;
        }
        table {
            margin: 0 auto;
            border-collapse: collapse;
        }
        th, td {
            text-align: center;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
        .title {
            font-size: 1.5em;
            margin-bottom: 20px;
        }
    </style>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.2/html2pdf.bundle.js"></script>
</head>
<body>
<h1>商品销售统计报表</h1>
<a href="${pageContext.request.contextPath}/selectAllServlet" style="float:right; margin-right: 20px;">返回管理页面</a>
<!-- 排序选择 -->
<form action="salesReportServlet" method="get">
    <label for="sortOrder">选择排序方式: </label>
    <select name="sortOrder" id="sortOrder">
        <option value="default" <c:if test="${param.sortOrder == 'default'}">selected</c:if>>默认排序 (按商品ID)</option>
        <option value="totalSales" <c:if test="${param.sortOrder == 'totalSales'}">selected</c:if>>按销售额排序</option>
        <option value="totalQuantity" <c:if test="${param.sortOrder == 'totalQuantity'}">selected</c:if>>按销售总量排序</option>
    </select>
    <button type="submit">排序</button>
</form>

<!-- 排序标题 -->
<div class="title" id="sortTitle">
    <c:choose>
        <c:when test="${param.sortOrder == 'default'}">默认排序 (按商品ID)</c:when>
        <c:when test="${param.sortOrder == 'totalSales'}">按销售额排序</c:when>
        <c:when test="${param.sortOrder == 'totalQuantity'}">按销售总量排序</c:when>
        <c:otherwise>未选择排序方式</c:otherwise>
    </c:choose>
</div>

<!-- 商品销售报表表格 -->
<table border="1" cellspacing="0" width="80%" id="salesTable">
    <tr>
        <th>商品ID</th>
        <th>销售数量</th>
        <th>总销售额</th>
    </tr>
    <c:forEach items="${salesReports}" var="report">
        <tr>
            <td>${report['brandId']}</td>
            <td>${report['totalQuantity']}</td>
            <td>${report['totalSales']}</td>
        </tr>
    </c:forEach>
</table>

<!-- 导出PDF按钮 -->
<button id="exportPdfBtn">导出为PDF</button>

<!-- 导出PDF功能 -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        document.getElementById("exportPdfBtn").addEventListener("click", function () {
            console.log("Button clicked!");

            // 获取排序标题和表格
            const sortTitle = document.getElementById('sortTitle');
            const table = document.getElementById('salesTable');

            // 创建一个新的容器，将标题和表格添加到里面
            const contentToExport = document.createElement('div');
            contentToExport.appendChild(sortTitle.cloneNode(true));  // 克隆排序标题
            contentToExport.appendChild(table.cloneNode(true));  // 克隆表格

            // 使用html2pdf将选中的内容转为PDF
            const options = {
                filename: "sales_report.pdf",   // 文件名
                html2canvas: { scale: 2 },      // 渲染时的缩放比例
                jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' },  // PDF页面设置
                pagebreak: { mode: 'avoid-all' }  // 避免分页
            };

            html2pdf().from(contentToExport).set(options).save();
        });
    });
</script>
</body>
</html>

<%--<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>商品销售统计报表</title>
    <style>
        body {
            text-align: center;
        }
        table {
            margin: 0 auto;
            border-collapse: collapse;
        }
        th, td {
            text-align: center;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
    <!-- 引入html2pdf.js库 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.2/html2pdf.bundle.js"></script>
</head>
<body>
<h1>商品销售统计报表</h1>

<!-- 排序选择 -->
<form action="salesReportServlet" method="get">
    <label for="sortOrder">选择排序方式: </label>
    <select name="sortOrder" id="sortOrder">
        <option value="default" <c:if test="${param.sortOrder == 'default'}">selected</c:if>>默认排序 (按商品ID)</option>
        <option value="totalSales" <c:if test="${param.sortOrder == 'totalSales'}">selected</c:if>>按销售额排序</option>
        <option value="totalQuantity" <c:if test="${param.sortOrder == 'totalQuantity'}">selected</c:if>>按销售总量排序</option>
    </select>
    <button type="submit">排序</button>
</form>

<!-- 商品销售报表表格 -->
<table border="1" cellspacing="0" width="80%">
    <tr>
        <th>商品ID</th>
        <th>销售数量</th>
        <th>总销售额</th>
    </tr>
    <c:forEach items="${salesReports}" var="report">
        <tr>
            <td>${report['brandId']}</td>
            <td>${report['totalQuantity']}</td>
            <td>${report['totalSales']}</td>
        </tr>
    </c:forEach>
</table>

<!-- 导出PDF按钮 -->
<button id="exportPdfBtn">导出为PDF</button>

<!-- 导出PDF功能 -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        document.getElementById("exportPdfBtn").addEventListener("click", function () {
            console.log("Button clicked!");  // 打印日志以确认事件被触发

            // 获取表格
            const element = document.querySelector('table');

            // 使用html2pdf将表格转为PDF
            const options = {
                filename: "sales_report.pdf",   // 文件名
                html2canvas: { scale: 2 },      // 渲染时的缩放比例
                jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' },  // PDF页面设置
                pagebreak: { mode: 'avoid-all' }  // 避免分页
            };

            html2pdf().from(element).set(options).save();
        });
    });
</script>
</body>
</html>--%>
