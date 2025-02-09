package com.justmoby.GetWeather.Controller;

import com.justmoby.GetWeather.Model.TemperatureDTO;
import com.justmoby.GetWeather.Model.WeatherDTO;
import com.justmoby.GetWeather.Service.WeatherService;
import com.justmoby.GetWeather.Utils.CityNotFoundException;
import com.justmoby.GetWeather.Utils.NetworkException;
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
    public ResponseEntity<TemperatureDTO> getWeather(@PathVariable("cityName") String cityName)
    {
        System.out.println("WeatherController " + cityName);

        WeatherDTO weatherDTO = weatherService.getWeather(cityName);
        TemperatureDTO temperatureDTO = weatherDTO.getTemperatureDTO();

        System.out.println(temperatureDTO.getTemperature());

        return new ResponseEntity<>(temperatureDTO, HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<WeatherErrorResponse> handleException(CityNotFoundException cityNotFoundException)
    {
        System.out.println("CityNotFoundException ");

        WeatherErrorResponse weatherErrorResponse = new WeatherErrorResponse(cityNotFoundException.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(weatherErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<WeatherErrorResponse> handleException(NetworkException networkException)
    {
        System.out.println("NetworkException ");

        WeatherErrorResponse weatherErrorResponse = new WeatherErrorResponse(networkException.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(weatherErrorResponse, HttpStatus.NOT_FOUND);
    }
}
