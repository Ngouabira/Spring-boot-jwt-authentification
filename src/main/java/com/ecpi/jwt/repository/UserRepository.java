package com.ecpi.jwt.repository;

import com.ecpi.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Page<User> findByNameLikeAndUsernameLike(String name, String username, Pageable pageRequest);

}