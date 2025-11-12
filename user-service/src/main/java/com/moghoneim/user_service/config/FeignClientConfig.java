package com.moghoneim.user_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignClientConfig implements RequestInterceptor {

    private final AccessTokenProvider tokenProvider;

    @Override
    public void apply(RequestTemplate template) {
        String token = tokenProvider.getAccessToken();
        if (token != null) {
            template.header("Authorization", "Bearer " + token);
        }
    }
}


