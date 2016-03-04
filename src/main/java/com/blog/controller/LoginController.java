package com.blog.controller;

import com.blog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by geekgao on 2015/10/6.
 * 管理员登录后台控制器
 */

@Controller
public class LoginController {

    @Autowired
    private AdminService adminService;

    /**
     * 登录页面
     * @return
     */
    @RequestMapping(value = "login" , method = RequestMethod.GET)
    public String login(HttpSession session,HttpServletResponse response) throws IOException {
        String flag = (String) session.getAttribute("flag");
        //如果已经等录，就提示
        if (flag != null && flag.equals("on")) {
            response.sendRedirect("reLogin");
        }
        return "login";
    }

    /**
     * 登录页面
     * @return
     */
    @RequestMapping(value = "logout" , method = RequestMethod.GET)
    public @ResponseBody String logout(HttpSession session) throws IOException {
        String flag = (String) session.getAttribute("flag");
        //如果没有登录就不处理
        if (flag == null) {
            return "false";
        }

        session.invalidate();
        return "success";
    }

    /**
     * 登录表单验证
     * @param session
     * @param response
     * @param email
     * @param passwd
     * @throws IOException
     */
    @RequestMapping(value = "loginVerify" , method = RequestMethod.POST)
    public @ResponseBody String verify(HttpSession session, HttpServletResponse response, @RequestParam("email") String email, @RequestParam("passwd") String passwd) throws IOException {
        //如果帐号信息正确,session中加入“已经等录,帐号等级,email帐号,昵称3条信息”
        response.setContentType("text/html; charset=UTF-8");
        if (adminService.isCorrect(email,passwd)) {
            //标记是否登录
            session.setAttribute("flag","on");
            session.setAttribute("level","0");
            session.setAttribute("email",email);
            session.setAttribute("nickname",adminService.getNickName(email));
            session.setAttribute("id",adminService.getId(email));
            return "success";
        } else {
            return "用户名或密码错误!";
        }
    }

}
