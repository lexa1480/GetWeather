package com.justmoby.GetWeather.Controller;

import com.justmoby.GetWeather.Exceptions.CityNotFoundException;
import com.justmoby.GetWeather.Exceptions.HttpClientServerErrorException;
import com.justmoby.GetWeather.Exceptions.WeatherErrorResponse;
import com.justmoby.GetWeather.Exceptions.WeatherNotFoundException;
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

    @ExceptionHandler(value = WeatherNotFoundException.class)
    private ResponseEntity<WeatherErrorResponse> handleException(WeatherNotFoundException weatherNotFoundException)
    {
        WeatherErrorResponse weatherErrorResponse = new WeatherErrorResponse(weatherNotFoundException.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(weatherErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = HttpClientServerErrorException.class)
    private ResponseEntity<WeatherErrorResponse> handleException(HttpClientServerErrorException httpClientServerErrorException)
    {
        WeatherErrorResponse weatherErrorResponse = new WeatherErrorResponse(httpClientServerErrorException.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(weatherErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    private ResponseEntity<WeatherErrorResponse> handleException(Exception exception)
    {
        WeatherErrorResponse weatherErrorResponse = new WeatherErrorResponse(exception.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(weatherErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
