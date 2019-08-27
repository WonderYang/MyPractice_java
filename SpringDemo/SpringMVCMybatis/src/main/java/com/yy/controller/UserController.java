package com.yy.controller;

import com.yy.po.User;
import com.yy.service.UserService;
import com.yy.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 12:10 2019/8/27
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/showUserList")
    public ModelAndView userController() throws Exception{

        List<User> userList =  userService.queryUserListService();

        ModelAndView modelAndView  = new ModelAndView();
        modelAndView.addObject("userListKey",userList);
        modelAndView.setViewName("showuser");
        return modelAndView;
    }
}
