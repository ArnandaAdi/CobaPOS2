package com.example.security2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostDTO {
    private  RajaOngkir rajaongkir;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class LocationDetails {
    @JsonProperty("city_id")
    private String cityId;

    @JsonProperty("province_id")
    private String provinceId;

    private String province;
    private String type;

    @JsonProperty("city_name")
    private String cityName;

    @JsonProperty("postal_code")
    private String postalCode;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class RajaOngkir {
    @JsonProperty("origin_details")
    private LocationDetails originDetails;

    @JsonProperty("destination_details")
    private LocationDetails destinationDetails;

    private List<Result> results;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Result {
    private String name;
    private List<ServiceInfo> costs;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ServiceInfo {
    private String service;
    private String description;
    private List<Cost> cost;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Cost {
    private int value;
    private String etd;

}