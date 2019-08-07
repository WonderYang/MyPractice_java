package com.yy.controller;

import com.yy.po.User;
import com.yy.service.ServiceUser;
import org.springframework.stereotype.Controller;
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

    public ModelAndView querryAll() {
        ServiceUser serviceUser = new ServiceUser();
        ModelAndView modelAndView = new ModelAndView();
        List<User> list = new ArrayList<>();


        return modelAndView;
    }
}
