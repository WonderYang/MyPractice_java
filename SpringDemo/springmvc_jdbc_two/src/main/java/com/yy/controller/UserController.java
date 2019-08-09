package com.yy.controller;

import com.yy.po.User;
import com.yy.service.ServiceUser;
import com.yy.service.ServiceUserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : YangY
 * @Description :  通过注解的方式来实现处理器，结合它的配置文件为：
 *                                                              applicationContext.xml
 *                       属性的注入也是通过注解
 * @Time : Created in 19:26 2019/8/7
 */
@Controller
public class UserController {

    @Autowired //使用该注解来注入属性，前提是能找到这种类
    private ServiceUserImpl serviceUser;

    @RequestMapping("/spring")
    public ModelAndView querryAll() throws Exception{

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
