package com.breakabletoy.SpotifyApp.Models.Responses;

import com.breakabletoy.SpotifyApp.Models.Complement.FollowersModel;
import com.breakabletoy.SpotifyApp.Models.Complement.ImagesModel;

import java.util.List;
import java.util.Map;

public record ArtistModelResponse(
    Map<String, String> external_urls,
    FollowersModel followers,
    List<String> genres,
    String href,
    String id,
    List<ImagesModel> images,
    String name,
    int popularity,
    String type,
    String uri
) {
}
