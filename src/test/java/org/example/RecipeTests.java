package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class RecipeTests {
    private final String apiKey = "02d67ad1edbf41eabdd912b8e712140c";
    private final String complexSearchUrl = "https://api.spoonacular.com/recipes/complexSearch";
    RequestSpecification requestSpecification = null;
    ResponseSpecification responseSpecification = null;

    RequestSpecification requestSpecificationCusine = null;

    private final String cusineUrl = "https://api.spoonacular.com/recipes/cuisine";

    @BeforeEach
    void beforeTest() {
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .log(LogDetail.ALL)
                .setContentType(ContentType.JSON)
                .build();
        requestSpecificationCusine = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .log(LogDetail.ALL)
                .setContentType(ContentType.JSON)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .build();

        RestAssured.responseSpecification = responseSpecification;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @ParameterizedTest
    @CsvSource({"chicken,Chicken 65"})
    void getRecipePositiveQueryTest(String input, String expected) {
        Recipe recipe = given()
                .spec(requestSpecification)
                .queryParam("query", input)
                .when()
                .get(complexSearchUrl)
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .as(Recipe.class);
        assertEquals(recipe.results.get(0).title, expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"calories:asc:Watermelon Popsicles with Mint, Basil & Lime"}, delimiter = ':')
    void getRecipeSortAscTest(String input1, String input2, String expected) {
        Recipe recipe = given()
                .spec(requestSpecification)
                .queryParam("sort", input1)
                .queryParam("sortDirection", input2)
                .when()
                .get(complexSearchUrl)
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .as(Recipe.class);
        assertEquals(recipe.results.get(0).title, expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"calories:desc:Kim's Baked Macaroni & Cheese"}, delimiter = ':')
    void getRecipeSortDescTest(String input1, String input2, String expected) {
        Recipe recipe = given()
                .spec(requestSpecification)
                .queryParam("sort", "calories")
                .queryParam("sortDirection", "desc")
                .when()
                .get(complexSearchUrl)
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .as(Recipe.class);
        assertEquals(recipe.results.get(0).title, expected);
    }

    @ParameterizedTest
    @CsvSource("12")
    void getRecipeSortNumberTest(String number) {
        Recipe recipe = given()
                .spec(requestSpecification)
                .queryParam("number", number)
                .when()
                .get(complexSearchUrl)
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .as(Recipe.class);
        assertEquals(recipe.results.size(), Integer.parseInt(number));
    }

    @ParameterizedTest
    @CsvSource({"pizza,Italian", "hotdog,American","moussaka,Greek","sushi,Japanese","curry,Indian"})
    void postRecipeCuisine(String title, String expected ) {
        Cuisine cuisine = given()
                .spec(requestSpecificationCusine)
                .formParam("title", title )
                .when()
                .post(cusineUrl)
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .as(Cuisine.class);
        assertEquals(cuisine.cuisine, expected);
    }

}
