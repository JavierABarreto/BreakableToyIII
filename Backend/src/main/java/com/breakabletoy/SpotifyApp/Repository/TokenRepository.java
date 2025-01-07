package com.breakabletoy.SpotifyApp.Repository;

import com.breakabletoy.SpotifyApp.Models.TokenModel;

import java.util.HashMap;

public class TokenRepository {
    private HashMap<String, TokenModel> tokenRepository = new HashMap<>();

    public void addToken(String userId, String token, String refreshToken) {
        TokenModel newToken = new TokenModel(token, refreshToken);
        tokenRepository.put(userId, newToken);
    }

    public TokenModel getUser(String userId) {
        return tokenRepository.get(userId);
    }
}
