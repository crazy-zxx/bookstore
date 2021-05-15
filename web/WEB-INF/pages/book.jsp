<%--
  Created by IntelliJ IDEA.
  User: zhaoxiangxin
  Date: 2021/5/15
  Time: 16:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/common/baseURL.jsp" %>

<html>
<head>
    <title>book</title>
    <script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
    <%@ include file="/WEB-INF/common/queryCondition.jsp" %>
</head>
<body>

<center>

    <br><br>
    Title: ${book.title }
    <br><br>
    Author: ${book.author }
    <br><br>
    Price: ${book.price }
    <br><br>
    PublishingDate: ${book.publishingDate }
    <br><br>
    Remark: ${book.remark }
    <br><br>

    <a href="bookServlet?method=getBooks&pageNo=${param.pageNo }">继续购物</a>


</center>

</body>
</html>
