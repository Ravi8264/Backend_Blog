package com.blog.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponce {

    private String message;
    private boolean success;
    private LocalDateTime timestamp;

    // Custom constructor with automatic timestamp
    public ApiResponce(String message, boolean success) {
        this.message = message;
        this.success = success;
        this.timestamp = LocalDateTime.now();
    }
}
