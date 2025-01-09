package com.breakabletoy.SpotifyApp.Models.Responses;

import com.breakabletoy.SpotifyApp.Models.Complement.SearchAlbumsModel;
import com.breakabletoy.SpotifyApp.Models.Complement.SearchArtistModel;

public record SearchModelResponse(
    TrackModelResponse tracks,
    SearchArtistModel artists,
    SearchAlbumsModel albums
) {
}
