package com.justmoby.GetWeather.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WeatherErrorResponse
{
    private String message;
    private Long timestamp;

    public WeatherErrorResponse(String message, long timestamp)
    {
        this.message = message;
        this.timestamp = timestamp;
    }
}
