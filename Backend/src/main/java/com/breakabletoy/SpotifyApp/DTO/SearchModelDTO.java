package com.breakabletoy.SpotifyApp.DTO;

import com.breakabletoy.SpotifyApp.Models.Responses.ArtistModelResponse;

import java.util.HashMap;
import java.util.List;

public record SearchModelDTO(
    List<HashMap> tracksItems,
    List<ArtistModelResponse> artistsItems,
    List<SimplifiedAlbumModelDTO> albumsItems
) {
}
