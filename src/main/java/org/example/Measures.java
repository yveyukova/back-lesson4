package org.example;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "original",
        "metric",
        "us"
})
@Data
public class Measures {

    @JsonProperty("original")
    public MeasuresType original;
    @JsonProperty("metric")
    public MeasuresType metric;
    @JsonProperty("us")
    public MeasuresType us;

}
