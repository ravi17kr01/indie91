
package com.example.indie91.Exceptions;

import com.example.indie91.POJO.ApiErrorResponse;
import com.example.indie91.Utils.ErrorUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //Handle custom NoUsersFoundException
    @ExceptionHandler(NoUsersFoundException.class)
    public ResponseEntity<Object> handleNoUsersFound(NoUsersFoundException ex){
        logger.warn("No users found: {}", ex.getMessage());
        //Map<String, Object> errorDetails = ErrorUtils.buildErrorResponse(ex.getMessage(), HttpStatus.NO_CONTENT);
        ApiErrorResponse response = ErrorUtils.buildErrorResponse(ex.getMessage(), HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    //Handle Database exception
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDatabaseException(DataAccessException ex){
        logger.error("Database error: " + ex.getMostSpecificCause().getMessage());
        ApiErrorResponse response = ErrorUtils.buildErrorResponse(ex.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Fallback for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex){
        logger.error("Unexpected error occurred", ex);
        //Map<String, Object> errorDetails = ErrorUtils.buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        ApiErrorResponse response = ErrorUtils.buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
