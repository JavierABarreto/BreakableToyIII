package com.breakabletoy.SpotifyApp.Repository;

import com.breakabletoy.SpotifyApp.DTO.SpotifyTokenModelDTO;

import java.util.HashMap;

public class TokenRepository {
    private HashMap tokenRepository = new HashMap<String, SpotifyTokenModelDTO>();
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

    public void setToken(String userId, SpotifyTokenModelDTO tokenData) {
        tokenRepository.put(userId, tokenData);
    }

    public SpotifyTokenModelDTO getTokenData(String userId) {
        return (SpotifyTokenModelDTO) tokenRepository.get(userId);
    }
}
