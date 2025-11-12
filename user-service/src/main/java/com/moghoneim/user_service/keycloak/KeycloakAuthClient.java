package com.moghoneim.user_service.keycloak;

import com.moghoneim.user_service.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KeycloakAuthClient {

    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();

    public String authenticate(LoginDto loginDto) {
        String clientId = env.getProperty("keycloak.client-id");
        String clientSecret = env.getProperty("keycloak.client-secret");
        String tokenUrl = env.getProperty("keycloak.token-url");

        if (clientId == null || clientSecret == null || tokenUrl == null) {
            throw new IllegalStateException("Missing Keycloak configuration in application properties.");
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("username", loginDto.getUsername());
        body.add("password", loginDto.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
            return response.getBody();
        } catch (Exception ex) {
            System.err.println("Error during authentication: " + ex.getMessage());
            throw new RuntimeException("Authentication failed. Please check your credentials.");
        }
    }
}
