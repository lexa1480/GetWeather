package com.justmoby.GetWeather.Service;

import com.justmoby.GetWeather.Model.GeoDTO;
import com.justmoby.GetWeather.Model.TemperatureDTO;
import com.justmoby.GetWeather.Model.WeatherDTO;
import com.justmoby.GetWeather.Utils.CityNotFoundException;
import com.justmoby.GetWeather.Utils.HttpClientServerErrorException;
import com.justmoby.GetWeather.Utils.WeatherNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private RestClient restClient;

    @Mock
    private GeoService geoService;

    @InjectMocks
    private WeatherService weatherService;


    @Test
    void getTemperature_success()
    {
        String cityName   = "testCity";
        String WeatherUrl = "https://testUrl";
        GeoDTO geoDTO = new GeoDTO();
        geoDTO.setLatitude(51.5);
        geoDTO.setLongitude(0.5);
        List<GeoDTO> listGeoDTO = List.of(geoDTO);
        TemperatureDTO temperatureDTO = new TemperatureDTO();
        temperatureDTO.setTemperature(15);
        temperatureDTO.setTemperatureMin(10);
        temperatureDTO.setTemperatureMax(20);
        WeatherDTO weatherDTO = new WeatherDTO();
        weatherDTO.setTemperatureDTO(temperatureDTO);

        ReflectionTestUtils.setField(weatherService, "weatherApiUrl", WeatherUrl);
        when(geoService.getCoordinates(cityName)).thenReturn(listGeoDTO);
        RestClient.RequestHeadersUriSpec requestHeadersUriSpec = Mockito.mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec responseSpec = Mockito.mock(RestClient.ResponseSpec.class);
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(WeatherUrl)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(Mockito.any(), Mockito.any())).thenReturn(responseSpec);
        when(responseSpec.body(WeatherDTO.class)).thenReturn(weatherDTO);


        // Assert
        TemperatureDTO assertWeatherDTO = weatherService.getTemperature(cityName);
        assertNotNull(assertWeatherDTO);
        assertEquals(15, assertWeatherDTO.getTemperature());
        assertEquals(10, assertWeatherDTO.getTemperatureMin());
        assertEquals(20, assertWeatherDTO.getTemperatureMax());
    }

    @Test
    void getTemperature_WeatherNotFound()
    {
        String cityName   = "testCity";
        String WeatherUrl = "https://testUrl";
        GeoDTO geoDTO = new GeoDTO();
        geoDTO.setLatitude(51.5);
        geoDTO.setLongitude(0.5);
        List<GeoDTO> listGeoDTO = List.of(geoDTO);

        ReflectionTestUtils.setField(weatherService, "weatherApiUrl", WeatherUrl);
        when(geoService.getCoordinates(cityName)).thenReturn(listGeoDTO);
        RestClient.RequestHeadersUriSpec requestHeadersUriSpec = Mockito.mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec responseSpec = Mockito.mock(RestClient.ResponseSpec.class);
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(WeatherUrl)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(Mockito.any(), Mockito.any())).thenReturn(responseSpec);
        when(responseSpec.body(WeatherDTO.class)).thenReturn(null);


        // Assert
        assertThrows(WeatherNotFoundException.class, () -> weatherService.getTemperature(cityName));
    }

    @Test
    void getWeather_success()
    {
        String cityName   = "testCity";
        String WeatherUrl = "https://testUrl";
        GeoDTO geoDTO = new GeoDTO();
        geoDTO.setLatitude(51.5);
        geoDTO.setLongitude(0.5);
        List<GeoDTO> listGeoDTO = List.of(geoDTO);
        WeatherDTO weatherDTO = new WeatherDTO();

        ReflectionTestUtils.setField(weatherService, "weatherApiUrl", WeatherUrl);
        when(geoService.getCoordinates(cityName)).thenReturn(listGeoDTO);
        RestClient.RequestHeadersUriSpec requestHeadersUriSpec = Mockito.mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec responseSpec = Mockito.mock(RestClient.ResponseSpec.class);
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(WeatherUrl)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(Mockito.any(), Mockito.any())).thenReturn(responseSpec);
        when(responseSpec.body(WeatherDTO.class)).thenReturn(weatherDTO);


        // Assert
        WeatherDTO assertWeatherDTO = weatherService.getWeather(cityName);
        assertSame(weatherDTO, assertWeatherDTO);
    }

    @Test
    void getWeather_HttpClientServerError()
    {
        String cityName   = "testCity";
        String WeatherUrl = "https://testUrl";
        GeoDTO geoDTO = new GeoDTO();
        geoDTO.setLatitude(51.5);
        geoDTO.setLongitude(0.5);
        List<GeoDTO> listGeoDTO = List.of(geoDTO);

        ReflectionTestUtils.setField(weatherService, "weatherApiUrl", WeatherUrl);
        when(geoService.getCoordinates(cityName)).thenReturn(listGeoDTO);
        RestClient.RequestHeadersUriSpec requestHeadersUriSpec = Mockito.mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec responseSpec = Mockito.mock(RestClient.ResponseSpec.class);
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(WeatherUrl)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(
                Mockito.any(),
                Mockito.any())).thenThrow(new HttpClientServerErrorException("HttpClientServer Exception: Mocked Exception"));


        // Assert
        assertThrows(HttpClientServerErrorException.class, () -> weatherService.getWeather(cityName));
    }


    @Test
    void getWeather_CityNotFound()
    {
        String cityName   = "testCity";
        List<GeoDTO> listGeoDTO = Collections.emptyList();

        when(geoService.getCoordinates(cityName)).thenReturn(listGeoDTO);

        // Assert
        assertThrows(CityNotFoundException.class, () -> weatherService.getWeather(cityName));
    }
}