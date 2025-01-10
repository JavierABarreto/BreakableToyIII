package com.breakabletoy.SpotifyApp.Models.Exceptions;

public record ErrorModel (
    int statusCode,
    String message
) {
}
