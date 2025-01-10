package com.breakabletoy.SpotifyApp.Service;

import com.breakabletoy.SpotifyApp.DTO.SearchModelDTO;
import com.breakabletoy.SpotifyApp.DTO.SpotifyTokenModelDTO;
import com.breakabletoy.SpotifyApp.Exceptions.ErrorCustomException;
import com.breakabletoy.SpotifyApp.Exceptions.UnauthorizedCustomException;
import com.breakabletoy.SpotifyApp.Models.Complement.TopArtistsItemModel;
import com.breakabletoy.SpotifyApp.Models.Responses.AlbumModelResponse;
import com.breakabletoy.SpotifyApp.Models.Responses.ArtistModelResponse;
import com.breakabletoy.SpotifyApp.Models.Responses.SearchModelResponse;
import com.breakabletoy.SpotifyApp.Models.Responses.TopArtistsModelResponse;
import com.breakabletoy.SpotifyApp.Repository.TokenRepository;
import com.breakabletoy.SpotifyApp.Util.UrlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SpotifyApiService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final AuthService authService;

    TokenRepository tokenRepository = TokenRepository.getInstance();

    @Autowired
    public SpotifyApiService (AuthService authService) {
        this.authService = authService;
    }

    public ResponseEntity<List<TopArtistsItemModel>> getTopArtists() {
        if (tokenRepository.getTokenData() == null) {
            throw new UnauthorizedCustomException(
                HttpStatus.UNAUTHORIZED,
                "User is not logged in, cannot interact with the API."
            );
        } else if (tokenRepository.getTokenData().isTokenExpired()) {
            authService.refreshToken();
        }

        SpotifyTokenModelDTO tokenInfo = tokenRepository.getTokenData();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenInfo.token());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<TopArtistsModelResponse> response = restTemplate.exchange(
                UrlConstants.SPOTIFY_API_URL + "/me/top/artistse?offset=0&limit=10",
                HttpMethod.GET,
                entity,
                TopArtistsModelResponse.class
            );

            if(response.getStatusCode() != HttpStatus.OK) {
                throw new ErrorCustomException(
                        response.getStatusCode(),
                        response.getBody().toString()
                );
            }

            TopArtistsModelResponse res = response.getBody();
            return new ResponseEntity<List<TopArtistsItemModel>>(res.items(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            throw new ErrorCustomException(
                e.getStatusCode(),
                e.getMessage()
            );
        }
    }


    public ResponseEntity<ArtistModelResponse> getArtist(String artistId) {
        if (tokenRepository.getTokenData() == null) {
            throw new UnauthorizedCustomException(
                    HttpStatus.UNAUTHORIZED,
                    "User is not logged in, cannot interact with the API."
            );
        } else if (tokenRepository.getTokenData().isTokenExpired()) {
            authService.refreshToken();
        }

        SpotifyTokenModelDTO tokenInfo = tokenRepository.getTokenData();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenInfo.token());

        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<ArtistModelResponse> response = restTemplate.exchange(
                UrlConstants.SPOTIFY_API_URL + "/artists/" + artistId,
                HttpMethod.GET,
                entity,
                    ArtistModelResponse.class
            );

            if(response.getStatusCode() != HttpStatus.OK) {
                throw new ErrorCustomException(
                    response.getStatusCode(),
                    response.getBody().toString()
                );
            }

            return new ResponseEntity<ArtistModelResponse>(response.getBody(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            throw new ErrorCustomException(
                    e.getStatusCode(),
                    e.getMessage()
            );
        }
    }


    public ResponseEntity<AlbumModelResponse> getAlbum(String albumId) {
        if (tokenRepository.getTokenData() == null) {
            throw new UnauthorizedCustomException(
                    HttpStatus.UNAUTHORIZED,
                    "User is not logged in, cannot interact with the API."
            );
        } else if (tokenRepository.getTokenData().isTokenExpired()) {
            authService.refreshToken();
        }

        SpotifyTokenModelDTO tokenInfo = tokenRepository.getTokenData();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenInfo.token());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<AlbumModelResponse> response = restTemplate.exchange(
                    UrlConstants.SPOTIFY_API_URL + "/albums/" + albumId,
                    HttpMethod.GET,
                    entity,
                    AlbumModelResponse.class
            );

            if(response.getStatusCode() != HttpStatus.OK) {
                throw new ErrorCustomException(
                        response.getStatusCode(),
                        response.getBody().toString()
                );
            }

            return new ResponseEntity<AlbumModelResponse>(response.getBody(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            throw new ErrorCustomException(
                    e.getStatusCode(),
                    e.getMessage()
            );
        }
    }


    public ResponseEntity<SearchModelDTO> search(String query) {
        if (tokenRepository.getTokenData() == null) {
            throw new UnauthorizedCustomException(
                    HttpStatus.UNAUTHORIZED,
                    "User is not logged in, cannot interact with the API."
            );
        } else if (tokenRepository.getTokenData().isTokenExpired()) {
            authService.refreshToken();
        }

        String url = UrlConstants.SPOTIFY_API_URL + "/search?" +
                "q=track" + URLEncoder.encode(query, StandardCharsets.UTF_8) +
                "&type=album,artist,track" +
                "&limit=10&offset=0";

        SpotifyTokenModelDTO tokenInfo = tokenRepository.getTokenData();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenInfo.token());

        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<SearchModelResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    SearchModelResponse.class
            );

            if(response.getStatusCode() != HttpStatus.OK) {
                throw new ErrorCustomException(
                        response.getStatusCode(),
                        response.getBody().toString()
                );
            }

            SearchModelDTO data = new SearchModelDTO(
                    response.getBody().tracks().items(),
                    response.getBody().artists().items(),
                    response.getBody().albums().items()
            );

            return new ResponseEntity<SearchModelDTO>(data, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            throw new ErrorCustomException(
                e.getStatusCode(),
                e.getMessage()
            );
        }
    }
}
