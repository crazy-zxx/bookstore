<%--
  Created by IntelliJ IDEA.
  User: zhaoxiangxin
  Date: 2021/5/14
  Time: 14:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- 引入JSTL标签库 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 规定页面中所有相对链接的基准 URL --%>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath}/">
