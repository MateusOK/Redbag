package br.com.redbag.api.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PredictionResponse {
    @JsonProperty("predicted_class")
    private String predictedClass;

    @JsonProperty("confidence")
    private double confidence;
}

