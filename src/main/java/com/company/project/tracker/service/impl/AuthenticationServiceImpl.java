package com.company.project.tracker.service.impl;

import com.company.project.tracker.exception.AuthenticationException;
import com.company.project.tracker.model.entity.User;
import com.company.project.tracker.model.entity.UserSession;
import com.company.project.tracker.repository.UserSessionRepository;
import com.company.project.tracker.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    public User getAuthenticatedUser(String sessionId) {
        UserSession bySessionId = userSessionRepository.findBySessionId(sessionId);
        if (bySessionId == null) {
            throw new AuthenticationException("User is not authenticated");
        }

        return bySessionId.getUser();
    }
}
