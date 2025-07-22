package org.example.secure_joke_vault.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * Configuration class to define RestTemplate bean.
 * Required for calling external REST APIs.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Creates a shared RestTemplate bean for API calls.
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
