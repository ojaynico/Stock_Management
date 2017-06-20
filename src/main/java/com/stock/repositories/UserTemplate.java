package com.stock.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by nick on 6/12/17.
 */
//This class is our repository and is used for data access using JdbcTemplate
    //We annotate it with @Repository to tell our application that this class is a repository
@Repository
public class UserTemplate {

    //Define our template below
    private JdbcTemplate jdbcTemplate;

    //create a constructor to initialize our template
    public UserTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //create a method to change user status
    //method takes two parameters which are id of user and status to set
    public void changeStatus(Integer id, int status){

        //use template to update the fields in the database
        //update is a jdbctemplate build in method
        //we pass in our query in sql format
        jdbcTemplate.update("UPDATE user SET status="+status+" WHERE id="+id);
    }

}
