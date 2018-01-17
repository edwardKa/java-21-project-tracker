package com.company.project.tracker.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USER_LOGIN")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @JoinColumn(unique = true)
    @OneToOne
    private User user;

    private String password;
}
