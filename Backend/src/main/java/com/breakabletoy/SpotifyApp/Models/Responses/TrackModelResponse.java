package com.breakabletoy.SpotifyApp.Models.Responses;

import java.util.HashMap;
import java.util.List;

public record TrackModelResponse(
    String href,
    int limit,
    String next,
    int offset,
    String previous,
    int total,
    List<HashMap> items
) {
}
