package com.justmoby.GetWeather.Service;


import com.justmoby.GetWeather.Model.GeoDTO;
import com.justmoby.GetWeather.Utils.HttpClientServerErrorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class GeoServiceTest {

    @Mock
    private RestClient restClient;

    @InjectMocks
    private GeoService geoService;

    @Test
    void getCoordinates_success()
    {
        String cityName = "testCity";
        String GeoUrl   = "https://testUrl";
        GeoDTO GeoDTO = new GeoDTO();
        GeoDTO.setLatitude(51.5074);
        GeoDTO.setLongitude(0.1278);
        List<GeoDTO> listGeoDTO = List.of(GeoDTO);

        ReflectionTestUtils.setField(geoService, "geoApiUrl", GeoUrl);
        RestClient.RequestHeadersUriSpec requestHeadersUriSpec = Mockito.mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec responseSpec = Mockito.mock(RestClient.ResponseSpec.class);
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(GeoUrl)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(Mockito.any(), Mockito.any())).thenReturn(responseSpec);
        when(responseSpec.body(new ParameterizedTypeReference< List<GeoDTO>>() {})).thenReturn(listGeoDTO);


        // Assert
        List<GeoDTO> assertListGeoDTO = geoService.getCoordinates(cityName);
        assertEquals(1, assertListGeoDTO.size());
        assertEquals(GeoDTO.getLatitude(), assertListGeoDTO.getFirst().getLatitude());
        assertEquals(GeoDTO.getLongitude(), assertListGeoDTO.getFirst().getLongitude());
    }

    @Test//???
    void getCoordinates_emptyResult()
    {
        String cityName = "testCity";
        String GeoUrl   = "https://testUrl";
        List<GeoDTO> listGeoDTO = Collections.emptyList();

        ReflectionTestUtils.setField(geoService, "geoApiUrl", GeoUrl);
        RestClient.RequestHeadersUriSpec requestHeadersUriSpec = Mockito.mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec responseSpec = Mockito.mock(RestClient.ResponseSpec.class);
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(GeoUrl)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(Mockito.any(), Mockito.any())).thenReturn(responseSpec);
        when(responseSpec.body(new ParameterizedTypeReference< List<GeoDTO>>() {})).thenReturn(listGeoDTO);


        // Assert
        List<GeoDTO> assertListGeoDTO = geoService.getCoordinates(cityName);
        assertSame(listGeoDTO, assertListGeoDTO);
    }

    @Test
    void getCoordinates_networkError()
    {
        String cityName = "testCity";
        String GeoUrl   = "https://testUrl";

        ReflectionTestUtils.setField(geoService, "geoApiUrl", GeoUrl);
        RestClient.RequestHeadersUriSpec requestHeadersUriSpec = Mockito.mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec responseSpec = Mockito.mock(RestClient.ResponseSpec.class);
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(GeoUrl)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(
                Mockito.any(),
                Mockito.any())).thenThrow(new HttpClientServerErrorException("HttpClientServer Exception: Mocked Exception"));


        // Assert
        assertThrows(HttpClientServerErrorException.class, () -> geoService.getCoordinates(cityName));
    }
}
























