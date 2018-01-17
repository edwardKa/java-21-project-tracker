package com.company.project.tracker.repository;

import com.company.project.tracker.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByLastName(String lastName);
    User findByEmail(String email);
}
