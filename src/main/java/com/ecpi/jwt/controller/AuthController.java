package com.ecpi.jwt.controller;

import com.ecpi.jwt.response.AuthResponse;
import com.ecpi.jwt.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecpi.jwt.dto.LoginDTO;
import com.ecpi.jwt.dto.RegisterDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }




    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody  LoginDTO dto){
        return new ResponseEntity<AuthResponse>(authService.login(dto), HttpStatus.OK);
    }


    @PostMapping("/register")
    public String register(@Valid @RequestBody  RegisterDTO dto){
        return authService.register(dto);
    }

    @GetMapping("/test")
    public String test(){
        return "Well done!";
    }
}
