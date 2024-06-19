package com.practice.ahub.repository;

import com.practice.ahub.model.User;

import java.util.Optional;

public interface UserRepository {
    User getById(int id);
    User save(User user);
    Optional<User> findByEmail(String email);
}
