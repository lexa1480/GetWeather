package com.justmoby.GetWeather.Controller;

import com.justmoby.GetWeather.Utils.CityNotFoundException;
import com.justmoby.GetWeather.Utils.NetworkException;
import com.justmoby.GetWeather.Utils.WeatherErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController
{
    @ExceptionHandler(value = CityNotFoundException.class)
    private ResponseEntity<WeatherErrorResponse> handleException(CityNotFoundException cityNotFoundException)
    {
        WeatherErrorResponse weatherErrorResponse = new WeatherErrorResponse(cityNotFoundException.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(weatherErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NetworkException.class)
    private ResponseEntity<WeatherErrorResponse> handleException(NetworkException networkException)
    {
        WeatherErrorResponse weatherErrorResponse = new WeatherErrorResponse(networkException.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(weatherErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
