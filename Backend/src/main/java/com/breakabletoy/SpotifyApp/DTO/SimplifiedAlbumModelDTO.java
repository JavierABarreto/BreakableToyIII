package com.breakabletoy.SpotifyApp.DTO;

import com.breakabletoy.SpotifyApp.Models.Responses.ArtistModelResponse;
import com.breakabletoy.SpotifyApp.Models.Complement.ImagesModel;

import java.util.HashMap;
import java.util.List;

public record SimplifiedAlbumModelDTO (
    String album_type,
    int total_tracks,
    List<String> available_markets,
    HashMap<String, String> external_urls,
    String href,
    String id,
    List<ImagesModel> images,
    String name,
    String release_date,
    String release_date_precision,
    HashMap<String, String> restrictions,
    String type,
    String uri,
    List<ArtistModelResponse> artists
) {
}
