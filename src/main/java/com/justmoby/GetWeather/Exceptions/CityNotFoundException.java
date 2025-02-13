package com.justmoby.GetWeather.Exceptions;

public class CityNotFoundException extends RuntimeException
{
    public CityNotFoundException(String msg)
    {
        super(msg);
    }
}

