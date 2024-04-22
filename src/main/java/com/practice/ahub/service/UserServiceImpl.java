package com.practice.ahub.service;

import com.practice.ahub.jwt.JwtRequest;
import com.practice.ahub.jwt.JwtResponse;
import com.practice.ahub.jwt.JwtService;
import com.practice.ahub.model.Role;
import com.practice.ahub.model.User;
import com.practice.ahub.model.UserDTO.UserDTO;
import com.practice.ahub.model.UserDTO.UserDTOMapper;
import com.practice.ahub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(User user) {
        user.setPassword(cryptPassword(user.getPassword()));
        user.setRole(Role.USER);
        return UserDTOMapper.userToUserDTO(userRepository.save(user));
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
    }

    @Override
    public JwtResponse login(JwtRequest request) {
        User user = loadUserByUsername(request.getEmail());
        if (user != null && passwordEncoder.matches(
                request.getPassword(), user.getPassword()
        )) {
            return jwtService.generateToken(user).getBody();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + request.getEmail());
        }
    }

    @Override
    public UserDTO getUserById(Long id) {
        return UserDTOMapper.userToUserDTO(userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id: " + id)
        ));
    }

    private String cryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
