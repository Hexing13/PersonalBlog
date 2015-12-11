package com.blog.controller;

import com.blog.po.Article;
import com.blog.po.ArticleCategory;
import com.blog.service.ArticleCategoryService;
import com.blog.service.ArticleService;
import com.blog.utils.BlogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by geekgao on 15-10-12.
 */
@Controller
public class PostController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    /**
     * 提交博文表单验证
     * @param session
     * @param title 博客标题
     * @param content 内容
     * @param categoryIds 类别的id,多个类别用逗号分开
     * @throws IOException
     */
    @RequestMapping(value = "postedit" ,method = RequestMethod.POST)
    public @ResponseBody String postEdit(HttpSession session, @RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("plainContent") String plainContent, @RequestParam("categoryIds") String categoryIds) throws IOException {
        //根据session中的level判断是不是管理员
        String level = (String) session.getAttribute("level");
        //是管理员,调用服务将文章写入数据库
        if (level != null && level.equals("0")) {
            //不能为空
            if (title.equals("") || content.equals("")) {
                return "paramWrong";
            }

            Date nowTime = new Date(System.currentTimeMillis());

            //判断文章预览内容的长度
            if (plainContent.length() > 100) {
                plainContent = plainContent.substring(0,100);
            }

            //判断题目的长度
            if (title.length() > 50) {
                title = title.substring(0,50);
            }

            //确保获得到的最新存储的文章id和内容一致
            synchronized (PostController.class) {
                articleService.insert(new Article((Integer) session.getAttribute("id"),title, plainContent, nowTime,0,0,"n"));
                //获得刚存储的文章的id
                int latestId = articleService.getLatestId();
                //存储文章和类别的关联信息
                if (categoryIds.equals("")) {
                    //1号分类默认是“未分类”
                    articleCategoryService.insert(new ArticleCategory(latestId, 1));
                } else {
                    String categoryId[] = categoryIds.split(",");
                    for (String id:categoryId) {
                        articleCategoryService.insert(new ArticleCategory(latestId, Integer.valueOf(id)));
                    }
                }
                //文章存储到本地
                saveArticleToDisk(latestId, content);
            }

        }
        return "success";
    }

    /**
     * 按照文章id把文章存储到本地
     * @param latestId 最新的文章id
     * @param content 文章内容
     */
    private void saveArticleToDisk(int latestId, String content) throws IOException {

        //获取存储文章文件的路径
        String articleFolderPath = BlogUtils.getArticleFolderPath();

        //尝试创建文件夹
        File folder = new File(articleFolderPath);
        folder.mkdirs();

        BufferedWriter writer = new BufferedWriter(new FileWriter(articleFolderPath + latestId));
        writer.write(content);
        writer.close();
    }
}