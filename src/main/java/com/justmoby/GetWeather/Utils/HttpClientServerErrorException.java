package com.justmoby.GetWeather.Utils;

public class HttpClientServerErrorException extends RuntimeException
{
    public HttpClientServerErrorException(String msg)
    {
        super(msg);
    }
}
