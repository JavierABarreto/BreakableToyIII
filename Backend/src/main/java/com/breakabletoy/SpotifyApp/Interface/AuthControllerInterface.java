package com.breakabletoy.SpotifyApp.Interface;

import org.springframework.http.ResponseEntity;

public interface AuthControllerInterface {
    public ResponseEntity<Void> login();
    public void callback(String code);
}
