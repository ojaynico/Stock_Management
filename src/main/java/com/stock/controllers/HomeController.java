package com.stock.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by nick on 5/29/17.
 */
@Controller
public class HomeController {

    @RequestMapping("/home")
    public String home(Model model){
        return "home";
    }

    @RequestMapping("/customers")
    public String customers(Model model){
        return "customers";
    }
}
