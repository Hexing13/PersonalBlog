package com.blog.service;

import com.blog.po.ArticleCategory;

import java.io.IOException;

/**
 * Created by geekgao on 15-10-18.
 */
public interface ArticleCategoryService {
    void insert(ArticleCategory articleCategory) throws IOException;
}