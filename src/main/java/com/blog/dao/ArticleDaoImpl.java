package com.blog.dao;

import com.blog.mapper.ArticleMapper;
import com.blog.po.Article;
import com.blog.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
    public List<Article> getAllArticle() throws IOException {
        SqlSession session = MybatisUtils.getSession();
        try {
            ArticleMapper mapper = session.getMapper(ArticleMapper.class);
            return mapper.getAllArticle();
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
    public List<Article> getCommonArticle() throws IOException {
        SqlSession session = MybatisUtils.getSession();
        try {
            ArticleMapper mapper = session.getMapper(ArticleMapper.class);
            return mapper.getCommonArticle();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Article> getDeletedArticle() throws IOException {
        SqlSession session = MybatisUtils.getSession();
        try {
            ArticleMapper mapper = session.getMapper(ArticleMapper.class);
            return mapper.getDeletedArticle();
        } finally {
            session.close();
        }
    }

    @Override
    public int getAuthorId(int id) throws IOException {
        article = getArticle(id);
        return article.getAuthor_Id();
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
        return article.getRead_Num();
    }

    @Override
    public int getCommentNum(int id) throws IOException {
        article = getArticle(id);
        return article.getComment_Num();
    }

    @Override
    public boolean isDeleted(int id) throws IOException {
        article = getArticle(id);
        String deleted = article.getDeleted();
        if (deleted.equals("y")) {
            return true;
        }
        return false;
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

    @Override
    public void moveToDusbin(int articleId) throws IOException {
        SqlSession session = MybatisUtils.getSession();
        try {
            ArticleMapper mapper = session.getMapper(ArticleMapper.class);
            mapper.moveToDusbin(articleId);
            session.commit();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(int articleId) throws IOException {
        SqlSession session = MybatisUtils.getSession();
        try {
            ArticleMapper mapper = session.getMapper(ArticleMapper.class);
            mapper.delete(articleId);
            session.commit();
        } finally {
            session.close();
        }
    }

    @Override
    public void recover(int articleId) throws IOException {
        SqlSession session = MybatisUtils.getSession();
        try {
            ArticleMapper mapper = session.getMapper(ArticleMapper.class);
            mapper.recover(articleId);
            session.commit();
        } finally {
            session.close();
        }
    }

    @Override
    public int getRowCount() throws IOException {
        SqlSession sqlSession = MybatisUtils.getSession();
        try {
            ArticleMapper articleMapper = sqlSession.getMapper(ArticleMapper.class);
            int row = articleMapper.getRowCount();
            sqlSession.commit();
            return row;
        }finally {
            sqlSession.close();
        }

    }

    @Override
    public List<Article> getPagedArticle(int offset, int size) throws IOException {
        SqlSession sqlSession = MybatisUtils.getSession();
        try {
            ArticleMapper articleMapper = sqlSession.getMapper(ArticleMapper.class);
            List<Article> list = articleMapper.getPagedArticle(offset,size);
            sqlSession.commit();
            return list;
        }finally {
            sqlSession.close();
        }
    }

    @Override
    public void delCategory(int id) throws IOException {
        SqlSession sqlSession = MybatisUtils.getSession();
        try {
            ArticleMapper articleMapper = sqlSession.getMapper(ArticleMapper.class);
            articleMapper.delCategory(id);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    @Override
    public void setCategory(int id, int selectedId) throws IOException {
        SqlSession sqlSession = MybatisUtils.getSession();
        try {
            ArticleMapper articleMapper = sqlSession.getMapper(ArticleMapper.class);
            articleMapper.setCategory(id,selectedId);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    @Override
    public String getPeek(int id) throws IOException {
        SqlSession sqlSession = MybatisUtils.getSession();
        try {
            ArticleMapper articleMapper = sqlSession.getMapper(ArticleMapper.class);
            return articleMapper.getPeek(id);
        }finally {
            sqlSession.close();
        }
    }

    @Override
    public void increaseReadNum(String articleId) throws IOException {
        SqlSession sqlSession = MybatisUtils.getSession();
        try {
            ArticleMapper articleMapper = sqlSession.getMapper(ArticleMapper.class);
            articleMapper.increaseReadNum(articleId);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }
}
