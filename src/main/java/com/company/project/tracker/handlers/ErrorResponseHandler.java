package com.company.project.tracker.handlers;

import com.company.project.tracker.exception.AuthenticationException;
import com.company.project.tracker.exception.InputValidationException;
import com.company.project.tracker.exception.ItemDoesNotExistException;
import com.company.project.tracker.model.web.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ErrorResponseHandler {

    @ExceptionHandler(InputValidationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> getAPIExceptionResponse(InputValidationException exception) {


        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorStatus(httpStatus.value())
                .errorResponse(exception.getErrors())
                .message("Input validation error occurred")
                .build();


        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> getAuthenticationException(AuthenticationException exception) {

        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), null, exception.getMessage());

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> getSystemExceptionResponse(Exception exception) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), null, exception.getMessage());

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(ItemDoesNotExistException.class)
    public ResponseEntity<ErrorResponse> getItemDoesNotExistResponse(ItemDoesNotExistException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), null, e.getMessage());

        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
