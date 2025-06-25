package com.example.webgateway.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String password;
    private String roles;
}
