package com.practice.socialnetwork.service.user;

import com.practice.socialnetwork.exception.CustomException;
import com.practice.socialnetwork.jwt.JwtRequest;
import com.practice.socialnetwork.jwt.JwtResponse;
import com.practice.socialnetwork.jwt.JwtService;
import com.practice.socialnetwork.model.user.Role;
import com.practice.socialnetwork.model.user.User;
import com.practice.socialnetwork.model.userprofile.Gender;
import com.practice.socialnetwork.model.userprofile.UserProfile;
import com.practice.socialnetwork.repository.user.UserRepository;
import com.practice.socialnetwork.repository.userprofile.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileRepository profileRepository;

    @Override
    public User createUser(User user) {
        user.setPassword(cryptPassword(user.getPassword()));
        user.setRole(Role.USER);
        User savedUSer = userRepository.save(user);
        UserProfile profile = UserProfile.builder()
                .user(user)
                .link(UUID.randomUUID().toString())
                .gender(Gender.UNKNOWN)
                .build();
        profileRepository.save(profile);
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

    private String cryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
