package com.ecpi.jwt.service;

import com.ecpi.jwt.dto.UserDTO;
import com.ecpi.jwt.exception.EntityAlreadyExistsException;
import com.ecpi.jwt.exception.EntityNotFoundException;
import com.ecpi.jwt.mapper.UserMapper;
import com.ecpi.jwt.model.User;
import com.ecpi.jwt.repository.UserRepository;
import com.ecpi.jwt.response.DataResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

 private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public UserDTO create(UserDTO dto) {

        Optional<User> user = userRepository.findByUsername(dto.getUsername());
        if (user.isEmpty()) {

            String encodedPassword = encoder.encode(dto.getPassword());
            dto.setPassword(encodedPassword);
            var savedUser = userRepository.save(UserMapper.toEntity(dto));
            return UserMapper.toDTO(savedUser);
        }
        throw new EntityAlreadyExistsException("User email already exists.", 419);
    }

    public UserDTO update(UserDTO dto) {

        Optional<User> user = userRepository.findByUsername(dto.getUsername());
        var userData = user.orElse(new User());

        if (Objects.equals(userData.getUsername(), dto.getUsername())) {

            User userToUpdate = UserMapper.toEntity(dto);
            userToUpdate.setPassword(userData.getPassword());

            var savedUser = userRepository.save(userToUpdate);
            return UserMapper.toDTO(savedUser);
        }
        throw new EntityAlreadyExistsException("User email already exists.", 419);
    }

    public UserDTO findById(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {

            return UserMapper.toDTO(optionalUser.get());

        }
        throw new EntityNotFoundException("User with id "+id+" not found", 404);
    }


    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(UserMapper::toDTO).toList();
    }

    public DataResponse<UserDTO> search(String param, int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        var data = userRepository.findByNameLikeAndUsernameLike("%"+param+"%","%"+param+"%", pageRequest);
        var content = data.getContent().stream().map(UserMapper::toDTO).toList();
        var totalPages = data.getTotalPages();
        var totalElements = data.getTotalElements();
        return  new DataResponse<UserDTO>(content, totalPages, totalElements, size);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

 
}
