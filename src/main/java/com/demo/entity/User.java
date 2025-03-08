package com.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
//    @Valid
    @Column(unique = true)
    private String email;

    private String password;

    private boolean isVerified;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Enumerated(EnumType.STRING)
    private Role role;
}
