package com.stock.controllers;

import com.stock.entities.Product;
import com.stock.repositories.ProductRepositories;
import com.stock.repositories.ProductTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

/**
 * Created by nick on 6/26/17.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepositories productRepositories;
    @Autowired
    private ProductTemplate productTemplate;

    @GetMapping("/list")
    public String productList(Product addproduct, Model model){
        model.addAttribute("products", productRepositories.findAll());
        model.addAttribute("addproduct", addproduct);
        return "products";
    }

    @PostMapping("/add")
    public String addProduct(@Valid Product addproduct){
        if (productRepositories.save(addproduct) != null){
            return "redirect:/product/list";
        } else
            return "redirect:/product/list";
    }

    @PostMapping("/edit")
    public String editProduct(@RequestParam("id") Integer id,
                              @RequestParam("code_product") String code_product,
                              @RequestParam("name") String name,
                              @RequestParam("quantity") Integer quantity,
                              @RequestParam("unit_price") Double unit_price){
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setCode_product(code_product);
        product.setUnit_price(unit_price);
        product.setQuantity(quantity);

        if (productRepositories.save(product) != null){
            return "redirect:/product/list";
        } else {
            return "redirect:/product/list";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id){
        productRepositories.delete(id);
        return "redirect:/product/list";
    }

}
