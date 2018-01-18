package com.company.project.tracker.service;

import com.company.project.tracker.model.entity.User;

public interface AuthenticationService {

    User getAuthenticatedUser(String sessionId);

    void authenticate(String sessionId);
}
