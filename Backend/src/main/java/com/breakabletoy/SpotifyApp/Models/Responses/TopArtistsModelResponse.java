package com.breakabletoy.SpotifyApp.Models.Responses;

import com.breakabletoy.SpotifyApp.Models.Complement.TopArtistsItemModel;

import java.util.List;

public record TopArtistsModelResponse(
    String href,
    int limit,
    String next,
    int offset,
    String previous,
    int total,
    List<TopArtistsItemModel> items
) {
}
