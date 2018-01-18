package com.company.project.tracker.controller;

import com.company.project.tracker.annotation.NoAuthentication;
import com.company.project.tracker.exception.InputValidationException;
import com.company.project.tracker.model.web.RegisterUserRequest;
import com.company.project.tracker.model.web.UserLoginRequest;
import com.company.project.tracker.model.web.UserLoginResponse;
import com.company.project.tracker.service.AuthenticationService;
import com.company.project.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@NoAuthentication
@RequestMapping(value = "/users")
public class EntryController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserLoginResponse registerNewUser(@RequestBody @Valid RegisterUserRequest userRequest,
                                             BindingResult result) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }

        return userService.registerNewUser(userRequest);

    }

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody @Valid UserLoginRequest userLoginRequest,
                                   BindingResult result) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }

        return authenticationService.loginUser(userLoginRequest);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String sessionId) {
        authenticationService.logout(sessionId);
    }
}
