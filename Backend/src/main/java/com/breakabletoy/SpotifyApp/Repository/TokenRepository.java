package com.breakabletoy.SpotifyApp.Repository;

import com.breakabletoy.SpotifyApp.DTO.SpotifyTokenModelDTO;

public class TokenRepository {
    private SpotifyTokenModelDTO tokenRepository = null;
    private static TokenRepository instance = null;

    private TokenRepository() { }

    public static TokenRepository getInstance() {
        if(instance != null) {
            return instance;
        } else {
            instance = new TokenRepository();
            return instance;
        }
    }

    public void setToken(SpotifyTokenModelDTO tokenData) {
        tokenRepository = tokenData;
    }

    public SpotifyTokenModelDTO getTokenData() {
        return tokenRepository;
    }
}
