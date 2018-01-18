package com.company.project.tracker.controller;

import com.company.project.tracker.annotation.NoAuthentication;
import com.company.project.tracker.exception.InputValidationException;
import com.company.project.tracker.model.web.RegisterUserRequest;
import com.company.project.tracker.model.web.UserLoginRequest;
import com.company.project.tracker.model.web.UserLoginResponse;
import com.company.project.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@NoAuthentication
@RequestMapping(value = "/users")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserLoginResponse registerNewUser(@RequestBody @Valid RegisterUserRequest userRequest,
                                             BindingResult result) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }

        return userService.registerNweUser(userRequest);

    }

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody @Valid UserLoginRequest userLoginRequest,
                                   BindingResult result) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }

        return userService.loginUser(userLoginRequest);
    }
}
