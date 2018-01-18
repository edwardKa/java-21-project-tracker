package com.company.project.tracker.model.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterUserRequest {

    @NotBlank(message = "First name cannot be blank")
    @Length(min = 3, max = 50, message = "First name's length should be 3 to 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Length(min = 3, max = 50, message = "Last name's length should be 3 to 50 characters")
    private String lastName;

    @Email(message = "Email format is incorrect")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Length(min = 6, max = 25, message = "Password's length should be 6 to 25 characters")
    private String password;
}
