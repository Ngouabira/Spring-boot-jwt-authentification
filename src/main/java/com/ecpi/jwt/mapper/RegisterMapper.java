package com.ecpi.jwt.mapper;

import com.ecpi.jwt.dto.RegisterDTO;
import com.ecpi.jwt.model.User;

public class RegisterMapper {

    public static User toUser(RegisterDTO dto){
        if (dto == null) {
            return null;
        }

        return new User(dto.getUsername(), dto.getPassword(), dto.getRole());
    }
}
