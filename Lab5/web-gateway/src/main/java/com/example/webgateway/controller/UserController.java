package com.example.webgateway.controller;

import com.example.webgateway.dto.UserDTO;
import com.example.webgateway.service.MyUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private MyUserService userService;

    public UserController(MyUserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/new-user")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDto) {
        userService.addUser(userDto);
        return ResponseEntity.ok("User is saved");
    }
}