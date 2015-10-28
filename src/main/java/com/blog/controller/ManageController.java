package com.blog.controller;

import com.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by geekgao on 15-10-7.
 * 后台管理页面控制器
 */
@Controller
@RequestMapping("manage")
public class ManageController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "" , method = RequestMethod.GET)
    public void manage(HttpServletResponse response) throws IOException {
        response.sendRedirect("manage/home");
    }

    /**
     * 后台首页
     * @return
     */
    @RequestMapping(value = "home" , method = RequestMethod.GET)
    public String manageHomePage() {
        return "manage/home";
    }

    /**
     * 文章管理页面
     * @return
     */
    @RequestMapping(value = "postlist" , method = RequestMethod.GET)
    public String articleManage() {
        return "manage/postlist";
    }

    /**
     * 写博文页面
     * @return
     */
    @RequestMapping(value = "post" , method = RequestMethod.GET)
    public String postManage(Model model) throws IOException {
        //将文章类别信息传送到前台
        model.addAttribute("categories", categoryService.getAll());
        return "manage/post";
    }
}
