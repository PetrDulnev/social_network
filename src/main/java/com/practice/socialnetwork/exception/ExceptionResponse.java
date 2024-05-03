package com.practice.socialnetwork.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponse {
    boolean hasError;
    String message;
    String timestamp;

    public ExceptionResponse(boolean hasError, String message) {
        this.hasError = hasError;
        this.message = message;
        this.timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        );
    }
}
