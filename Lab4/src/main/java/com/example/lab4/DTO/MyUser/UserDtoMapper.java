package com.example.lab4.DTO.MyUser;

import com.example.lab4.models.MyUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    private final PasswordEncoder encoder;

    public UserDtoMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public MyUser toMyUser(UserDTO dto) {
        MyUser user = new MyUser();
        user.setName(dto.getName());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRoles(dto.getRoles());
        return user;
    }
}
