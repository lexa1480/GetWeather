package com.justmoby.GetWeather.Exceptions;

public class WeatherNotFoundException extends RuntimeException
{
    public WeatherNotFoundException(String msg)
    {
        super(msg);
    }
}
