<%--
  Created by IntelliJ IDEA.
  User: zhaoxiangxin
  Date: 2021/5/12
  Time: 09:21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>index</title>
</head>
<body>
    <%
        response.sendRedirect(request.getContextPath() + "/bookServlet?method=getBooks");
    %>
</body>
</html>
