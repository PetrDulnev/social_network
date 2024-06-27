package com.practice.ahub.service;

import com.practice.ahub.exception.CustomException;
import com.practice.ahub.jwt.JwtRequest;
import com.practice.ahub.jwt.JwtResponse;
import com.practice.ahub.jwt.JwtService;
import com.practice.ahub.model.Role;
import com.practice.ahub.model.User;
import com.practice.ahub.model.UserProfile;
import com.practice.ahub.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    JwtService jwtService;
    PasswordEncoder passwordEncoder;
    UserProfileService userProfileService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, UserProfileService userProfileService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userProfileService = userProfileService;
        log.info("this is {}", userRepository.getClass());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User createUser(User user) {
        user.setPassword(cryptPassword(user.getPassword()));
        user.setRole(Role.USER);
        User savedUSer = userRepository.save(user);
        UserProfile profile = UserProfile.builder()
                .link(String.valueOf(savedUSer.getId()))
                .user(user)
                .build();
        userProfileService.saveUserProfile(profile);
        return savedUSer;
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
    }

    @Override
    public ResponseEntity<JwtResponse> login(JwtRequest request) {
        User user = loadUserByUsername(request.getEmail());
        if (user != null && passwordEncoder.matches(
                request.getPassword(), user.getPassword()
        )) {
            return jwtService.generateToken(user);
        } else {
            throw new CustomException("You entered the wrong password");
        }
    }

    @Override
    public Optional<User> getById(long id) {
        return Optional.ofNullable(userRepository.getById((int) id));
    }

    private String cryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
