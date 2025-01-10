package com.breakabletoy.SpotifyApp.Exceptions;

import com.breakabletoy.SpotifyApp.Models.Exceptions.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedCustomException.class)
    public ResponseEntity<ErrorModel> handleUserNotLoggedInException(UnauthorizedCustomException ex) {
        ErrorModel errorResponse = new ErrorModel(ex.getStatusCode().value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(ErrorCustomException.class)
    public ResponseEntity<ErrorModel> handleUnauthorizedCustomErrorException(ErrorCustomException ex) {
        ErrorModel errorResponse = new ErrorModel(ex.getStatusCode().value(), ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(AuthCustomException.class)
    public ResponseEntity<ErrorModel> handleAuthCustomException(AuthCustomException ex) {
        ErrorModel errorResponse = new ErrorModel(ex.getStatusCode().value(), ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }
}
