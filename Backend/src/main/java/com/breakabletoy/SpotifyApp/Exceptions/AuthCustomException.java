package com.breakabletoy.SpotifyApp.Exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

public class AuthCustomException extends HttpClientErrorException {
  public AuthCustomException(HttpStatusCode code, String message) {
    super(code, message);
  }
}
