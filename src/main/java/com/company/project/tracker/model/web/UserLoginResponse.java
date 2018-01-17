package com.company.project.tracker.model.web;

import com.company.project.tracker.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLoginResponse {
    private User user;
    private String sessionId;
}
