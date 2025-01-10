package com.breakabletoy.SpotifyApp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

public class ErrorCustomException extends HttpClientErrorException {
    public ErrorCustomException(HttpStatusCode code, String message) {
        super(code, message);
    }
}
