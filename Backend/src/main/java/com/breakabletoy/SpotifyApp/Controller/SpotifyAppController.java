package com.breakabletoy.SpotifyApp.Controller;

import com.breakabletoy.SpotifyApp.DTO.SearchModelDTO;
import com.breakabletoy.SpotifyApp.Models.Responses.AlbumModelResponse;
import com.breakabletoy.SpotifyApp.Models.Responses.ArtistModelResponse;
import com.breakabletoy.SpotifyApp.Models.Complement.TopArtistsItemModel;
import com.breakabletoy.SpotifyApp.Service.SpotifyApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SpotifyAppController {
    private final SpotifyApiService spotifyApiService;

    @Autowired
    public SpotifyAppController(SpotifyApiService spotifyApiService) {
        this.spotifyApiService = spotifyApiService;
    }

    @GetMapping("/me/top/artists")
    public ResponseEntity<List<TopArtistsItemModel>> topArtists(
        @RequestParam("userId") String userId
    ) {
        return spotifyApiService.getTopArtists(userId);
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<ArtistModelResponse> getArtist(
        @PathVariable("id") String id,
        @RequestParam("userId") String userId
    ) {
        return spotifyApiService.getArtist(id, userId);
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<AlbumModelResponse> getAlbum(
        @PathVariable("id") String id,
        @RequestParam("userId") String userId
    ) {
        return spotifyApiService.getAlbum(id, userId);
    }

    @GetMapping("/search") //  Allow search for artists, albums, or tracks.
    public ResponseEntity<SearchModelDTO> search(
        @RequestParam("q") String query,
        @RequestParam("userId") String userId
    ) {
        return spotifyApiService.search(query, userId);
    }
}
