package com.tis.controller;

import com.tis.bean.User;
import com.tis.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public String login(User user, HttpSession session, Model model)
    {
        User user1 = userService.get(user);
        if (user1 != null)
        {
            if (user1.getType().equals(0))
            {
                session.setAttribute("student",user1);
                return "/student/main";
            }
            if (user1.getType().equals(1))
            {
                session.setAttribute("teacher",user1);
                return "/teacher/main";
            }
        }
        model.addAttribute("error_msg","用户名或密码错误");
        return "/login";
    }
}
