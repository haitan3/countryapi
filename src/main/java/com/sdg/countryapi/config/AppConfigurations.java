package com.sdg.countryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfigurations {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}
