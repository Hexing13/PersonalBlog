<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: geekgao
  Date: 15-11-7
  Time: 下午22:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>发博文</title>
    <script>
        window.UEDITOR_HOME_URL = "${pageContext.request.contextPath}/ueditor/";
    </script>

    <!-- Custom styles for this template -->
    <link href="../css/starter-template.css" rel="stylesheet">

    <link href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">

    <link href="../css/screen.css" rel="stylesheet">

    <link href="../css/background.css" rel="stylesheet">
</head>

<body>
<%@include file="../nav.jsp"%>
<!-- 加载编辑器的容器 -->
<div class="container" style="padding: 0;background-color: #ffffff;margin-top: 40px;">

    <div class="starter-template" style="padding: 15px;">
        <div class="input-group input-group" style="margin-bottom: 10px">
            <span class="input-group-addon" id="sizing-addon1">文章标题</span>
            <input id="articleTitle" type="text" class="form-control" placeholder="输入文章标题" aria-describedby="sizing-addon1">
        </div>
        <script id="editor" name="content" type="text/plain"></script>
        选择类别:<br>
        <div id="selectcategory" class="pull-left">
            <c:forEach items="${categories}" var="category">
                <c:if test="${category.id != 1}">
                    <label class="checkbox-inline" style="margin-left: 10px">
                        <input type="checkbox" value="${category.id}" name="category">${category.name}
                    </label>
                </c:if>
            </c:forEach>
        </div>
        <br>
        <a id="postBtn" href="#" class="btn btn-default pull-right">发布</a>
    </div>
</div>
<!-- 配置文件 -->
<script type="text/javascript" src="../js/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="http://apps.bdimg.com/libs/ueditor/1.4.3.1/ueditor.all.js"></script>
<!-- 实例化编辑器 -->
<script type="text/javascript">
    var ue = UE.getEditor('editor');
</script>

<script>

    $(document).ready(function() {
        //清空最上面的导航栏目的ul中所有li的class属性
        $("ul.navbar-nav li").attr("class","");
        $("#postArticle").attr("class","active");
    });

    $("#postBtn").click(function () {
        //检查所需要的内容是否都填写了
        if ($("#articleTitle").val() == "") {
            alert("请填写文章标题");
            return;
        }
        if (UE.getEditor('editor').getContent() == "") {
            alert("请填写文章内容");
            return;
        }


        var id_array = [];
        $('input[name = "category"]:checked').each(function(){
            id_array.push($(this).val());//向数组中添加元素
        });
        var categoryIds = id_array.join(',');//将数组元素连接起来以构建一个字符串
        var title = $("#articleTitle").val();
        var content = ue.getContent();
        var plainContent = ue.getContentTxt();

        $.ajax({
            type:"post",
            url:"${pageContext.request.contextPath}/postedit",
            data:{
                title:title,
                categoryIds:categoryIds,
                content:content,
                plainContent:plainContent
            },
            success:function(result){
                if (result == "success") {
                    window.location.href = "${pageContext.request.contextPath}/";
                } else if (result == "paramWrong") {
                    alert("提交参数错误，请保存文章后调试.");
                } else {
                    alert("发生未知错误,文章提交失败.");
                }
            }
        });
    });
</script>

</body>

</html>