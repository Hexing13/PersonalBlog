package com.blog.mapper;

import com.blog.po.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by geekgao on 15-10-12.
 */
public interface ArticleMapper {
    @Select("select * from article where id = #{id}")
    Article getArticle(int id);

    @Select("select * from article")
    List<Article> getAllArticle();

    @Insert("insert into article values(null,#{author_Id},#{title},#{peek},#{time},#{read_Num},#{comment_Num},#{deleted})")
    void insert(Article article);

    @Select("select max(id) from article")
    int getLatestId();

    //挑选出没有被删除的文章
    @Select("select * from article where deleted = 'n' order by id DESC")
    List<Article> getCommonArticle();

    //挑选出被删除的文章
    @Select("select * from article where deleted = 'y' order by id DESC")
    List<Article> getDeletedArticle();

    @Select("select read_num from article where id=#{articleId}")
    int getReadNum(int articleId);

    @Select("select comment_num from article where id=#{articleId}")
    int getCommentNum(int articleId);

    @Update("update article set deleted='y' where id=#{articleId}")
    void moveToDusbin(int articleId);

    @Delete("delete from article where id=#{articleId}")
    void delete(int articleId);

    @Update("update article set deleted='n' where id=#{articleId}")
    void recover(int articleId);
    //获取总记录数
    @Select("select count(id) from article WHERE deleted = 'n'")
    int getRowCount();
    //获取文章
    @Select("select * from article where deleted = 'n' order by id DESC limit #{offset}, #{size}")
    List<Article> getPagedArticle(@Param("offset") int offset, @Param("size") int size);

    //删除旧的文章分类
    @Delete("delete from article_category where article_id = #{id}")
    void delCategory(int id);

    //设置新的文章分类
    @Insert("insert into article_category(article_id,category_id) values(#{id},#{selectedId})")
    void setCategory(@Param("id") int id, @Param("selectedId") int selectedId);

    @Select("select peek from article where id=#{id}")
    String getPeek(int id);

    @Update("update article set read_num = read_num + 1 where id = #{articleId}")
    void increaseReadNum(String articleId);
}
