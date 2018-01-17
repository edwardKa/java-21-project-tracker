package com.company.project.tracker.repository;

import com.company.project.tracker.model.entity.User;
import com.company.project.tracker.model.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    UserLogin findByUserAndPassword(User user, String password);
}
