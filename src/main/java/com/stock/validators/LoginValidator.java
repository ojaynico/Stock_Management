package com.stock.validators;

import com.stock.entities.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by nick on 6/8/17.
 */
//class is used for validating our form fields using our User class
public class LoginValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (user.getCode().length() == 0){
            errors.rejectValue("code", "user.code", "Username is required");
        }

        if (user.getPassword().length() == 0){
            errors.rejectValue("password", "user.password", "Password is required");
        }
    }
}
