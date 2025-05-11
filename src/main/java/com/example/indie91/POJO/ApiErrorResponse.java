package com.example.indie91.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private int status;
    private String error;
}
