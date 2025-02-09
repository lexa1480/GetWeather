package com.justmoby.GetWeather.Service;

import com.justmoby.GetWeather.Model.GeoModel;
import com.justmoby.GetWeather.Utils.CityNotFoundException;
import com.justmoby.GetWeather.Utils.NetworkException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.http.HttpStatus;

@Service
public class GeoService
{
    @Value("${geo_api_url}")
    private String geoApiUrl;

    private final WebClient webClient;

    public GeoService(WebClient webClient) {
        this.webClient = webClient;
    }


    public List<GeoModel> getCoordinates(String cityName)
    {
        System.out.println("GeoService " + cityName);

        String GeoUrl = MessageFormat.format(geoApiUrl, cityName);

        System.out.println(GeoUrl);

        return webClient
                .get()
                .uri(GeoUrl)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        error ->
                        {
                            System.out.println("Ошибка сервера ");

                            return Mono.error(new NetworkException("Network Exception: " + error.statusCode()));
                        })
                .bodyToFlux(GeoModel.class)
                .collectList()
                .block();
    }
}
