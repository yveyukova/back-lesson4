
package org.example;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "offset",
        "number",
        "results",
        "totalResults"
})
@Data
public class Recipe {

    @JsonProperty("offset")
    public Integer offset;
    @JsonProperty("number")
    public Integer number;
    @JsonProperty("results")
    public List<RecipeResult> results = new ArrayList<RecipeResult>();
    @JsonProperty("totalResults")
    public Integer totalResults;
}
