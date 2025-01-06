package com.breakabletoy.SpotifyApp.ConfigProperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spotify")
public class SpotifyConfig {
    private String clientId;
    private String clientSecret;
}
