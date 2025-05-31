package com.example.webgateway.service;

import com.example.webgateway.dto.UserDTO;
import com.example.webgateway.dto.UserDtoMapper;
import com.example.webgateway.repository.UserRepository;
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
