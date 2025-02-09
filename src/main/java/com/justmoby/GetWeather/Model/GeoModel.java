package com.justmoby.GetWeather.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoModel
{
    private String name;
    private Map<String, String> local_names;
    @JsonProperty("lat")
    private double latitude;
    @JsonProperty("lon")
    private double longitude;
    private String country;
    private String state;
}