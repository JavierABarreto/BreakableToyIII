package com.breakabletoy.SpotifyApp.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class AuthService {
    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public void authenticate() {
        String AUTHORIZE_URL = "https://accounts.spotify.com/authorize?" +
                "response_type=code&" +
                "client_id=" + clientId + "&" +
                "redirect_uri=" + URLEncoder.encode("http://localhost:8080/auth/spotify/callback", StandardCharsets.UTF_8);

        ResponseEntity<String> response = restTemplate.getForEntity(AUTHORIZE_URL, String.class);
        System.out.println(AUTHORIZE_URL);
    }
}
