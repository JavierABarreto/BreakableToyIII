package com.breakabletoy.SpotifyApp.Controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpotifyAppControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("User try to make a request and receives an error with status 401 Unauthorized")
    void test1() {
        String url = "http://localhost:"+ port + "/me/top/artists";
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", "testUserId");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
