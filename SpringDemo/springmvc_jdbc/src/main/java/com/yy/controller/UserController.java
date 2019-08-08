package com.yy.controller;

import com.yy.po.User;
import com.yy.service.ServiceUser;
import com.yy.service.ServiceUserImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 19:26 2019/8/7
 */
@Controller
public class UserController {
    @RequestMapping("/spring")
    public ModelAndView querryAll() throws Exception{
        ServiceUser serviceUser = new ServiceUserImpl();
        ModelAndView modelAndView = new ModelAndView();
        List<User> list = new ArrayList<>();
        list = serviceUser.getUserList();

        //相当于servlet中的request.setAttribute(key,value);
        modelAndView.addObject("userListKey", list);
        //相当于request.getRequestDispatcher("showItemsList.jsp").forward(request,response);
        modelAndView.setViewName("showuser");

        return modelAndView;
    }
}
