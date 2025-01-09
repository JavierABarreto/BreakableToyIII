package com.breakabletoy.SpotifyApp.Controller;

import com.breakabletoy.SpotifyApp.DTO.SearchModelDTO;
import com.breakabletoy.SpotifyApp.Models.Responses.AlbumModelResponse;
import com.breakabletoy.SpotifyApp.Models.Responses.ArtistModelResponse;
import com.breakabletoy.SpotifyApp.Models.Complement.TopArtistsItemModel;
import com.breakabletoy.SpotifyApp.Service.SpotifyApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpotifyAppController {
    private final SpotifyApiService spotifyApiService;

    @Autowired
    public SpotifyAppController(SpotifyApiService spotifyApiService) {
        this.spotifyApiService = spotifyApiService;
    }

    @GetMapping("/me/top/artists")
    public ResponseEntity<List<TopArtistsItemModel>> topArtists() {
        return spotifyApiService.getTopArtists();
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<ArtistModelResponse> getArtist(@PathVariable("id") String id) {
        return spotifyApiService.getArtist(id);
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<AlbumModelResponse> getAlbum(@PathVariable("id") String id) {
        return spotifyApiService.getAlbum(id);
    }

    @GetMapping("/search") //  Allow search for artists, albums, or tracks.
    public ResponseEntity<SearchModelDTO> search(@RequestParam("q") String query) {
        return spotifyApiService.search(query);
    }
}
