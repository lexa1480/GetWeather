package com.justmoby.GetWeather.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;


@Configuration
public class RestClientConfiguration
{
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Bean
    public RestClient restClient()
    {
        return RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }
}
















