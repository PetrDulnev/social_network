package com.practice.ahub.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Stream;


class PasswordValidatorTest {

    static PasswordValidator passwordValidator;

    @MockBean
    ConstraintValidatorContext constraintValidatorContext;

    @BeforeAll
    static void setUp() {
        passwordValidator = new PasswordValidator();
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource(value = "argumentsStream")
    void isValid(String password, boolean expected) {
        Assertions.assertEquals(passwordValidator.isValid(password, constraintValidatorContext), expected);
    }

    private static Stream<Arguments> argumentsStream() {
        return Stream.of(
                Arguments.of("Qwe123we", true),
                Arguments.of("q", false),
                Arguments.of("qwe", false),
                Arguments.of("qqqqqW123455qq", true),
                Arguments.of("qqqqqW123455qq", true),
                Arguments.of("AAAAAAAAAAAAAAAAAAAAAA", false),
                Arguments.of("qweqwewqweqweq", false),
                Arguments.of("qweQ", false),
                Arguments.of("qwe1", false),
                Arguments.of("qwe123qwe", false)
        );
    }

}