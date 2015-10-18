package com.blog.dao;

import com.blog.mapper.ArticleMapper;
import com.blog.po.Article;
import com.blog.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Date;

/**
 * Created by geekgao on 15-10-12.
 */
@Repository
public class ArticleDaoImpl implements ArticleDao {
    private Article article;

    private Article getArticle(int id) throws IOException {
        SqlSession session = MybatisUtils.getSession();
        try {
            ArticleMapper mapper = session.getMapper(ArticleMapper.class);
            return mapper.getArticle(id);
        } finally {
            session.close();
        }
    }

    @Override
    public int getLatestId() throws IOException {
        SqlSession session = MybatisUtils.getSession();
        try {
            ArticleMapper mapper = session.getMapper(ArticleMapper.class);
            return mapper.getLatestId();
        } finally {
            session.close();
        }
    }

    @Override
    public int getAuthorId(int id) throws IOException {
        article = getArticle(id);
        return article.getAuthorId();
    }

    @Override
    public String getTitle(int id) throws IOException {
        article = getArticle(id);
        return article.getTitle();
    }

    @Override
    public Date getTime(int id) throws IOException {
        article = getArticle(id);
        return article.getTime();
    }

    @Override
    public int getReadNum(int id) throws IOException {
        article = getArticle(id);
        return article.getReadNum();
    }

    @Override
    public int getCommentNum(int id) throws IOException {
        article = getArticle(id);
        return article.getCommentNum();
    }

    @Override
    public String getDeleted(int id) throws IOException {
        article = getArticle(id);
        return article.getDeleted();
    }

    @Override
    public void insert(Article article) throws IOException {
        SqlSession session = MybatisUtils.getSession();
        try {
            ArticleMapper mapper = session.getMapper(ArticleMapper.class);
            mapper.insert(article);
            session.commit();
        } finally {
            session.close();
        }
    }
}