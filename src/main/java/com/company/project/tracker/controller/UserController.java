package com.company.project.tracker.controller;

import com.company.project.tracker.exception.InputValidationException;
import com.company.project.tracker.model.entity.User;
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


    @GetMapping(value = "/{userId}")
    public User getUserById(@PathVariable("userId") Long userId) {
        return userService.getById(userId);
    }

    @GetMapping(value = "/")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PutMapping(value = "/{userId}")
    public User updateUser(@PathVariable("userId") Long userId,
                           @RequestBody @Valid UserUpdateRequest userUpdateRequest,
                           BindingResult result) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }

        return userService.updateUserById(userId, userUpdateRequest);
    }





}
