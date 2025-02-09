package com.justmoby.GetWeather.Controller;

import com.justmoby.GetWeather.Model.WeatherModel;
import com.justmoby.GetWeather.Service.WeatherService;
import com.justmoby.GetWeather.Utils.CityNotFoundException;
import com.justmoby.GetWeather.Utils.WeatherErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class WeatherController
{
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService)
    {
        this.weatherService = weatherService;
    }

    @GetMapping("getWeather/{cityName}")
    public ResponseEntity<WeatherModel> getWeather(@PathVariable("cityName") String cityName)
    {
        WeatherModel weatherModel = weatherService.getWeather(cityName);

        return new ResponseEntity<>(weatherModel, HttpStatus.OK);
    }
}
