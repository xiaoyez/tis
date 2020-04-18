package com.tis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PageController {

    @GetMapping("/company/**")
    public String toCompanyPage(HttpServletRequest request)
    {
        String url = request.getRequestURL().toString();
        url = url.substring(url.indexOf("/","https://".length()));
        return url;
    }

    @GetMapping("/{page}")
    public String toPage(@PathVariable("page") String page)
    {
        return page;
    }
//
//
@GetMapping("/member/**")
public String toMemberPage(HttpServletRequest request)
{
    String url = request.getRequestURL().toString();
    url = url.substring(url.indexOf("/","https://".length()));
    return url;
}

    @GetMapping("/admin/**")
    public String toAdminPage(HttpServletRequest request)
    {
        String url = request.getRequestURL().toString();
        url = url.substring(url.indexOf("/","https://".length()));
        return url;
    }

    @GetMapping("/student/**")
    public String toStudentPage(HttpServletRequest request)
    {
        String url = request.getRequestURL().toString();
        url = url.substring(url.indexOf("/","https://".length()));
        return url;
    }
}
