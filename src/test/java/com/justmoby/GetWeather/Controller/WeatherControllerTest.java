package com.justmoby.GetWeather.Controller;

import com.justmoby.GetWeather.Model.TemperatureDTO;
import com.justmoby.GetWeather.Service.WeatherService;
import com.justmoby.GetWeather.Utils.CityNotFoundException;
import com.justmoby.GetWeather.Utils.HttpClientServerErrorException;
import com.justmoby.GetWeather.Utils.WeatherNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest
{
    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @Test
    void getWeather_Success()
    {
        String cityName = "testCity";
        TemperatureDTO temperatureDTO = new TemperatureDTO();
        temperatureDTO.setTemperature(15);
        temperatureDTO.setTemperatureMin(10);
        temperatureDTO.setTemperatureMax(20);

        when(weatherService.getTemperature(cityName)).thenReturn(temperatureDTO);


        // Assert
        ResponseEntity<TemperatureDTO> response = weatherController.getWeather(cityName);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(temperatureDTO, response.getBody());
    }

    @Test
    void getWeather_WeatherNotFound()
    {
        String cityName = "testCity";

        when(weatherService.getTemperature(cityName)).thenThrow(new WeatherNotFoundException("WeatherNotFound Exception: Mocked Exception"));


        // Assert
        assertThrows(WeatherNotFoundException.class, () -> weatherController.getWeather(cityName));
    }


    @Test
    void getWeather_CityNotFound()
    {
        String cityName = "testCity";

        when(weatherService.getTemperature(cityName)).thenThrow(new CityNotFoundException("CityNotFoundException Exception: Mocked Exception" ));


        // Assert
        assertThrows(CityNotFoundException.class, () -> weatherController.getWeather(cityName));
    }


    @Test
    void getWeather_HttpClientServerError()
    {
        String cityName = "testCity";

        when(weatherService.getTemperature(cityName)).thenThrow(new HttpClientServerErrorException("HttpClientServer Exception: Mocked Exception"));


        // Assert
        assertThrows(HttpClientServerErrorException.class, () -> weatherController.getWeather(cityName));
    }
}















