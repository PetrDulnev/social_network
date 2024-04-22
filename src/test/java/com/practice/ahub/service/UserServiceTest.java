package com.practice.ahub.service;

import com.practice.ahub.jwt.JwtProvider;
import com.practice.ahub.jwt.JwtRequest;
import com.practice.ahub.jwt.JwtResponse;
import com.practice.ahub.model.User;
import com.practice.ahub.model.UserDTO.UserDTO;
import com.practice.ahub.model.UserDTO.UserDTOMapper;
import com.practice.ahub.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class UserServiceTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse(
            "postgres:13-alpine"
    ));

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;

    static User user;
    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    static void configurePostgreSQLContainer(@NotNull DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.generate-ddl", () -> true);
    }

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .email("asd@mail.ru").password("qqq").name("q").surname("qkov")
                .build();
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @BeforeAll
    static void startContainers() {
        postgres.start();
    }

    @AfterAll
    static void stopContainers() {
        postgres.stop();
    }

    @Test
    public void createUser() {
        UserDTO expected = userService.createUser(user);
        UserDTO trueUser = UserDTOMapper.userToUserDTO(userRepository.findByEmail(user.getEmail()).orElseThrow());
        Assertions.assertEquals(trueUser, expected);
    }

    @Test
    void loadUserByUsername() {
        userService.createUser(user);
        Assertions.assertEquals(
                UserDTOMapper.userToUserDTO(user),
                UserDTOMapper.userToUserDTO(userService.loadUserByUsername(user.getUsername())));
    }

    @Test
    void login() {
        userService.createUser(user);
        JwtRequest request = new JwtRequest();
        request.setEmail(user.getEmail());
        request.setPassword("qqq");
        JwtResponse jwtResponse = userService.login(request);
        JwtProvider provider = new JwtProvider();
        Assertions.assertEquals(provider.getUserNameFromToken(jwtResponse.getToken()), user.getEmail());
    }
}