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
import java.util.logging.Logger;

@Service
public class SpotifyApiService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final AuthService authService;
    private static final Logger logger = Logger.getLogger(SpotifyApiService.class.getName());

    TokenRepository tokenRepository = TokenRepository.getInstance();

    @Autowired
    public SpotifyApiService (AuthService authService) {
        this.authService = authService;
    }

    public ResponseEntity<List<TopArtistsItemModel>> getTopArtists() {
        tokenValidation(); // Repeated function that validates that the token exists or it's not expired

        SpotifyTokenModelDTO tokenInfo = tokenRepository.getTokenData();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenInfo.token());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<TopArtistsModelResponse> response = restTemplate.exchange(
                UrlConstants.SPOTIFY_API_URL + "/me/top/artists?offset=0&limit=10",
                HttpMethod.GET,
                entity,
                TopArtistsModelResponse.class
            );

            if(response.getStatusCode() != HttpStatus.OK) {
                logger.warning("There's been an error while requesting the users top artists.");

                throw new ErrorCustomException(
                        response.getStatusCode(),
                        response.getBody().toString()
                );
            }

            TopArtistsModelResponse res = response.getBody();

            logger.info("User successfuly retrieved the user top artists data.");
            return new ResponseEntity<List<TopArtistsItemModel>>(res.items(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            logger.warning("There's been an error while requesting the users top artists.");

            throw new ErrorCustomException(
                e.getStatusCode(),
                e.getMessage()
            );
        }
    }


    public ResponseEntity<ArtistModelResponse> getArtist(String artistId) {
        tokenValidation(); // Repeated function that validates that the token exists or it's not expired

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
                logger.warning("There's been an error while requesting the artists data.");

                throw new ErrorCustomException(
                    response.getStatusCode(),
                    response.getBody().toString()
                );
            }

            logger.info("User successfuly retrieved the artists data.");
            return new ResponseEntity<ArtistModelResponse>(response.getBody(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            logger.warning("There's been an error while requesting the artists data.");

            throw new ErrorCustomException(
                    e.getStatusCode(),
                    e.getMessage()
            );
        }
    }


    public ResponseEntity<AlbumModelResponse> getAlbum(String albumId) {
        tokenValidation(); // Repeated function that validates that the token exists or it's not expired

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
                logger.warning("There's been an error while requesting the album data.");

                throw new ErrorCustomException(
                        response.getStatusCode(),
                        response.getBody().toString()
                );
            }

            logger.info("User successfuly retrieved the album data.");
            return new ResponseEntity<AlbumModelResponse>(response.getBody(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            logger.warning("There's been an error while requesting the album data.");

            throw new ErrorCustomException(
                    e.getStatusCode(),
                    e.getMessage()
            );
        }
    }


    public ResponseEntity<SearchModelDTO> search(String query) {
        tokenValidation(); // Repeated function that validates that the token exists or it's not expired

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
                logger.warning("There's been an error while requesting the search data.");

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

            logger.info("User successfuly retrieved the search data.");
            return new ResponseEntity<SearchModelDTO>(data, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            logger.warning("There's been an error while requesting the search data.");

            throw new ErrorCustomException(
                e.getStatusCode(),
                e.getMessage()
            );
        }
    }

    private void tokenValidation () {
        if (tokenRepository.getTokenData() == null) {
            logger.warning("User is not logged in, can't interact with the API.");

            throw new UnauthorizedCustomException(
                    HttpStatus.UNAUTHORIZED,
                    "User is not logged in, cannot interact with the API."
            );
        } else if (tokenRepository.getTokenData().isTokenExpired()) {
            authService.refreshToken();
        }
    }
}
