package com.justmoby.GetWeather.Utils;


public class CityNotFoundException extends RuntimeException
{
    public CityNotFoundException(String msg)
    {
        super(msg);
    }
}

