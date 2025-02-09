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
        Optional<WeatherModel> weatherModelOptional = Optional.of(weatherModel);

        if(weatherModelOptional.isPresent())
        {
            return weatherModelOptional.get();
        }
        else
        {
            throw new CityNotFoundException("City is not found" + cityName);
        }
    }
}
