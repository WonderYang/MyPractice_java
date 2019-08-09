package com.yy.controller;



import com.yy.po.User;
import com.yy.service.ServiceUser;
import com.yy.service.ServiceUserImpl;
import com.yy.service.ServiceUserImpl_two;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : YangY
 * @Description : 通过配置的方式来实现处理器，结合它的配置文件是：
 *                              applicationContext_two.xml和applicationContext_beans.xml
 *                              属性的注入也是通过配置来实现
 * @Time : Created in 17:33 2019/8/9
 */
//注意有两种Controller类，注意选择正确
public class UserController_two implements Controller {
    //通过配置的方式注入该属性，必须设置其setter方法才能在配置文件中注入
    private ServiceUserImpl_two serviceUserImpl_two;

    public ServiceUserImpl_two getServiceUserImpl_two() {
        return serviceUserImpl_two;
    }

    public void setServiceUserImpl_two(ServiceUserImpl_two serviceUserImpl_two) {
        this.serviceUserImpl_two = serviceUserImpl_two;
    }



    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        List<User> list = new ArrayList<>();
        list = serviceUserImpl_two.getUserList();

        //相当于servlet中的request.setAttribute(key,value);
        modelAndView.addObject("userListKey", list);
        //相当于request.getRequestDispatcher("showItemsList.jsp").forward(request,response);
        modelAndView.setViewName("showuser");

        return modelAndView;
    }
}
