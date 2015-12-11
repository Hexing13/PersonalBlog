<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a id="blogName" class="navbar-brand" href="#">${pageContext.servletContext.getAttribute("blogName")}</a>
        </div>

        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li id="bloghome" class="active"><a href="${pageContext.request.contextPath}/">首页</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <%
                    if (session.getAttribute("flag") == "on") {
                %>
                <li id="postArticle"><a href="${pageContext.request.contextPath}/manage/post">发表博文</a></li>
                <li id="manageHome"><a href="${pageContext.request.contextPath}/manage">后台管理</a></li>
                <li><a id="logout" href="#">退出</a></li>
                <%
                    } else {
                %>
                <li><a id="showModal" href="#" data-toggle="modal" data-target="#myModal">管理员登录</a></li>
                <%
                    }
                %>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 480px;height: 360px;padding: 50px;">
        <div class="modal-content">
            <form id="loginform" action="loginVerify" style="margin: 0">
                <div class="input-group" style="padding: 10px;">
                    <div class="input-group-addon">
                        <i class="glyphicon glyphicon-envelope"></i>
                    </div>
                    <input id="loginemail" type="email" name="email" class="form-control" placeholder="Email address" required autofocus/>
                </div>
                <div class="input-group" style="padding: 10px;">
                    <div class="input-group-addon">
                        <i class="glyphicon glyphicon-lock"></i>
                    </div>
                    <input id="loginpasswd" type="password" name="passwd" class="form-control" placeholder="Password" required/>
                </div>
                <div class="input-group" style="padding: 10px;margin: auto;">
                    <input id="submit" class="btn btn-success" type="button" value="登录" style="width: 100px">
                </div>
                <div id = "loginresult"></div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<%--导航兰会在不同页面出现，不能用相对路径--%>
<script src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<%--注销事件--%>
<script>
    $(document).ready(function(){
        $("#logout").click(function() {
            $.get("${pageContext.request.contextPath}/logout",function(result){
                if (result == "success") {
                    window.location.href = "${pageContext.request.contextPath}/";
                }
            });
        });
    });
</script>

<script>
    $("#showModal").click(clearResult);
    function clearResult() {
        $("#loginresult").html("");
    }

</script>

<%--登录模态框里面的事件--%>
<script type="text/javascript">
    $(document).ready(function() {
        function post() {
            $.post($("#loginform").attr("action"), $("#loginform").serialize(), function (result) {
                if (result == "success") {
                    location.reload();
                } else {
                    $("#loginresult").html(result);
                }
            });
        }

        function keydown() {
            if(event.keyCode == 13) {
                post();
            }
        }

        $("#loginemail").keydown(keydown);
        $("#loginpasswd").keydown(keydown);
        $("#submit").click(post);
    });
</script>