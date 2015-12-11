package com.blog.service;

import com.blog.dao.ArticleCategoryDao;
import com.blog.dao.ArticleDao;
import com.blog.dao.CommentDao;
import com.blog.po.Article;
import com.blog.po.Comment;
import com.blog.utils.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by geekgao on 15-10-12.
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleCategoryDao articleCategoryDao;
    @Autowired
    private CommentDao commentDao;

    @Override
    public List<Article> getAllArticle() throws IOException {
        return articleDao.getAllArticle();
    }

    @Override
    public int getAuthorId(int id) throws IOException {
        return articleDao.getAuthorId(id);
    }

    @Override
    public String getTitle(int id) throws IOException {
        return articleDao.getTitle(id);
    }

    @Override
    public Date getTime(int id) throws IOException {
        return articleDao.getTime(id);
    }

    @Override
    public int getReadNum(int id) throws IOException {
        return articleDao.getReadNum(id);
    }

    @Override
    public int getCommentNum(int id) throws IOException {
        return articleDao.getCommentNum(id);
    }

    @Override
    public boolean isDeleted(int id) throws IOException {
        return articleDao.isDeleted(id);
    }

    @Override
    public void insert(Article article) throws IOException {
        articleDao.insert(article);
    }

    @Override
    public int getLatestId() throws IOException {
        return articleDao.getLatestId();
    }

    @Override
    public List<Article> getCommonArticle() throws IOException {
        return articleDao.getCommonArticle();
    }

    @Override
    public List<Article> getDeletedArticle() throws IOException {
        return articleDao.getDeletedArticle();
    }

    @Override
    public void moveToDusbin(int articleId) throws IOException {
        articleDao.moveToDusbin(articleId);
    }

    @Override
    public void delete(int articleId) throws IOException {
        //删除文章的分类信息
        articleCategoryDao.delete(articleId);

        /*删除文章的评论信息*/
        //删除本文章所有的评论的回复
        List<Comment> replies = commentDao.selectRep(articleId);
        for (Comment r:replies) {
            commentDao.delete(r.getId());
        }
        //删除本文章所有的评论
        List<Comment> comments = commentDao.selectCom(articleId);
        for (Comment c:comments) {
            commentDao.delete(c.getId());
        }
        //删除文章
        articleDao.delete(articleId);
    }

    @Override
    public void recover(int articleId) throws IOException {
        articleDao.recover(articleId);
    }

    @Override
    public int getRowCount() throws IOException {
        return articleDao.getRowCount();
    }

    @Override
    public PageParam getPagedArticle(PageParam pageParam) throws IOException {
        int currPage = pageParam.getCurrPage();
        // limit offset, size
        int offset = (currPage - 1) * PageParam.pageSize;
        int size = PageParam.pageSize;
        List<Article> articleList = articleDao.getPagedArticle(offset,size);
        pageParam.setData(articleList);

        return pageParam;
    }

    @Override
    public void setCategory(int id, int[] selectedId) throws IOException {
        articleDao.delCategory(id);
        for (int i = 0; i < selectedId.length;i++){
            articleDao.setCategory(id,selectedId[i]);
        }
    }

    @Override
    public String getPeek(int id) throws IOException {
        return articleDao.getPeek(id);
    }

    @Override
    public void increasePageView(HttpSession session,String articleId) throws IOException {
        //session里面添加[read:1,2,3],代表浏览过id分别为1,2,3的文章
        String read = (String) session.getAttribute("read");
        //没有浏览过任何文章
        if (read == null) {
            session.setAttribute("read",articleId);
        } else {
            //已经阅读过的文章id
            String[] readIds = read.split(",");
            for (String id:readIds) {
                //已经阅读过本篇文章
                if (id.equals(articleId)) {
                    return;
                }
            }
        }
        //到这说明没有浏览过本篇文章
        articleDao.increaseReadNum(articleId);
        if (read == null) {
            session.setAttribute("read",articleId);
        } else {
            session.setAttribute("read",read + "," + articleId);
        }
    }
}
