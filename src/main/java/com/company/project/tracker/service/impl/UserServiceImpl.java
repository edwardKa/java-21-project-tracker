package com.company.project.tracker.service.impl;

import com.company.project.tracker.exception.InputValidationException;
import com.company.project.tracker.model.entity.User;
import com.company.project.tracker.model.entity.UserLogin;
import com.company.project.tracker.model.entity.UserSession;
import com.company.project.tracker.model.web.RegisterUserRequest;
import com.company.project.tracker.model.web.UserLoginResponse;
import com.company.project.tracker.model.web.UserUpdateRequest;
import com.company.project.tracker.repository.UserLoginRepository;
import com.company.project.tracker.repository.UserRepository;
import com.company.project.tracker.repository.UserSessionRepository;
import com.company.project.tracker.service.UserService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;


    @Override
    public UserLoginResponse registerNewUser(RegisterUserRequest registerUserRequest) {

        User user = userRepository.findByEmail(registerUserRequest.getEmail());
        if (user != null) {
            throw new InputValidationException(new Pair<>("email", String.format("Such email already exists [%s]", registerUserRequest.getEmail())));
        }
        user = User.builder()
                .firstName(registerUserRequest.getFirstName())
                .lastName(registerUserRequest.getLastName())
                .email(registerUserRequest.getEmail())
                .build();

        userRepository.save(user);

        UserLogin userLogin = new UserLogin();
        userLogin.setUser(user);
        userLogin.setPassword(registerUserRequest.getPassword());

        userLoginRepository.save(userLogin);

        UserSession userSession = new UserSession();
        userSession.setUser(user);
        userSession.setSessionId(UUID.randomUUID().toString());
        userSession.setIsValid(true);

        userSessionRepository.save(userSession);

        return new UserLoginResponse(user, userSession.getSessionId());
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
            User userWithSameEmail = userRepository.findByEmail(userUpdateRequest.getEmail());
            if (userWithSameEmail != null) {
                throw new InputValidationException(new Pair<>("email", String.format("Such email already exists [%s]", userUpdateRequest.getEmail())));
            }
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




}
