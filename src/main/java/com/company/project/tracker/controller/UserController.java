package com.company.project.tracker.controller;

import com.company.project.tracker.exception.InputValidationException;
import com.company.project.tracker.model.entity.User;
import com.company.project.tracker.model.web.RegisterUserRequest;
import com.company.project.tracker.model.web.UserLoginRequest;
import com.company.project.tracker.model.web.UserLoginResponse;
import com.company.project.tracker.model.web.UserUpdateRequest;
import com.company.project.tracker.repository.UserRepository;
import com.company.project.tracker.service.AuthenticationService;
import com.company.project.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    private void authenticate(String sessionId) {
//        authenticationService.getAuthenticatedUser(sessionId);
    }

    @GetMapping(value = "/{userId}")
    public User getUserById(@PathVariable("userId") Long userId,
                            @RequestHeader("Authorization") String sessionId) {
        authenticate(sessionId);
        return userService.getById(userId);
    }

    @GetMapping(value = "/")
    public List<User> getAllUsers(@RequestHeader("Authorization") String sessionId) {
        authenticate(sessionId);
        return userRepository.findAll();
    }

    @PutMapping(value = "/{userId}")
    public User updateUser(@PathVariable("userId") Long userId,
                           @RequestBody @Valid UserUpdateRequest userUpdateRequest,
                           BindingResult result,
                           @RequestHeader("Authorization") String sessionId) {
        authenticate(sessionId);
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }

        return userService.updateUserById(userId, userUpdateRequest);
    }

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

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String sessionId) {
        authenticate(sessionId);
        userService.logout(sessionId);
    }

}
