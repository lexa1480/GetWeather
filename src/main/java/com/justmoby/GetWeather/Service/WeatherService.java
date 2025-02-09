package com.justmoby.GetWeather.Service;

import com.justmoby.GetWeather.Model.GeoModel;
import com.justmoby.GetWeather.Model.WeatherModel;
import com.justmoby.GetWeather.Utils.CityNotFoundException;
import com.justmoby.GetWeather.Utils.NetworkException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherService
{
    @Value("${data_api_url}")
    private String weatherApiUrl;

    private final WebClient webClient;

    private final GeoService geoService;

    public WeatherService(WebClient webClient, GeoService geoService)
    {
        this.webClient = webClient;
        this.geoService = geoService;
    }

    public WeatherModel getWeather(String cityName)
    {
        System.out.println("WeatherService " + cityName);

        List<GeoModel> listGeoModel = geoService.getCoordinates(cityName);
        if(listGeoModel.isEmpty())
        {
            throw new CityNotFoundException("City is not found" + cityName);
        }

        GeoModel geoModel = listGeoModel.getFirst();
        System.out.println("geoCoordinates " + geoModel.getLatitude() + " / " + geoModel.getLongitude());





        WeatherModel weatherModel = new WeatherModel();
        Optional<WeatherModel> weatherModelOptional = Optional.of(weatherModel);

        if(weatherModelOptional.isPresent())
        {
            return weatherModelOptional.get();
        }
        else
        {
            throw new CityNotFoundException("City is not found" + cityName);
        }
    }
}
