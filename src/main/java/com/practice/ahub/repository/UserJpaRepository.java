package com.practice.ahub.repository;

import com.practice.ahub.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Qualifier("jpa")
@Primary
public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {
    Optional<User> findByEmail(String email);
}
