package com.practice.ahub.repository;


import com.practice.ahub.model.User;
import com.practice.ahub.repository.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Qualifier("jdbc")
public class UserJdbcRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper mapper;

    @Autowired
    public UserJdbcRepository(UserMapper mapper, JdbcTemplate jdbcTemplate) {
        this.mapper = mapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getById(int id) {
        String SQL = "SELECT * FROM users_ahub WHERE id = ?";
        return jdbcTemplate.queryForObject(
                SQL,
                mapper,
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
