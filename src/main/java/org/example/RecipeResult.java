package org.example;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title",
        "calories",
        "carbs",
        "fat",
        "image",
        "imageType",
        "protein",
        "nutrition"
})
@Data
public class RecipeResult {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("title")
    public String title;
    @JsonProperty("calories")
    public Integer calories;
    @JsonProperty("carbs")
    public String carbs;
    @JsonProperty("fat")
    public String fat;
    @JsonProperty("image")
    public String image;
    @JsonProperty("imageType")
    public String imageType;
    @JsonProperty("protein")
    public String protein;
    @JsonProperty("nutrition")
    public Nutrition nutrition;
}
