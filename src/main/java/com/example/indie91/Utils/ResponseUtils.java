package com.example.indie91.Utils;

import com.example.indie91.POJO.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtils {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>(
                data,
                null, // error
                message,
                HttpStatus.OK.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String message) {
        ApiResponse<T> response = new ApiResponse<>(
                null,
                message, // error
                status.getReasonPhrase(),
                status.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(response);
    }
}
