package com.practice.socialnetwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(UsernameNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(
                true,
                e.getMessage()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Object> handleException(CustomException e) {
        return ResponseEntity.badRequest()
                .body(ExceptionResponse.builder().message(e.getMessage()).hasError(true).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException e) {
        List<ExceptionResponse> exceptionResponses = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> exceptionResponses.add(
                ExceptionResponse.builder().message(e.getMessage()).hasError(true).build()
        ));
        return ResponseEntity.badRequest().body(exceptionResponses);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        return ResponseEntity
                .internalServerError()
                .body(ExceptionResponse.builder().message(e.getMessage()).hasError(true).build());
    }
}
