package com.company.project.tracker.service.impl;

import com.company.project.tracker.exception.AuthenticationException;
import com.company.project.tracker.model.entity.User;
import com.company.project.tracker.model.entity.UserLogin;
import com.company.project.tracker.model.entity.UserSession;
import com.company.project.tracker.model.web.RegisterUserRequest;
import com.company.project.tracker.model.web.UserLoginRequest;
import com.company.project.tracker.model.web.UserLoginResponse;
import com.company.project.tracker.model.web.UserUpdateRequest;
import com.company.project.tracker.repository.UserLoginRepository;
import com.company.project.tracker.repository.UserRepository;
import com.company.project.tracker.repository.UserSessionRepository;
import com.company.project.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;


    @Override
    @Transactional
    public UserLoginResponse registerNweUser(RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setFirstName(registerUserRequest.getFirstName());
        user.setLastName(registerUserRequest.getLastName());
        user.setEmail(registerUserRequest.getEmail());

        userRepository.save(user);

        UserLogin userLogin = new UserLogin();
        userLogin.setUser(user);
        userLogin.setPassword(registerUserRequest.getPassword());

        userLoginRepository.save(userLogin);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail(registerUserRequest.getEmail());
        userLoginRequest.setPassword(registerUserRequest.getPassword());

        return loginUser(userLoginRequest);
    }

    @Override
    public User getById(Long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public List<User> getByLastName(String lastName) {
        return userRepository.findAllByLastName(lastName);
    }

    @Override
    public User updateUserById(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findOne(userId);
        if (!StringUtils.isEmpty(userUpdateRequest.getEmail())) {
            user.setEmail(userUpdateRequest.getEmail());
        }

        if (!StringUtils.isEmpty(userUpdateRequest.getFirstName())) {
            user.setFirstName(userUpdateRequest.getFirstName());
        }

        if (!StringUtils.isEmpty(userUpdateRequest.getLastName())) {
            user.setLastName(userUpdateRequest.getLastName());
        }

        return userRepository.save(user);
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

        return new UserLoginResponse(user, userSession.getSessionId());
    }

    @Override
    public void logout(String sessionId) {
        UserSession userSession = userSessionRepository.findBySessionId(sessionId);
        if (userSession != null) {
            userSession.setIsValid(false);
            userSessionRepository.save(userSession);
        }
    }
}
