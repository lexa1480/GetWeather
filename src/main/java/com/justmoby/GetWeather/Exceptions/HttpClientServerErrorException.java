package com.justmoby.GetWeather.Exceptions;

public class HttpClientServerErrorException extends RuntimeException
{
    public HttpClientServerErrorException(String msg)
    {
        super(msg);
    }
}
