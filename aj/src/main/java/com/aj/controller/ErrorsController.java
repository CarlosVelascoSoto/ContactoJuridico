package com.aj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aj.spring.IntrovertException;

@Controller
public class ErrorsController {

    @RequestMapping("/hello")
    String hello() {
        throw new IntrovertException("Don't bother me please..!");
    }
}