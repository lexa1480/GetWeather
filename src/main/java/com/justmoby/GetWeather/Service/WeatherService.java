package com.justmoby.GetWeather.Service;

import com.justmoby.GetWeather.Model.GeoDTO;
import com.justmoby.GetWeather.Model.WeatherDTO;
import com.justmoby.GetWeather.Utils.CityNotFoundException;
import com.justmoby.GetWeather.Utils.NetworkException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.List;

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

    public WeatherDTO getWeather(String cityName)
    {
        System.out.println("WeatherService " + cityName);

        List<GeoDTO> listGeoDTO = geoService.getCoordinates(cityName);
        if(listGeoDTO.isEmpty())
        {
            throw new CityNotFoundException("City is not found" + cityName);
        }

        GeoDTO geoDTO = listGeoDTO.getFirst();
        System.out.println("geoCoordinates " + geoDTO.getLatitude() + " / " + geoDTO.getLongitude());

        String weatherUrl = MessageFormat.format(weatherApiUrl, geoDTO.getLatitude(), geoDTO.getLongitude());

        System.out.println(weatherUrl);

        return webClient
            .get()
            .uri(weatherUrl)
            .retrieve()
            .onStatus(HttpStatusCode::isError,
                    error ->
                    {
                        System.out.println("Ошибка сервера 2 ");

                        return Mono.error(new NetworkException("Network Exception: " + error.statusCode()));
                    })
            .bodyToMono(WeatherDTO.class)
            .block();
    }
}
