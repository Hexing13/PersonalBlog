package com.blog.service;

import com.blog.mapper.ArticleCategoryMapper;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.CommentMapper;
import com.blog.po.Article;
import com.blog.po.Comment;
import com.blog.utils.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Article> getAllArticle() throws IOException {
        return articleMapper.getAllArticle();
    }

    @Override
    public int getAuthorId(int id) throws IOException {
        return articleMapper.getArticle(id).getAuthor_Id();
    }

    @Override
    public String getTitle(int id) throws IOException {
        return articleMapper.getArticle(id).getTitle();
    }

    @Override
    public Date getTime(int id) throws IOException {
        return articleMapper.getArticle(id).getTime();
    }

    @Override
    public int getReadNum(int id) throws IOException {
        return articleMapper.getReadNum(id);
    }

    @Override
    public int getCommentNum(int id) throws IOException {
        return articleMapper.getCommentNum(id);
    }

    @Override
    public boolean isDeleted(int id) throws IOException {
        return articleMapper.getArticle(id).getDeleted().equals("y");
    }

    @Override
    public void insert(Article article) throws IOException {
        articleMapper.insert(article);
    }

    @Override
    public int getLatestId() throws IOException {
        return articleMapper.getLatestId();
    }

    @Override
    public List<Article> getCommonArticle() throws IOException {
        return articleMapper.getCommonArticle();
    }

    @Override
    public List<Article> getDeletedArticle() throws IOException {
        return articleMapper.getDeletedArticle();
    }

    @Override
    public void moveToDusbin(int articleId) throws IOException {
        articleMapper.moveToDusbin(articleId);
    }

    @Override
    public void delete(int articleId) throws IOException {
        //删除文章的分类信息
        articleCategoryMapper.delete(articleId);

        /*删除文章的评论信息*/
        //删除本文章所有的评论的回复
        List<Comment> replies = commentMapper.selectRep(articleId);
        for (Comment r:replies) {
            commentMapper.delete(r.getId());
        }
        //删除本文章所有的评论
        List<Comment> comments = commentMapper.selectCom(articleId);
        for (Comment c:comments) {
            commentMapper.delete(c.getId());
        }
        //删除文章
        articleMapper.delete(articleId);
    }

    @Override
    public void recover(int articleId) throws IOException {
        articleMapper.recover(articleId);
    }

    @Override
    public int getRowCount() throws IOException {
        return articleMapper.getRowCount();
    }

    @Override
    public PageParam getPagedArticle(PageParam pageParam) throws IOException {
        int currPage = pageParam.getCurrPage();
        // limit offset, size
        int offset = (currPage - 1) * PageParam.pageSize;
        int size = PageParam.pageSize;
        List<Article> articleList = articleMapper.getPagedArticle(offset,size);
        pageParam.setData(articleList);

        return pageParam;
    }

    @Override
    public void setCategory(int id, int[] selectedId) throws IOException {
        articleMapper.delCategory(id);
        for (int i = 0; i < selectedId.length;i++){
            articleMapper.setCategory(id,selectedId[i]);
        }
    }

    @Override
    public String getPeek(int id) throws IOException {
        return articleMapper.getPeek(id);
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
        articleMapper.increaseReadNum(articleId);
        if (read == null) {
            session.setAttribute("read",articleId);
        } else {
            session.setAttribute("read",read + "," + articleId);
        }
    }
}
