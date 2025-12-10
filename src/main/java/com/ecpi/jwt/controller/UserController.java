package com.ecpi.jwt.controller;

import com.ecpi.jwt.dto.UserDTO;
import com.ecpi.jwt.response.DataResponse;
import com.ecpi.jwt.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    List<UserDTO> findAll() {
        return userService.findAll();
    }

    @PostMapping
    ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO dto) {
        return new ResponseEntity<>(userService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping
    ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO dto) {
        return new ResponseEntity<>(userService.update(dto), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<DataResponse<UserDTO>> search(@RequestParam(value = "param", required = false, defaultValue = "") String param,
                                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                        @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return new ResponseEntity<>(userService.search(param, page, size), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }
}
