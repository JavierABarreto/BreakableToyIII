package com.breakabletoy.SpotifyApp.DTO;

import java.util.HashMap;

public record SimplifiedArtistModelDTO(
    HashMap<String, String> external_urls,
    String href,
    String id,
    String name,
    String type,
    String uri
) {
}
