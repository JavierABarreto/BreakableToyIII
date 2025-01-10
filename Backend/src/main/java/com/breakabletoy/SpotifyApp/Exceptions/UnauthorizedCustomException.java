package com.breakabletoy.SpotifyApp.Exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

public class UnauthorizedCustomException extends HttpClientErrorException {
    public UnauthorizedCustomException(HttpStatusCode code, String message) {
        super(code, message);
    }
}
