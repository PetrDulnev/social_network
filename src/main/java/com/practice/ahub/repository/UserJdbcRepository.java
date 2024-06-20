package com.practice.ahub.repository;


import com.practice.ahub.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Qualifier("jdbc")
public class UserJdbcRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
//    private final BeanPropertyRowMapper<User> userRowMapper;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public User getById(int id) {
        String SQL = "SELECT * FROM users_ahub WHERE id = ?";
        return jdbcTemplate.queryForObject(
                SQL,
                new BeanPropertyRowMapper<>(User.class),
                id
        );
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}
