<%--
  Created by IntelliJ IDEA.
  User: zhaoxiangxin
  Date: 2021/5/15
  Time: 16:38
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/common/baseURL.jsp" %>

<html>
<head>
    <title>cash</title>
    <script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
    <%@ include file="/WEB-INF/common/queryCondition.jsp" %>
</head>
<body>

<center>
    <br><br>
    您一共买了 ${sessionScope.ShoppingCart.bookNumber } 本书
    <br>
    应付: ￥ ${ sessionScope.ShoppingCart.totalMoney}

    <br><br>

    <c:if test="${requestScope.errors != null }">
        <span style="color: red">${requestScope.errors }</span>
    </c:if>

    <form action="bookServlet?method=cash" method="post">

        <table cellpadding="10">
            <tr>
                <td>信用卡姓名:</td>
                <td><input type="text" name="username"/></td>
            </tr>
            <tr>
                <td>信用卡账号:</td>
                <td><input type="text" name="accountId"/></td>
            </tr>
            <tr>
                <td><a href="bookServlet?method=forwardPage&page=cart&pageNo=${param.pageNo}">返回购物车</a></td>
                <td><input type="submit" value="支付"/></td>
            </tr>
        </table>

    </form>

</center>

</body>
</html>
