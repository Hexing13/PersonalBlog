package com.blog.dao;

import com.blog.mapper.ArticleCategoryMapper;
import com.blog.po.ArticleCategory;
import com.blog.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * Created by geekgao on 15-10-18.
 */
@Repository
public class ArticleCategoryDaoImp implements ArticleCategoryDao {
    @Override
    public void insert(ArticleCategory articleCategory) throws IOException {
        SqlSession session = MybatisUtils.getSession();
        try {
            ArticleCategoryMapper mapper = session.getMapper(ArticleCategoryMapper.class);
            mapper.insert(articleCategory);
            session.commit();
        } finally {
            session.close();
        }
    }
}