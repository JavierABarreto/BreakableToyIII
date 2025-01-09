package com.breakabletoy.SpotifyApp.Models.Responses;

public record SpotifyTokenModelResponse(
    String access_token,
    String token_type,
    String scope,
    int expires_in,
    String refresh_token
) {
}
