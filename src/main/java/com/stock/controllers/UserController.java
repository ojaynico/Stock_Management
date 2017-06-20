package com.stock.controllers;

import com.stock.entities.User;
import com.stock.repositories.UserRepository;
import com.stock.repositories.UserTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by nick on 6/12/17.
 */

//This will handle adding, deleting, updating status and updating user details
    //It has a request mapping of /user
    //It is our controller for handling users
@Controller
@RequestMapping("/user")
public class UserController {

    //We define our repositories here that will help us with data access
    private UserRepository userRepository;
    private UserTemplate userTemplate;

    //We create a constructor for our class that we use for initializing our repositories
    public UserController(UserRepository userRepository, UserTemplate userTemplate){
        this.userRepository = userRepository;
        this.userTemplate = userTemplate;
    }


    //This method will Open our users.html file
    //We also add a list of all the users using model
    //The key for our list is users
    @GetMapping("/list")
    public String userList(Model model){

        //addling list called users to our model
        //We use the findAll() method to query all the users from the database
        model.addAttribute("users", userRepository.findAll());

        //We load our users.html using the line below
        return "users";
    }

    //This method will be used for adding a user to the database
    //We use @RequestParam annotation to get parameters from our form fields
    //We use MultipartFile for files which is a picture in our case
    @PostMapping("/add")
    public String addUser(@RequestParam("code") String code,
                          @RequestParam("name") String name,
                          @RequestParam("contact") String contact,
                          @RequestParam("password") String password,
                          @RequestParam("repassword") String repassword,
                          @RequestParam("role") int role,
                          @RequestParam("picture") MultipartFile picture){

        //We are generating random numbers below
        Random r = new Random();

        //We want to create unique names for images uploaded so we use the random numbers generated
        int pname = r.nextInt(33000000) + r.nextInt(33000000);
        try {

            // Get the filename and build the local file path
            //we generate the name of the image from the random numbers above
            String imagename = String.valueOf(pname);
            //We define a directory where we want to store the image
            String directory = "/home/stock/users";
            //Get the file path and call its toString method
            String filepath = Paths.get(directory, imagename).toString();

            //We Save the file locally
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(picture.getBytes());
            stream.close();

            //We create a new user object and use setter methods to set the user values from the form
            User user = new User();
            user.setCode(code);
            user.setName(name);
            user.setContact(contact);
            user.setPassword(password);
            user.setRepassword(repassword);
            user.setRole(role);
            user.setPicture(imagename);

            //we save the user with all the details that have been provided above and are in the user object
            userRepository.save(user);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            //we redirect to list incase of any  exception
            return "redirect:list";
        }

        //We redirect to the list after inserting a new user is done
        return "redirect:list";
    }


    //The method below is used for changing the status of the user like explained in the previous form
    //We pass in the user id through the GetMapping(status/{id}) and use @PathVariable(id) to get the passed value
    @GetMapping("/status/{id}")
    public String changeStatus(@PathVariable("id") Integer id){
        //We retrieve the users details using findOne(id)
        User user = userRepository.findOne(id);

        //We check if the user's status is 0, we call a method changeStatus(id, 1)
        //If the status is 1 the else part will run
        //The changeStatus method is part of our UserTemplate class, lets look at it
        if (user.getStatus() == 0)
            userTemplate.changeStatus(id, 1);
        else
            userTemplate.changeStatus(id, 0);

        //We redirect to /user/list if all the above is done
        return "redirect:/user/list";
    }


    //The method below is used for deleting a user from the database
    //we pass the id of the user to delete using {} and @PathVariable
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id){

        //We query user details using findAll(id)
        User user = userRepository.findOne(id);

        //We define a file field below to hold details about the user picture
        File file = new File("/home/stock/users/"+user.getPicture());

        //we check if user image exists and delete it from the folder
        if (file.exists()){
            file.delete();
        }

        //we delete the user from the database
        userRepository.delete(id);

        //we redirect to our user/list
        return "redirect:/user/list";
    }
}
