package com.justmoby.GetWeather.Service;

import com.justmoby.GetWeather.Model.GeoDTO;
import com.justmoby.GetWeather.Model.WeatherDTO;
import com.justmoby.GetWeather.Utils.CityNotFoundException;
import com.justmoby.GetWeather.Utils.NetworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.text.MessageFormat;
import java.util.List;

@Service
public class WeatherService
{
    @Value("${data_api_url}")
    private String weatherApiUrl;

    private final RestClient restClient;
    private final GeoService geoService;

    public WeatherService(RestClient restClient, GeoService geoService)
    {
        this.restClient = restClient;
        this.geoService = geoService;
    }

    public WeatherDTO getWeather(String cityName)
    {
        List<GeoDTO> listGeoDTO = geoService.getCoordinates(cityName);
        if(listGeoDTO.isEmpty())
        {
            throw new CityNotFoundException("City not found: " + cityName);
        }

        GeoDTO geoDTO = listGeoDTO.getFirst();

        String weatherUrl = MessageFormat.format(weatherApiUrl, geoDTO.getLatitude(), geoDTO.getLongitude());

        return restClient
                .get()
                .uri(weatherUrl)
                .retrieve()
                .onStatus(HttpStatusCode::isError
                        , (request, response) ->
                        {
                            throw new NetworkException("Network Exception: " + response.getStatusCode() + ".\n" +  response.getHeaders());
                        })
                .body(WeatherDTO.class);
    }
}
