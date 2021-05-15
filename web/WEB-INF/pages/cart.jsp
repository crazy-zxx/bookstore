<%--
  Created by IntelliJ IDEA.
  User: zhaoxiangxin
  Date: 2021/5/15
  Time: 16:47
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/common/baseURL.jsp" %>

<html>
<head>
    <title>cart</title>

    <script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
    <%@ include file="/WEB-INF/common/queryCondition.jsp" %>
    <script type="text/javascript">
        $(function(){
            //删除操作
            $(".delete").click(function(){

                var $tr = $(this).parent().parent();
                var title = $.trim($tr.find("td:first").text());
                var flag = confirm("确定要删除" + title + "的信息吗?");

                if(flag){
                    return true;
                }

                return false;
            });

            //ajax 修改单个商品的数量:
            //1. 获取页面中所有的 text, 并为其添加 onchange 响应函数. 弹出确认对话框: 确定要修改吗?
            $(":text").change(function(){
                var quantityVal = $.trim(this.value);

                var flag = false;

                var reg = /^\d+$/g;
                var quantity = -1;
                if(reg.test(quantityVal)){
                    quantity = parseInt(quantityVal);
                    if(quantity >= 0){
                        flag = true;
                    }
                }

                if(!flag){
                    alert("输入的数量不合法!");
                    $(this).val($(this).attr("class"));
                    return;
                }

                var $tr = $(this).parent().parent();
                var title = $.trim($tr.find("td:first").text());

                if(quantity === 0){
                    var flag2 = confirm("确定要删除" + title + "吗?");
                    if(flag2){
                        //得到了 a 节点
                        var $a = $tr.find("td:last").find("a");
                        //执行 a 节点的 onclick 响应函数.
                        $a[0].onclick();

                        return;
                    }
                }

                var flag3 = confirm("确定要修改" + title + "的数量吗?");

                if(!flag3){
                    $(this).val($(this).attr("class"));
                    return;
                }
                //2. 请求地址为: bookServlet
                var url = "bookServlet";

                //3. 请求参数为: method:updateItemQuantity, bookId:name属性值, quantity:val, time:new Date()
                var idVal = $.trim(this.name);
                var args = {"method":"updateItemQuantity", "bookId":idVal, "quantity":quantityVal, "time":new Date()};

                //4. 在 updateItemQuantity 方法中, 获取 quanity, bookId, 再获取购物车对象, 调用 service 的方法做修改
                //5. 传回 JSON 数据: bookNumber:xx, totalMoney

                //6. 更新当前页面的 bookNumber 和 totalMoney
                $.post(url, args, function(data){
                    var bookNumber = data.bookNumber;
                    var totalMoney = data.totalMoney;

                    $("#totalMoney").text("总金额: ￥" + totalMoney);
                    $("#bookNumber").text("您的购物车中共有" + bookNumber + "本书");
                },"JSON");

            });

        });

    </script>
</head>
<body>
<center>

    <br><br>
    <div id="bookNumber">您的购物车中共有 ${sessionScope.ShoppingCart.bookNumber } 本书</div>
    <table cellpadding="10">
        <tr>
            <td>Title</td>
            <td>Quantity</td>
            <td>Price</td>
            <td>&nbsp;</td>
        </tr>

        <c:forEach items="${sessionScope.ShoppingCart.items }" var="item">
            <tr>
                <td>${item.book.title }</td>
                <td>
                    <input class="${item.quantity}" type="text" size="5" name="${item.book.bookId }" value="${item.quantity }"/>
                </td>
                <td>${item.book.price }</td>
                <td>
                    <a href="bookServlet?method=remove&pageNo=${param.pageNo }&bookId=${item.book.bookId }" class="delete">删除</a>
                </td>
            </tr>
        </c:forEach>

        <tr>
            <td colspan="4" id="totalMoney">总金额: ￥ ${ sessionScope.ShoppingCart.totalMoney}</td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="bookServlet?method=getBooks&pageNo=${param.pageNo }">继续购物</a>
                &nbsp;&nbsp;

                <a href="bookServlet?method=clear">清空购物车</a>
                &nbsp;&nbsp;

                <a href="bookServlet?method=forwardPage&page=cash&pageNo=${param.pageNo }">结账</a>
                &nbsp;&nbsp;
            </td>
        </tr>

    </table>

</center>

<br><br>

</body>
</html>
