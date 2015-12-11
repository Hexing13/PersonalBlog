package com.blog.dao;

import com.blog.po.Article;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by geekgao on 15-10-12.
 */
public interface ArticleDao {
    /**
     * 获取最新的文章的id
     * @return
     */
    int getLatestId() throws IOException;

    int getAuthorId(int id) throws IOException;

    List<Article> getAllArticle()throws IOException;
    /**
     * 获取没有被删除的正常文章
     * @return
     */
    List<Article> getCommonArticle() throws IOException;

    List<Article> getDeletedArticle() throws IOException;

    String getTitle(int id) throws IOException;

    Date getTime(int id) throws IOException;

    int getReadNum(int id) throws IOException;

    int getCommentNum(int id) throws IOException;

    boolean isDeleted(int id) throws IOException;

    void insert(Article article) throws IOException;

    void moveToDusbin(int articleId) throws IOException;

    void delete(int articleId) throws IOException;

    void recover(int articleId) throws IOException;

    int getRowCount() throws IOException;

    List<Article> getPagedArticle(int offset, int size) throws IOException;

    void delCategory(int id) throws IOException;

    void setCategory(int id, int selectedId) throws IOException;

    String getPeek(int id) throws IOException;

    void increaseReadNum(String articleId) throws IOException;
}
