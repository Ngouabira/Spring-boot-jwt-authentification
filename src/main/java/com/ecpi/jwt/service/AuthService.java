package com.ecpi.jwt.service;

import com.ecpi.jwt.dto.LoginDTO;
import com.ecpi.jwt.dto.RegisterDTO;
import com.ecpi.jwt.exception.EntityNotFoundException;
import com.ecpi.jwt.mapper.RegisterMapper;
import com.ecpi.jwt.repository.UserRepository;
import com.ecpi.jwt.response.AuthResponse;
import com.ecpi.jwt.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public AuthResponse login(LoginDTO dto){

        var optionalUser = userRepository.findByUsername(dto.getUsername());

        if (optionalUser.isPresent()) {
            var user = optionalUser.get();

            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String accessToken = jwtService.generateAccessToken(authentication, 3600);
                String refreshToken = jwtService.generateAccessToken(authentication, 7200);

                Set<String> roles = user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());

                return  new AuthResponse(accessToken, refreshToken, user.isEnabled(), roles);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        throw new EntityNotFoundException("Email or password incorrect");

    }


    public String register(RegisterDTO dto) {
        var optionalUser = userRepository.findByUsername(dto.getUsername());

        if (optionalUser.isEmpty()) {

            String encodePassword = passwordEncoder.encode(dto.getPassword());
            dto.setPassword(encodePassword);
            userRepository.save(RegisterMapper.toUser(dto));

            return "Account created successfully";
    }
        return "Account already exists";
    }





}
