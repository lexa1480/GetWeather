package com.justmoby.GetWeather.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemperatureDTO
{
    @JsonProperty("temp")
    private int temperature;
    @JsonProperty("temp_min")
    private int temperatureMin;
    @JsonProperty("temp_max")
    private int temperatureMax;
}