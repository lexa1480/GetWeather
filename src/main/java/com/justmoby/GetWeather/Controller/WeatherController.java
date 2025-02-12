package com.justmoby.GetWeather.Controller;

import com.justmoby.GetWeather.Model.TemperatureDTO;
import com.justmoby.GetWeather.Service.WeatherService;
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
    public ResponseEntity<TemperatureDTO> getWeather(@PathVariable("cityName") String cityName)
    {
        TemperatureDTO temperatureDTO = weatherService.getTemperature(cityName);

        return new ResponseEntity<>(temperatureDTO, HttpStatus.OK);
    }
}
