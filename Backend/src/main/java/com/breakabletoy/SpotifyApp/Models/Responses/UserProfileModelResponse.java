package com.breakabletoy.SpotifyApp.Models.Responses;

import com.breakabletoy.SpotifyApp.Models.Complement.FollowersModel;
import com.breakabletoy.SpotifyApp.Models.Complement.ImagesModel;

import java.util.HashMap;
import java.util.List;

public record UserProfileModelResponse (
    String country,
    String display_name,
    String email,
    HashMap<String, Boolean> explicit_content,
    HashMap<String, String> external_urls,
    FollowersModel followers,
    String href,
    String id,
    List<ImagesModel> images,
    String product,
    String uri
) {
}
