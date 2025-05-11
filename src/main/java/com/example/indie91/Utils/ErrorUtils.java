package com.example.indie91.Utils;

import com.example.indie91.POJO.ApiErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ErrorUtils {

    // 1. Return a fallback message if original is null or empty
    public static String safeMessage(String message, String fallback){
        return (message != null && !message.isEmpty()) ? message : fallback;
    }

    // 2. Format LocalDateTime for consistency in API responses
    public static String formatTimestamp(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // 3. Build a consistent error response using Map
    public static Map<String, Object> buildErrorResponseMap(String message, HttpStatus status){
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", formatTimestamp(LocalDateTime.now()));
        errorDetails.put("message", safeMessage(message, "An unexpected error occurred"));
        errorDetails.put("status", status.value());
        errorDetails.put("error", status.getReasonPhrase());

        return errorDetails;
    }

    // 4. Build a consistent error response using POJO Class
    public static ApiErrorResponse buildErrorResponse(String message, HttpStatus status){
        return new ApiErrorResponse(
                LocalDateTime.now(),
                safeMessage(message, "An unexpected error occurred"),
                status.value(),
                status.getReasonPhrase()
        );
    }
}
