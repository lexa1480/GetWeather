package com.justmoby.GetWeather.Service;

import com.justmoby.GetWeather.Model.GeoDTO;
import com.justmoby.GetWeather.Utils.NetworkException;
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

    private final RestClient restClient;

    public GeoService(RestClient restClient)
    {
        this.restClient = restClient;
    }

    public List<GeoDTO> getCoordinates(String cityName)
    {
        String GeoUrl = MessageFormat.format(geoApiUrl, cityName);

        return restClient
                .get()
                .uri(GeoUrl)
                .retrieve()
                .onStatus(HttpStatusCode::isError
                        , (request, response) ->
                        {
                            throw new NetworkException("Network Exception: " + response.getStatusCode() + ".\n" +  response.getHeaders());
                        })
                .body(new ParameterizedTypeReference<List<GeoDTO>>() {});
    }
}
