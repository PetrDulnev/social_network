package com.practice.ahub.repository.Mapper;

import com.practice.ahub.model.Role;
import com.practice.ahub.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setRole(Role.USER);
        user.setEmail(rs.getString("email"));
        return user;
    }
}
