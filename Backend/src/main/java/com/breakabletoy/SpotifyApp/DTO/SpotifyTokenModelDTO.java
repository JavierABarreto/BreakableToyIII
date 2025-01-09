package com.breakabletoy.SpotifyApp.DTO;

import java.time.Instant;

public record SpotifyTokenModelDTO(
    String token,
    String refreshToken,
    long expire_date
) {
    public boolean isTokenExpired() {
        return expire_date < Instant.now().getEpochSecond();
    }

    public boolean isTokenNull() {
        return token == null;
    }
}
