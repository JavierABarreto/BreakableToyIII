package com.breakabletoy.SpotifyApp.Service;

import com.breakabletoy.SpotifyApp.DTO.SpotifyTokenModelDTO;
import com.breakabletoy.SpotifyApp.Models.Responses.SpotifyTokenModelResponse;
import com.breakabletoy.SpotifyApp.Repository.TokenRepository;
import com.breakabletoy.SpotifyApp.Util.UrlConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

@Service
public class AuthService {
    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();
    private TokenRepository tokenRepository = TokenRepository.getInstance();

    public String authenticate() {
        String scope = "user-read-private user-read-email user-top-read";
       try {
           String AUTHORIZE_URL =  UrlConstants.AUTHORIZE_URL +
                   "response_type=code&" +
                   "client_id=" + clientId + "&" +
                   "scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8) + "&" +
                   "redirect_uri=" + URLEncoder.encode(UrlConstants.REDIRECT_URL, StandardCharsets.UTF_8);

           return AUTHORIZE_URL;
       } catch (Exception e) {
           throw new RuntimeException("There has been an error while requiring the authenticate code: " + e.getMessage());
       }
    }

    public void setSpotifyToken(String code) {
        String authHeader = Base64.getEncoder().encodeToString((clientId+":"+clientSecret).getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + authHeader);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.set("grant_type", "authorization_code");
        body.set("code", code);
        body.set("redirect_uri", UrlConstants.REDIRECT_URL);

        HttpEntity request = new HttpEntity<>(body, headers);

        try {
            HttpEntity<SpotifyTokenModelResponse> response = restTemplate.postForEntity(
                UrlConstants.REQUIRE_TOKEN_URL,
                request,
                SpotifyTokenModelResponse.class
            );

            SpotifyTokenModelResponse data = response.getBody();

            long expireDate = Instant.now().toEpochMilli() / 1000;
            expireDate += data.expires_in();

            SpotifyTokenModelDTO token = new SpotifyTokenModelDTO(
                    data.access_token(),
                    data.refresh_token(),
                    1736264279
            );
            tokenRepository.setToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", tokenRepository.getTokenData().refreshToken());
        body.add("client_id", clientId);
        body.add("refresh_token", clientSecret);

        HttpEntity request = new HttpEntity<>(body, headers);

        try {
            HttpEntity<SpotifyTokenModelResponse> response = restTemplate.postForEntity(
                    UrlConstants.REQUIRE_TOKEN_URL,
                    request,
                    SpotifyTokenModelResponse.class
            );

            SpotifyTokenModelResponse data = response.getBody();

            long expireDate = Instant.now().toEpochMilli() / 1000;
            expireDate += data.expires_in();

            SpotifyTokenModelDTO token = new SpotifyTokenModelDTO(
                    data.access_token(),
                    data.refresh_token(),
                    expireDate
            );

            tokenRepository.setToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
