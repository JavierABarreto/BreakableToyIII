package com.breakabletoy.SpotifyApp.Models.Complement;

import com.breakabletoy.SpotifyApp.DTO.SimplifiedAlbumModelDTO;

import java.util.List;

public record SearchAlbumsModel(
    String href,
    int limit,
    String next,
    int offset,
    String previous,
    int total,
    List<SimplifiedAlbumModelDTO> items
) {
}
