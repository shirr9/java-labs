package com.example.webgateway.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "MyUser")
public class MyUser {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String password;
    private String roles;
}
