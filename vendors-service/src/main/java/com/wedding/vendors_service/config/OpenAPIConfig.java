package com.wedding.vendors_service.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor
@Configuration
public class OpenAPIConfig {

    private final Environment env;

    @Bean
    public OpenAPI vendorsServiceAPI(){
        return new OpenAPI()
                .info(new Info().title(env.getProperty("vendors.doc.title"))
                                .description(env.getProperty("vendors.doc.description"))
                                .version(env.getProperty("vendors.doc.version"))
                        );
    }

}
