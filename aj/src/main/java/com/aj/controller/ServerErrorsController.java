package com.aj.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Controller
public class ServerErrorsController {
	@RequestMapping("/404.html")
    public String render404() {
        // Model attributes
        return "404";
    }

	@Configuration
	public class WebConfig extends WebMvcConfigurerAdapter {
	    @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        registry.addViewController("/404.html").setViewName("404");
	    }
	}
/*
	@RequestMapping(value = "/error") public String error404() {
		return "error/404";
	}
	
*/
	@RequestMapping("/error")
    public String handleError() {
        //do something like logging
        return "error";
    }
/*
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	    
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	            return "error-404";
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	            return "error-500";
	        }
	    }
	    return "error";
	}*/
}