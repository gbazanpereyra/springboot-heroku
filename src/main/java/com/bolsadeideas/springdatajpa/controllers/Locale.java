package com.bolsadeideas.springdatajpa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Locale {

    @GetMapping("/locale")
    public String locale(HttpServletRequest request) {
        String ultimaUrl = request.getHeader("referer");
        return "redirect:".concat(ultimaUrl);
    }
}
