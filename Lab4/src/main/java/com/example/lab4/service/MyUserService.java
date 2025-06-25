package com.example.lab4.service;

import com.example.lab4.DTO.MyUser.UserDTO;
import com.example.lab4.DTO.MyUser.UserDtoMapper;
import com.example.lab4.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MyUserService {
    private final UserRepository repository;
    private final UserDtoMapper mapper;

    public MyUserService(UserRepository repository, UserDtoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void addUser(UserDTO userDto) {
        repository.save(mapper.toMyUser(userDto));
    }
}
