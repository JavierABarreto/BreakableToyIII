package com.breakabletoy.SpotifyApp.Controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("POST /auth/spotify redirects to spotify login")
    void userIsRedirectedToSpotifyLoginTest() {
        String url = "http://localhost:" + port + "/auth/spotify";

        ResponseEntity response = restTemplate.postForEntity(
            url,
            "",
            String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    @DisplayName("Get /auth/spotify/callback returns an error due to wrong code being provided.")
    void userIsRedirectedToSpotifyCallbackTest() { // Replace to make sure that the user is redorected to the frontend
        String url = "http://localhost:" + port + "/auth/spotify/callback?code=temptemp";

        ResponseEntity response = restTemplate.getForEntity(
                url,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
