package com.company.project.tracker.service.impl;

import com.company.project.tracker.exception.AuthenticationException;
import com.company.project.tracker.model.entity.User;
import com.company.project.tracker.model.entity.UserLogin;
import com.company.project.tracker.model.entity.UserSession;
import com.company.project.tracker.model.web.UserLoginRequest;
import com.company.project.tracker.model.web.UserLoginResponse;
import com.company.project.tracker.repository.UserLoginRepository;
import com.company.project.tracker.repository.UserRepository;
import com.company.project.tracker.repository.UserSessionRepository;
import com.company.project.tracker.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override
    public User getAuthenticatedUser(String sessionId) {
        UserSession bySessionId = userSessionRepository.findBySessionIdAndIsValidTrue(sessionId);
        if (bySessionId == null) {
            throw new AuthenticationException("User is not authenticated");
        }

        return bySessionId.getUser();
    }

    @Override
    public void authenticate(String sessionId) {
        UserSession bySessionId = userSessionRepository.findBySessionIdAndIsValidTrue(sessionId);
        if (bySessionId == null) {
            throw new AuthenticationException("User is not authenticated");
        }
    }


    @Override
    public void logout(String sessionId) {
        UserSession userSession = userSessionRepository.findBySessionIdAndIsValidTrue(sessionId);
        if (userSession != null) {
            userSession.setIsValid(false);
            userSessionRepository.save(userSession);
        }
    }

    @Override
    public UserLoginResponse loginUser(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByEmail(userLoginRequest.getEmail());
        if (user == null) {
            throw new AuthenticationException("Email or password is incorrect");
        }

        UserLogin userLogin = userLoginRepository.findByUserAndPassword(user, userLoginRequest.getPassword());
        if (userLogin == null) {
            throw new AuthenticationException("Email or password is incorrect");
        }

        UserSession userSession = new UserSession();
        userSession.setUser(user);
        userSession.setSessionId(UUID.randomUUID().toString());
        userSession.setIsValid(true);

        userSessionRepository.save(userSession);

        return new UserLoginResponse(user, userSession.getSessionId());
    }
}
