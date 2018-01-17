package com.company.project.tracker.service;

import com.company.project.tracker.model.entity.User;
import com.company.project.tracker.model.web.RegisterUserRequest;
import com.company.project.tracker.model.web.UserLoginRequest;
import com.company.project.tracker.model.web.UserLoginResponse;
import com.company.project.tracker.model.web.UserUpdateRequest;

import java.util.List;

public interface UserService {

    UserLoginResponse registerNweUser(RegisterUserRequest registerUserRequest);
    User getById(Long userId);
    List<User> getByLastName(String lastName);
    User updateUserById(Long userId, UserUpdateRequest userUpdateRequest);
    UserLoginResponse loginUser(UserLoginRequest userLoginRequest);
    void logout(String sessionId);
}
