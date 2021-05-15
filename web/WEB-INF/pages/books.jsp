<%--
  Created by IntelliJ IDEA.
  User: zhaoxiangxin
  Date: 2021/5/14
  Time: 13:51
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/common/baseURL.jsp" %>


<html>
<head>
    <title>books</title>

    <link href="/css/content.css" rel="stylesheet">

    <script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
    <%@ include file="/WEB-INF/common/queryCondition.jsp" %>
    <script type="text/javascript">
        $(function () {

            // 页码输入框值改变触发的事件
            $("#pageNo").change(function () {
                var val = $(this).val().trim();

                var flag = false;
                //匹配数值的正则表达式
                var reg = /^\d+$/g;
                var pageNo = 0;

                if (reg.test(val)) {
                    pageNo = parseInt(val);
                    //页码取值范围校验
                    if (pageNo >= 1 && pageNo <= parseInt("${bookpage.totalPageNumber}")) {
                        flag = true;
                    }
                }

                if (!flag) {
                    alert("输入页码不合法！");
                    $(this).val("");
                    return;
                }

                //在当前窗口打开URL
                window.location.href = "bookServlet?method=getBooks&pageNo=" + pageNo + "&" + $(":hidden").serialize();
            });
        });
    </script>
</head>
<body>
<div style="text-align: center">

    <c:if test="${param.title != null}">
        您已经将 ${param.title} 放入到购物车中！
        <br><br>
    </c:if>

    <c:if test="${not empty sessionScope.ShoppingCart.books }">
        您的购物车中有 ${sessionScope.ShoppingCart.bookNumber } 本书,<a href="bookServlet?method=forwardPage&page=cart&pageNo=${bookpage.pageNo }">查看购物车</a>
    </c:if>

    <br><br>

    <%--  按价格区查询的表单  --%>
    <form action="bookServlet?method=getBooks" method="post">
        价格区间:
        <input type="text" size="10" name="minPrice" placeholder="${param.minPrice!=null?param.minPrice:""}"/> -
        <input type="text" size="10" name="maxPrice" placeholder="${param.maxPrice!=null?param.maxPrice:""}"/>
        &nbsp;&nbsp;
        <input type="submit" value="搜索"/>
    </form>

    <br><br>

    <%--  显示查询结果  --%>
    <table>
        <c:forEach items="${bookpage.list}" var="book">
            <tr>
                <td>
                    <a href="bookServlet?method=getBook&pageNo=${bookpage.pageNo}&bookId=${book.bookId}">${book.title }</a>
                    <br>
                    <span>${book.author }</span>
                </td>
                <td>
                    <span>${book.price }</span>
                </td>
                <td>
                    <a href="bookServlet?method=addToCart&pageNo=${bookpage.pageNo}&bookId=${book.bookId}&title=${book.title}">加入购物车</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <br><br>

    <div>
        共 ${bookpage.totalPageNumber } 页
        &nbsp;&nbsp;
        当前第 ${bookpage.pageNo } 页


        <c:if test="${bookpage.hasPrev }">
            &nbsp;&nbsp;
            <a href="bookServlet?method=getBooks&pageNo=1">首页</a>
            &nbsp;&nbsp;
            <a href="bookServlet?method=getBooks&pageNo=${bookpage.prevPage }">上一页</a>
        </c:if>



        <c:if test="${bookpage.hasNext }">
            &nbsp;&nbsp;
            <a href="bookServlet?method=getBooks&pageNo=${bookpage.nextPage }">下一页</a>
            &nbsp;&nbsp;
            <a href="bookServlet?method=getBooks&pageNo=${bookpage.totalPageNumber }">末页</a>
        </c:if>

        &nbsp;&nbsp;

        转到 <input type="text" size="5" id="pageNo"/> 页
    </div>

</div>
</body>
</html>
