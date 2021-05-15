<%--
  Created by IntelliJ IDEA.
  User: zhaoxiangxin
  Date: 2021/5/15
  Time: 11:46
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/common/baseURL.jsp" %>

<html>
<head>
    <title>trades</title>
</head>
<body>

<center>
    <br><br>
    <h4>
        User: ${user.username }
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="/users.jsp">退出</a>
    </h4>
    <br><br>
    <table>
        <c:forEach items="${user.trades }" var="trade">

            <tr>
                <td>
                    <table border="1" cellpadding="10" cellspacing="0">

                        <tr>
                            <td colspan="3">TradTime: ${trade.tradeTime }</td>
                        </tr>

                        <c:forEach items="${trade.items }" var="item">

                            <tr>
                                <td>${item.book.title }</td>
                                <td>${item.book.price }</td>
                                <td>${item.quantity }</td>
                            </tr>

                        </c:forEach>

                    </table>
                    <br><br>
                </td>
            </tr>

        </c:forEach>
    </table>

</center>
</body>
</html>
