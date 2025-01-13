package com.breakabletoy.SpotifyApp.Service;

import com.breakabletoy.SpotifyApp.Exceptions.AuthCustomException;
import com.breakabletoy.SpotifyApp.Repository.TokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AuthServiceTests {
    @Mock
    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("User logins fails")
    void tokenFailsToBeSetTest() {
        assertThrows(AuthCustomException.class, () -> authService.getSpotifyToken("testCode"));
    }
}
