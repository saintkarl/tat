package com.retirement.tat.web.controller;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (c) 2015 by Ban Vien Co., Ltd. All rights reserved.
 * User: viennh
 * Date: 8/27/15
 * Time: 4:53 PM
 */
@Controller
public class HomeController extends ApplicationObjectSupport {

    @RequestMapping(value={"/login.html"})
    public ModelAndView adminLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mav = new ModelAndView("/login");
        return mav;
    }

    @RequestMapping(value={"/admin/home.html"})
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/home");
        return mav;
    }


    @RequestMapping(value={"/", "/index", "/home.html", "/public/home.html"})
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/home");
        return mav;
    }

}
