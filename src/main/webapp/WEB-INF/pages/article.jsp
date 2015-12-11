<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: panlu
  Date: 15-10-21
  Time: 下午10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

    <title>${title}</title>
    <link rel="icon" href="images/favicon.ico">
    <link href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/starter-template.css" rel="stylesheet">

    <link href="css/screen.css" rel="stylesheet">
    <link href="css/background.css" rel="stylesheet">
</head>
<body>
<jsp:include page="nav.jsp"/>

<div class="container">
    <div class="starter-template">
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8" style="background-color: #ffffff;word-wrap: break-word">
                <h1>${title}</h1><br>
                <div>${content}</div><br/>

                <hr/>
                <div class="section_title">
                    <h3><span>评论:</span></h3>
                </div>
                <c:forEach items="${commentsList}" var="t">
                    ${t.user_name} 说:<br/>
                    ${t.content}<br/>
                    ${t.time}<br/>

                    <button class="btn-respond">回复</button>
                    <div id="respond" class="container">
                        <form action="${pageContext.request.contextPath}/editreply" method="post" id="commentform" name="commentform" onsubmit="return mail_test(this);">
                            <ul class="comment-l">
                                <li style="height:28px;line-height: 28px;overflow: hidden">
                                    <label for="content">评论内容：（必填）</label>
                                </li>
                                <li>
                                    <textarea name="content" id="content" tabindex="1" aria-required="true"></textarea>
                                </li>
                                <li class="comment-btn">
                                    <p>
                                        ( Ctrl+Enter快速提交 )&nbsp;&nbsp;&nbsp;
                                        <input name="submit" type="submit" id="submit" value="写好了，发出去！" />
                                    </p>
                                </li>
                            </ul>

                            <ul class="comment-r">
                                <li>
                                    <label for="user_name">你的大名：（必填）</label>
                                </li>
                                <li>
                                    <input type="text" name="user_name" id="user_name" size="25" tabindex="2" aria-required='true' />
                                </li>
                                <li>
                                    <label for="user_email">邮箱地址：(必填)</label>
                                </li>
                                <li>
                                    <input type="text" name="user_email" id="user_email" size="25" tabindex="3" aria-required="true" />
                                </li>
                                <li>
                                    <input type="hidden" name="id" value="<%=request.getParameter("id")%>" />
                                </li>
                                <li>
                                    <input type="hidden" name="comment_id" value="${t.id}" />
                                </li>
                            </ul>
                        </form>
                        <button class="btn-revoke">取消</button>
                    </div>
                    <br/>
                    <c:forEach items="${replyList}" var="tr">
                        <c:if test ="${tr.comment_id==t.id}">
                            ${tr.user_name} 说:<br/>
                            ${tr.content}
                            ${tr.time}<br/>
                        </c:if>
                    </c:forEach>
                    <br/>
                    <br/>
                </c:forEach>

                <div id="respond">
                    <form action="${pageContext.request.contextPath}/editcomment" method="post" id="commentform">
                        <ul class="comment-l">
                            <li style="height:28px;line-height: 28px;overflow: hidden">
                                <label for="content">评论内容：（必填）</label>
                            </li>
                            <li>
                                <textarea name="content" id="content" tabindex="1"></textarea>
                            </li>
                            <li class="comment-btn">
                                <p>
                                    ( Ctrl+Enter快速提交 )&nbsp;&nbsp;&nbsp;&nbsp;
                                    <input name="submit" type="submit" id="submit" tabindex="5" value="写好了，发出去！">
                                </p>
                            </li>
                        </ul>

                        <ul class="comment-r">
                            <li>
                                <label for="user_name">你的大名：（必填）</label>
                            </li>
                            <li>
                                <input type="text" name="user_name" id="user_name" size="25" tabindex="2" aria-required='true'>
                            </li>
                            <li>
                                <label for="user_email">邮箱地址：(必填)</label>
                            </li>
                            <%--<li>--%>
                                <%--<input type="text" name="user_email" size="30">--%>
                            <%--</li>--%>
                            <li>
                                <input type="hidden" name="id" value="<%= request.getParameter("id") %>" />
                            </li>
                            <li>
                                <input type="text" name="user_email" id="user_email" size="25" tabindex="3" aria-required='true'>
                            </li>
                        </ul>
                    </form>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>
    </div>
</div>

<script type="text/javascript">

   //验证邮箱的正确性
function mail_test(thisform) {
    with (thisform) {
    user_email = commentform.user_email.value;
    if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(user_email)) {
        alert("邮箱格式错误");
        commentform.user_email.focus();
        return false
    }else{return true}
    }
}

  $('.btn-respond').each(function(){
    $(this).click(function(){
      $(this).next().css('display', 'block');
    });
  });
  //取消回复
  $('.btn-revoke').each(function(){
    $(this).click(function(){
      $(this).parent().css('display','none');
    });
  });


</script>
</body>
</html>
