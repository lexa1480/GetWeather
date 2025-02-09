package com.justmoby.GetWeather.Service;

import com.justmoby.GetWeather.Model.WeatherModel;
import com.justmoby.GetWeather.Utils.CityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeatherService
{
    public WeatherModel getWeather(String cityName)
    {
        WeatherModel weatherModel = new WeatherModel();

        return  weatherModel;
    }
}
