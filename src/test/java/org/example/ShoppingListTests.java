package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShoppingListTests {
    private static final String APIKEY = "02d67ad1edbf41eabdd912b8e712140c";
    private static String id;
    RequestSpecification requestSpecification = null;
    ResponseSpecification responseSpecification = null;
    private String url = "https://api.spoonacular.com/mealplanner/";


    @BeforeAll
    public void beforeTest() {
        User user = given()
                .queryParam("apiKey", APIKEY)
                .body("{ \"username\": \"yveyukova\" }")
                .when()
                .post("https://api.spoonacular.com/users/connect")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .body()
                .as(User.class);
        assertEquals(user.status, "success");
        url = url + user.username + "/shopping-list";
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", APIKEY)
                .addQueryParam("hash", user.hash)
                .addQueryParam("username", user.username)
                .log(LogDetail.ALL)
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    void addShoppingListTest() {
        AisleItem item = given()
                .spec(requestSpecification)
                .body("{\n"
                        + " \"item\": \"salami\",\n"
                        + " \"aisle\": \"Sausages\",\n"
                        + " \"parse\": 0,\n"
                        + "}")
                .when()
                .post(url + "/items")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .body()
                .as(AisleItem.class);
        id = String.valueOf(item.id);
    }

    @Test
    void getShoppingListTest() {
        Aisles aisles= given()
                .spec(requestSpecification)
//                .expect()
//                .body("aisles[0].items[0].name", equalTo("salami"))
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .body()
                .as(Aisles.class);
        assertEquals(aisles.aisles.get(0).items.get(0).name, "salami");
    }

    @AfterAll
    public void tearDown() {
        given()
                .spec(requestSpecification)
                .delete(url + "/items/" + id)
                .then()
                .statusCode(200);
    }

}
