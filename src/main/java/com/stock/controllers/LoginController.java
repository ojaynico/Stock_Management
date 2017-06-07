package com.stock.controllers;

import com.stock.entities.User;
import com.stock.repositories.UserRepository;
import com.stock.validators.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by nick on 6/8/17.
 */
@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    HttpSession session;

    //loads the html login page
    @GetMapping("/")
    public String index(User user) {
        return "login";
    }


    //Performs login
    @PostMapping("/login")
    public String login(@Valid User user, BindingResult result, Model model) {

        //Checks for errors in the form
        if (!result.hasErrors()) {

            //queries for user from our database
            User find = userRepository.findUserByCodeAndPassword(user.getCode(), user.getPassword());

            //checks if user exists
            if (find != null) {

                //checks if user is authorised by admin
                if (find.getStatus() != 0) {

                    session.setAttribute("userid", find.getId());
                    session.setAttribute("role", find.getRole());

                    //loads the home page if login success
                    return "redirect:/home";
                } else {
                    model.addAttribute("error", "You Are Not Authorised");
                    model.addAttribute("user", new User());
                    return "login";
                }
            } else {
                model.addAttribute("error", "Invalid Username or Password");
                model.addAttribute("user", new User());
                return "login";
            }

        } else {
            return "login";
        }
    }

    @InitBinder
    public void loginValidator(WebDataBinder binder) {
        binder.addValidators(new LoginValidator());
    }

}
