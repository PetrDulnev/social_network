package com.practice.ahub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.ahub.jwt.JwtRequest;
import com.practice.ahub.jwt.JwtResponse;
import com.practice.ahub.model.Role;
import com.practice.ahub.model.User;
import com.practice.ahub.model.UserDTO.UserDTO;
import com.practice.ahub.repository.UserRepository;
import com.practice.ahub.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class UserControllerTest {

    private static final String URL = "/ahub/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Mock
    UserRepository userRepository;


    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse(
            "postgres:13-alpine"
    ));

    @DynamicPropertySource
    static void configurePostgreSQLContainer(@NotNull DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.generate-ddl", () -> true);
    }

    @Test
    void createUser() throws Exception {

        User user = User.builder()
                .id(1L).name("petya").email("qweqwe@gmail.com").password("1Qweqwqqqqe").surname("qweqweqwe")
                .role(Role.USER).build();
        String userJson = objectMapper.writeValueAsString(user);
        var res = this.mockMvc.perform(post(URL + "/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"email\":\"qweqwe@gmail.com\",\"password\":\"1Qweqwqqqqe\"" +
                                ",\"name\":\"petya\",\"surname\":\"qweqweqwe\"}"))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();
        user.setRole(Role.USER);
        when(userService.createUser(user)).thenReturn(new UserDTO());
    }

    @Test
    void login() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setEmail("qweqwe@gmail.com");
        jwtRequest.setPassword("1Qweqwqqqqqe");
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(String.valueOf(UUID.randomUUID()));
        User user = User.builder().
                email(jwtRequest.getEmail()).name("q").surname("q").password("a").role(Role.USER).build();
        when(userService.login(jwtRequest)).thenReturn(jwtResponse);
        when(userRepository.findByEmail("qweqwe@gmail.com")).thenReturn(Optional.of(user));
        this.mockMvc.perform(post(URL + "/login").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}