package com.ecpi.jwt.mapper;

import com.ecpi.jwt.dto.UserDTO;
import com.ecpi.jwt.model.User;

public class UserMapper {

    public static UserDTO toDTO(User entity){

        return entity!=null? new UserDTO(entity.getId(), entity.getUsername(), entity.getRole(), entity.getRole()) : null;
    }


    public static User toEntity(UserDTO dto){

        if (dto == null) {
            return null;
        }

        return new User(dto.getId(), dto.getUsername(), dto.getRole(), dto.getRole());

    }
}
