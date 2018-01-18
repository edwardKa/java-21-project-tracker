package com.company.project.tracker.service;

import com.company.project.tracker.model.entity.User;
import com.company.project.tracker.model.web.UserLoginRequest;
import com.company.project.tracker.model.web.UserLoginResponse;

public interface AuthenticationService {

    User getAuthenticatedUser(String sessionId);

    void authenticate(String sessionId);

    void logout(String sessionId);

    UserLoginResponse loginUser(UserLoginRequest userLoginRequest);
}
