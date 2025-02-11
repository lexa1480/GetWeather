package com.justmoby.GetWeather.Service;

import com.justmoby.GetWeather.Model.GeoDTO;
import com.justmoby.GetWeather.Utils.HttpClientServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.text.MessageFormat;
import java.util.List;

@Service
public class GeoService
{
    @Value("${geo_api_url}")
    private String geoApiUrl;

    public static final Logger LOG = LoggerFactory.getLogger(GeoService.class);

    private final RestClient restClient;

    public GeoService(RestClient restClient)
    {
        this.restClient = restClient;
    }


    public List<GeoDTO> getCoordinates(String cityName)
    {
        LOG.info("Start getCoordinates()-> {}", cityName);

        String GeoUrl = MessageFormat.format(geoApiUrl, cityName);

        return restClient
                .get()
                .uri(GeoUrl)
                .retrieve()
                .onStatus(HttpStatusCode::isError
                        , (request, response) ->
                        {
                            LOG.error("Network exception from getCoordinates()-> {}", GeoUrl);

                            throw new HttpClientServerErrorException("Network Exception: " + response.getStatusCode() + ".\n" + response.getHeaders());
                        })
                .body(new ParameterizedTypeReference<>() {
                });
    }
}













