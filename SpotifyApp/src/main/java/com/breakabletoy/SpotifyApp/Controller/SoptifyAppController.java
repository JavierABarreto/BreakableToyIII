package com.breakabletoy.SpotifyApp.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SoptifyAppController {
    @PostMapping("/auth/spotify")
    public ResponseEntity login() {
        return new ResponseEntity<>("Login", HttpStatus.OK);
    }

    @GetMapping("/me/top/artists")
    public ResponseEntity topArtists() {
        return new ResponseEntity<>("Top Artists", HttpStatus.OK);
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity getArtist() {
        return new ResponseEntity<>("Artist", HttpStatus.OK);
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity getAlbum() {
        return new ResponseEntity<>("Album", HttpStatus.OK);
    }

    @GetMapping("/search") //  Allow search for artists, albums, or tracks.
    public ResponseEntity search() {
        return new ResponseEntity<>("search", HttpStatus.OK);
    }
}