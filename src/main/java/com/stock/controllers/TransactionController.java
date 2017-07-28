package com.stock.controllers;

import com.stock.repositories.ProductRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private ProductRepositories productRepositories;

    @RequestMapping("")
    public String transaction(Model model){
        model.addAttribute("products", productRepositories.findAll());
        return "transaction";
    }


}
