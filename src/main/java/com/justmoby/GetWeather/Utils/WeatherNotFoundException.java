package com.justmoby.GetWeather.Utils;

public class WeatherNotFoundException extends RuntimeException
{
    public WeatherNotFoundException(String msg)
    {
        super(msg);
    }
}
