<%--
  Created by IntelliJ IDEA.
  User: zhaoxiangxin
  Date: 2021/5/14
  Time: 12:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/common/baseURL.jsp" %>

<html>
<head>
    <title>login</title>

    <style>
        /*修改提示文字的颜色*/
        input::-webkit-input-placeholder { /* WebKit browsers */
            color: red;
        }

        input:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
            color: red;
        }

        input::-moz-placeholder { /* Mozilla Firefox 19+ */
            color: red;
        }

        input:-ms-input-placeholder { /* Internet Explorer 10+ */
            color: red;
        }
    </style>

    <script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript">
        $(function () {

            $("input[name='username']").change(function () {
                if ($(this).val().trim() === "") {
                    $(this).attr("placeholder", "用户名不能为空");
                }
            });

            $("input[name='password']").change(function () {
                var reg = /\w{6,20}/g;
                if (!reg.test($(this).val())) {
                    $(this).val("");
                    $(this).attr("placeholder", "无效密码");
                }
            });

            $(":submit").click(function () {
                var $u = $("input[name='username']");
                var $p = $("input[name='password']");
                if ($u.val().trim() === "") {
                    $u.attr("placeholder", "用户名不能为空");
                    return false;
                }
                if ($p.val() === "") {
                    $p.attr("placeholder", "无效密码");
                    return false;
                }
            });
        });
    </script>
</head>
<body>

<br>
<br>
<center>

    <c:if test="${requestScope.message != null}">
        <span style="color: red">
                ${requestScope.message}
        </span>
    </c:if>

    <br><br>
    <form action="userServlet" method="post">
        username:<input type="text" name="username"/>
        <br>
        password:<input type="password" name="password">
        <br>
        <input type="submit" value="登陆"/>
    </form>
</center>
</body>
</html>
