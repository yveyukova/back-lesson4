package org.example;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "cuisine",
        "cuisines",
        "confidence"
})
@Data
public class Cuisine {

    @JsonProperty("cuisine")
    public String cuisine;
    @JsonProperty("cuisines")
    public List<String> cuisines = new ArrayList<String>();
    @JsonProperty("confidence")
    public Double confidence;

}
