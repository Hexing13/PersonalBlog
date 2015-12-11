package com.blog.controller;

import com.blog.dao.ArticleDaoImpl;
import com.blog.po.Article;
import com.blog.po.Comment;
import com.blog.service.ArticleService;
import com.blog.service.CommentService;
import com.blog.utils.BlogUtils;
import com.blog.utils.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by panlu on 15-10-22.
 */
@Controller
public class ComPostController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDaoImpl articleDao;

    @RequestMapping(value = "editcomment" ,method = RequestMethod.POST)
    public void editComment(HttpServletResponse respone, @RequestParam("id") String id, @RequestParam("content") String content, @RequestParam("user_name") String user_name, @RequestParam("user_email") String user_email) throws IOException{
        if (content.equals("") || user_email.equals("") || user_name.equals("")) {
            respone.sendRedirect("article?id=" + id);
            return;
        }
        //判断邮箱的正则表达式
        String checkemail = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(checkemail);
        Matcher matcher = regex.matcher(user_email);
        if (matcher.matches()==false){
            respone.sendRedirect("article?id=" + id);
            return;
        }

        Integer idact = Integer.parseInt(id);
        Date curtime = new Date(System.currentTimeMillis());
        synchronized (ComPostController.class){
            //首先是对一篇文章的评论
            Comment commentpojo = new Comment(0,user_name,user_email,curtime,content,"a",idact,0);
            commentService.insertArt(commentpojo);
        }
        respone.sendRedirect("article?id=" + id);
    }

    //拦截对评论的回复(刚开始默认的是对10号评论的回复)
    @RequestMapping(value = "editreply", method = RequestMethod.POST)
    public void editReply(HttpServletResponse respone, @RequestParam("id") String id,@RequestParam("content") String content, @RequestParam("user_name") String user_name, @RequestParam("user_email") String user_email,@RequestParam("comment_id") String comment_id) throws IOException {
        if (content.equals("") || user_email.equals("") || user_name.equals("")) {
            respone.sendRedirect("article?id=" + id);
            return;
        }
        int idarc = Integer.parseInt(id);
        Date curtime = new Date(System.currentTimeMillis());
        synchronized (ComPostController.class) {
            Integer rcomment_id = Integer.parseInt(comment_id);
            Comment commentpojo = new Comment(0,user_name,user_email,curtime,content,"c",idarc,rcomment_id);
            commentService.insertCom(commentpojo);
        }
        respone.sendRedirect("article?id=" + id);
    }


    @RequestMapping(value = "/article",method = RequestMethod.GET)
    public String showCom(HttpSession session,Model model,@RequestParam("id") String id) throws IOException{
        int idarc = Integer.parseInt(id);
        List<Comment> commentsList;
        List<Comment> replyList;

        //增加文章浏览量
        articleService.increasePageView(session,id);

        synchronized (ComPostController.class) {
            commentsList = commentService.selectCom(idarc);  //将所有的评论查找出来

            replyList = commentService.selectRep(idarc);
        }
        model.addAttribute("commentsList", commentsList);     //传过去两个List<Comment>  一个是所有评论的，另一个是所有对评论的回复
        model.addAttribute("replyList", replyList);

        //把文章传入前台页面
        BufferedReader readArticle = new BufferedReader(new FileReader(BlogUtils.getArticleFile(idarc)));
        StringBuilder content = new StringBuilder();
        while (readArticle.ready()) {
            content.append(readArticle.readLine());
        }
        //传入文章内容和标题
        model.addAttribute("content", content);
        model.addAttribute("title", articleService.getTitle(idarc));

        return "article";
    }

    @RequestMapping(value = "/manage/deletecomment", method = RequestMethod.POST)
    public @ResponseBody String setArticle(@RequestParam int id,@RequestParam int page,HttpServletRequest request)throws IOException{
//        String Iid = request.getParameter("delete");
//        int IDid = Integer.parseInt(id);
//        System.out.println("delete: "+Iid);
        commentService.deleteReply(id);          //首先删除所有对该评论的回复再去删除评论
        commentService.deleteCom(id);
        return "success";
    }

    @RequestMapping(value = "/manage/getcomment" ,method = RequestMethod.GET)
    public String getcomment(Model model,HttpServletRequest request) throws IOException {
        List<Article> artlist;                //将所有的评论筛选出来
        String currPageStr = request.getParameter("page");
        int currPage = 1;
        currPage = Integer.parseInt(currPageStr);
        int totlerow = commentService.getCommentRow();    //总数
        if (totlerow == 0){
            return "manage/nocomment";
        }else {
            PageParam pgm = new PageParam(); //传过去的就是一个页面
            pgm.setRowCount(totlerow);
            if (pgm.getTotalPage() < currPage){
                currPage = pgm.getTotalPage();
            }
            pgm.setCurrPage(currPage);
            pgm = commentService.pageOfComment(pgm);
//            artlist = articleService.getCommonArticle();
            artlist = articleService.getAllArticle();
            model.addAttribute("artlist",artlist);              //文章列表
            model.addAttribute("onepagedate", pgm.getDatacom());
            request.setAttribute("pageParam", pgm);
            List<Article> article = articleService.getDeletedArticle();
            return "manage/comment";
        }
    }
}
