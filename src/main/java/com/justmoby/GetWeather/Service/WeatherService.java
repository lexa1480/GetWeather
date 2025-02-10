package com.justmoby.GetWeather.Service;

import com.justmoby.GetWeather.Model.GeoDTO;
import com.justmoby.GetWeather.Model.TemperatureDTO;
import com.justmoby.GetWeather.Model.WeatherDTO;
import com.justmoby.GetWeather.Utils.CityNotFoundException;
import com.justmoby.GetWeather.Utils.NetworkException;
import com.justmoby.GetWeather.Utils.WeatherNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherService
{
    @Value("${data_api_url}")
    private String weatherApiUrl;

    public static final Logger LOG = LoggerFactory.getLogger(WeatherService.class);

    private final RestClient restClient;
    private final GeoService geoService;

    public WeatherService(RestClient restClient, GeoService geoService)
    {
        this.restClient = restClient;
        this.geoService = geoService;
    }

    public TemperatureDTO getTemperature(String cityName)
    {
        WeatherDTO weatherDTO = getWeather(cityName);

        if(weatherDTO == null)
        {
            LOG.error("Resulting weather is empty");

            throw new WeatherNotFoundException("Weather not found city: " + cityName);
        }

        TemperatureDTO temperatureDTO = weatherDTO.getTemperatureDTO();
        LOG.info("Received Temperature,TemperatureMin,TemperatureMax -> {},{},{}", temperatureDTO.getTemperature(), temperatureDTO.getTemperatureMin(), temperatureDTO.getTemperatureMax());

        return temperatureDTO;
    }

    public WeatherDTO getWeather(String cityName)
    {
        LOG.info("Start getWeather()-> {}", cityName);

        List<GeoDTO> listGeoDTO = geoService.getCoordinates(cityName);
        if(listGeoDTO.isEmpty())
        {
            LOG.error("Resulting list of coordinates is empty");

            throw new CityNotFoundException("City not found city: " + cityName);
        }

        GeoDTO geoDTO = listGeoDTO.getFirst();
        LOG.info("Received Latitude,Longitude-> {},{}", geoDTO.getLatitude(), geoDTO.getLongitude());


        String weatherUrl = MessageFormat.format(weatherApiUrl, geoDTO.getLatitude(), geoDTO.getLongitude());
        return restClient
                .get()
                .uri(weatherUrl)
                .retrieve()
                .onStatus(HttpStatusCode::isError
                        , (request, response) ->
                        {
                            LOG.error("Network exception from getWeather()-> {}", weatherUrl);

                            throw new NetworkException("Network Exception: " + response.getStatusCode() + ".\n" + response.getHeaders());
                        })
                .body(WeatherDTO.class);
    }
}
