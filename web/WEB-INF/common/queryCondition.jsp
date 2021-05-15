<%--
  Created by IntelliJ IDEA.
  User: zhaoxiangxin
  Date: 2021/5/14
  Time: 14:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">
    $(function (){
        //为所有超链接的点击事件绑定处理函数
        $("a").each(function (){
            this.onclick = function (){

                // 序列化：会形成URL的请求参数格式
                var serializeVal = $(":hidden").serialize();

                // 在当前页面打开URL页面
                window.location.href = this.href + "&" + serializeVal;

                // 事件处理函数返回false,可以防止默认的事件行为
                return false;
            }
        });
    });
</script>

<%-- 隐藏的域，保存当前查询条件 --%>
<input type="hidden" name="minPrice" value="${param.minPrice}">
<input type="hidden" name="maxPrice" value="${param.maxPrice}">