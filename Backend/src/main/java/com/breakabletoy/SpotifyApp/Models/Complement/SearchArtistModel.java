package com.breakabletoy.SpotifyApp.Models.Complement;

import com.breakabletoy.SpotifyApp.Models.Responses.ArtistModelResponse;

import java.util.List;

public record SearchArtistModel(
    String href,
    int limit,
    String next,
    int offset,
    String previous,
    int total,
    List<ArtistModelResponse> items
) {
}
