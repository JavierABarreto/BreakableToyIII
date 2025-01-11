package com.breakabletoy.SpotifyApp.Service;

import com.breakabletoy.SpotifyApp.DTO.SpotifyTokenModelDTO;
import com.breakabletoy.SpotifyApp.Exceptions.AuthCustomException;
import com.breakabletoy.SpotifyApp.Exceptions.ErrorCustomException;
import com.breakabletoy.SpotifyApp.Models.Responses.SpotifyTokenModelResponse;
import com.breakabletoy.SpotifyApp.Models.Responses.UserProfileModelResponse;
import com.breakabletoy.SpotifyApp.Properties.EnvProperties;
import com.breakabletoy.SpotifyApp.Repository.TokenRepository;
import com.breakabletoy.SpotifyApp.Util.UrlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.logging.Logger;

@Service
public class AuthService {
    private final RestTemplate restTemplate = new RestTemplate();
    private TokenRepository tokenRepository = TokenRepository.getInstance();
    private final EnvProperties envProperties;

    private static final Logger logger = Logger.getLogger(AuthService.class.getName());

    @Autowired
    public AuthService(EnvProperties envProperties) {
        this.envProperties = envProperties;
    }

    public String authenticate() {
        String scope = "user-read-private user-read-email user-top-read";

        try {
           String AUTHORIZE_URL =  UrlConstants.AUTHORIZE_URL +
                   "response_type=code&" +
                   "client_id=" + envProperties.getClientId() + "&" +
                   "scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8) + "&" +
                   "redirect_uri=" + URLEncoder.encode(UrlConstants.REDIRECT_URL, StandardCharsets.UTF_8);

           return AUTHORIZE_URL;
        } catch (HttpClientErrorException e) {
           throw new AuthCustomException(
               e.getStatusCode(),
               "There has been an error while requiring the authenticate code: " + e.getMessage()
           );
        }
    }

    public String setSpotifyToken(String code) {
        String authHeader = Base64.getEncoder()
                .encodeToString(
                    (envProperties.getClientId()+":"+envProperties.getClientSecret()).getBytes()
                );

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
                    expireDate
            );

            String userId = getUserId(data.access_token());

            tokenRepository.setToken(userId, token);
            logger.info("User logged in an the token has been set.");

            return userId;
        } catch (HttpClientErrorException e) {
            throw new AuthCustomException(
                e.getStatusCode(),
                "There has been an error while setting the authenticate token: " + e.getMessage()
            );
        }
    }

    public void refreshToken(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.set("grant_type", "refresh_token");
        body.set("refresh_token", tokenRepository.getTokenData(userId).refreshToken());
        body.set("client_id", envProperties.getClientId());
        body.set("client_secret", envProperties.getClientSecret());

        HttpEntity request = new HttpEntity<>(body, headers);

        try {
            HttpEntity<SpotifyTokenModelResponse> response = restTemplate.exchange(
                    UrlConstants.REQUIRE_TOKEN_URL,
                    HttpMethod.POST,
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

            tokenRepository.setToken(userId, token);
            logger.info("User token expired and a new one was generated using the user's refreshToken.");
        } catch (HttpClientErrorException e) {
            throw new AuthCustomException(
                e.getStatusCode(),
                "There has been an error while refreshing the authenticate token: " + e.getMessage()
            );
        }
    }

    public String getUserId (String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity request = new HttpEntity<>(new HashMap<>(), headers);

        try {
            ResponseEntity<UserProfileModelResponse> response = restTemplate.exchange(
                UrlConstants.SPOTIFY_API_URL + "/me",
                HttpMethod.GET,
                request,
                UserProfileModelResponse.class
            );

            if(response.getStatusCode() != HttpStatus.OK) {
                logger.warning("There's been an error while requesting the user id.");

                throw new ErrorCustomException(
                        response.getStatusCode(),
                        response.getBody().toString()
                );
            }

            return response.getBody().id();
        } catch (HttpClientErrorException e) {
            throw new ErrorCustomException(
                    e.getStatusCode(),
                    e.getMessage()
            );
        }
    }
}
