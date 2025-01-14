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
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController implements AuthControllerInterface {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @PostMapping
    public ResponseEntity login() {
        return new ResponseEntity<>(authService.authenticate(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/callback")
    public ResponseEntity callback(@RequestParam(required = false) String code) {
        String userId = authService.setSpotifyTokenAndGetUserData(code);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "http://localhost:5173/login/callback?userId=" + userId);

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
