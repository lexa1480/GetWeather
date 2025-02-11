package com.justmoby.GetWeather.Config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;


@Configuration
public class RestClientConfiguration
{
    private final int attempts = 3;

    public static final Logger LOG = LoggerFactory.getLogger(RestClientConfiguration.class);

    @Bean
    RestClient restClient()
    {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(Duration.ofSeconds(1));
        simpleClientHttpRequestFactory.setReadTimeout(Duration.ofSeconds(5L));

        return RestClient.builder()
                .requestFactory(simpleClientHttpRequestFactory)
                .requestInterceptor(
                        (request, body, execution) ->
                        {
                            LOG.info("Request {}", request.getURI());

                            ClientHttpResponse response = null;
                            for (int i = 0; i < attempts; i++)
                            {
                                try
                                {
                                    response = execution.execute(request, body);
                                    if(response.getStatusCode().is2xxSuccessful())
                                    {
                                        LOG.info("Successful at the {} attempts", i+1);
                                        return response;
                                    }
                                    LOG.info("Fail at the {} attempts", i+1);
                                }
                                catch (Exception exception)
                                {
                                    LOG.error("Fail from error at the {} attempts. {}", i+1, exception.getMessage());
                                    if(i == (attempts - 1))
                                    {
                                        LOG.error("Retry exhausted");
                                        throw exception;
                                    }
                                }
                            }

                            LOG.info("Retry exhausted");
                            return response;
                        }
                )
                .build();
    }
}














