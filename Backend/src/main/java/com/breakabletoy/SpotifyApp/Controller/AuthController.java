package com.breakabletoy.SpotifyApp.Controller;

import com.breakabletoy.SpotifyApp.Interface.AuthControllerInterface;
import com.breakabletoy.SpotifyApp.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/spotify")
public class AuthController implements AuthControllerInterface {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> login() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, authService.authenticate());  // Redirect to this URL
        return new ResponseEntity<>(headers, HttpStatus.FOUND);  // 302 - Found (temporary redirect)
    }

    @Override
    @GetMapping("/callback")
    public void callback(@RequestParam(required = false) String code) {
        authService.setSpotifyToken(code);
    }
}
