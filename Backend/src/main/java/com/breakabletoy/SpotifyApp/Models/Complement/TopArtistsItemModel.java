package com.breakabletoy.SpotifyApp.Models.Complement;

import java.util.HashMap;
import java.util.List;

public record TopArtistsItemModel(
    HashMap<String, String> external_urls,
    FollowersModel followers,
    List<String> genres,
    String href,
    String id,
    List<ImagesModel> images,
    String name,
    int popularity,
    String type,
    String uri
) { }
