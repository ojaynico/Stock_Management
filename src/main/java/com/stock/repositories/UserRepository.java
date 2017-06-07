package com.stock.repositories;

import com.stock.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by nick on 6/8/17.
 */
//This interface is used to communicate with our database and perform queries
public interface UserRepository extends JpaRepository<User, Integer> {

    //same as select * from user where code=? and password=?
    User findUserByCodeAndPassword(String code, String password);
}
