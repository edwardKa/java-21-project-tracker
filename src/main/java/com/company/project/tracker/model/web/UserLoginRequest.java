package com.company.project.tracker.model.web;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginRequest {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email format is incorrect")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
